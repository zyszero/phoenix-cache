package io.github.zyszero.phoenix.cache.core;

import io.github.zyszero.phoenix.cache.PhoenixPlugin;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.stereotype.Component;

/**
 * phoenix cache server
 * @Author: zyszero
 * @Date: 2024/6/21 1:30
 */
@Component
public class PhoenixCacheServer implements PhoenixPlugin {

    int port = 6379;

    NioEventLoopGroup bossGroup;
    NioEventLoopGroup workerGroup;
    Channel channel;

    @Override
    public void init() {
        bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("redis-boss"));
        workerGroup = new NioEventLoopGroup(16, new DefaultThreadFactory("redis-worker"));
    }

    @Override
    public void startup() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new RedisDecoder());
                            ch.pipeline().addLast(new PhoenixCacheDecoder());
//                            ch.pipeline().addLast(new RedisEncoder());
                            ch.pipeline().addLast(new PhoenixCacheHandler());
                        }
                    });


            channel = serverBootstrap.bind(port).sync().channel();
            System.out.println("开启 netty redis 服务器，端口为 " + port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void shutdown() {
        if (channel != null) {
            this.channel.close();
            this.channel = null;
        }
        if (bossGroup != null) {
            this.bossGroup.shutdownGracefully();
            this.bossGroup = null;
        }
        if (workerGroup != null) {
            this.workerGroup.shutdownGracefully();
            this.workerGroup = null;
        }
    }
}
