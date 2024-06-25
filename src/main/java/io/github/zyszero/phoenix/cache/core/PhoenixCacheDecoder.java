package io.github.zyszero.phoenix.cache.core;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zyszero
 * @Date: 2024/6/21 2:42
 */
public class PhoenixCacheDecoder extends ByteToMessageDecoder {
    AtomicLong counter = new AtomicLong();

    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf in, List<Object> out) throws Exception {

        System.out.println("PhoenixCacheDecode decodeCount: " + counter);
        if (in.readableBytes() <= 0) {
            return;
        }

        int count = in.readableBytes();
        int index = in.readerIndex();
        System.out.println("count:" + count + ",index:" + index);

        byte[] bytes = new byte[count];
        in.readBytes(bytes);
        String ret = new String(bytes);
        System.out.println("ret:" + ret);
        out.add(ret);
    }
}
