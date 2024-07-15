package io.github.zyszero.phoenix.cache.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.sql.SQLOutput;

/**
 * @Author: zyszero
 * @Date: 2024/6/21 1:49
 */
public class PhoenixCacheHandler extends SimpleChannelInboundHandler<String> {

    private static final String CRLF = "\r\n";


    private static final String STR_PREFIX = "+";

    private static final String BULK_PREFIX = "$";

    private static final String OK = "OK";

    private static final String INFO = "PhoenixCache server[v1.0.0], created by zyszero." + CRLF
            + "Mock Redis Server at 2024-06-21 in Guangzhou." + CRLF;


    private static final PhoenixCache CACHE = new PhoenixCache();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
        String[] args = message.split(CRLF);

        System.out.println("PhoenixCacheHandler => " + String.join(",", args));

        String cmd = args[2].toUpperCase();
        Command command = Commands.get(cmd);
        if (command != null) {
            try {
                Reply<?> reply = command.exec(CACHE, args);
                System.out.println("CMD[" + cmd + "] => " + reply.type + " => " + reply.value);
                replyContext(ctx, reply);
            } catch (Exception e) {
                Reply<String> reply = Reply.error("EXP exception with msg: '" + e.getMessage() + "'");
                replyContext(ctx, reply);
            }
        } else {
            Reply<String> reply = Reply.error("ERR unsupported command '" + cmd + "'");
            replyContext(ctx, reply);
        }
    }

    private void replyContext(ChannelHandlerContext ctx, Reply<?> reply) {
        switch (reply.getType()) {
            case INI -> integer(ctx, (Integer) reply.getValue());
            case ERROR -> error(ctx, (String) reply.getValue());
            case SIMPLE_STRING -> simpleString(ctx, (String) reply.getValue());
            case BULK_STRING -> bulkString(ctx, (String) reply.getValue());
            case ARRAY -> array(ctx, (String[]) reply.getValue());
            default -> simpleString(ctx, OK);
        }
    }

    private void error(ChannelHandlerContext ctx, String msg) {
        writeByteBuf(ctx, errorEncode(msg));
    }

    private String errorEncode(String msg) {
        return "-" + msg + CRLF;
    }

    private void integer(ChannelHandlerContext ctx, int i) {
        writeByteBuf(ctx, integerEncode(i));
    }

    private String integerEncode(int i) {
        return ":" + i + CRLF;
    }


    private void array(ChannelHandlerContext ctx, String[] array) {
        writeByteBuf(ctx, arrayEncode(array));
    }


    private String arrayEncode(Object[] array) {
        StringBuilder sb = new StringBuilder();
        if (array == null) {
            sb.append("$-1" + CRLF);
        } else if (array.length == 0) {
            sb.append("*0" + CRLF);
        } else {
            sb.append("*" + array.length + CRLF);
            for (int i = 0; i < array.length; i++) {
                Object obj = array[i];
                if (obj == null) {
                    sb.append("$-1" + CRLF);
                } else {
                    if (obj instanceof Integer) {
                        sb.append(integerEncode((Integer) obj));
                    } else if (obj instanceof String) {
                        sb.append(bulkStringEncode((String) obj));
                    } else if (obj instanceof Object[] objects) {
                        sb.append(arrayEncode(objects));
                    }
                }
            }
        }
        return sb.toString();
    }

    private void simpleString(ChannelHandlerContext ctx, String content) {
        String ret = stringEncode(content);
        writeByteBuf(ctx, ret);
    }

    private static String stringEncode(String content) {
        String ret;
        if (content == null) {
            ret = "$-1";
        } else if (content.isEmpty()) {
            ret = "$0";
        } else {
            ret = STR_PREFIX + content;
        }
        return ret + CRLF;
    }


    private void bulkString(ChannelHandlerContext ctx, String content) {
        String ret = bulkStringEncode(content);
        writeByteBuf(ctx, ret);
    }

    private static String bulkStringEncode(String content) {
        String ret;
        if (content == null) {
            ret = "$-1";
        } else if (content.isEmpty()) {
            ret = "$0";
        } else {
            ret = BULK_PREFIX + content.getBytes().length + CRLF + content;
        }
        return ret + CRLF;
    }

    private void writeByteBuf(ChannelHandlerContext ctx, String content) {
        System.out.println("wrap byte buffer and reply: " + content);
        ByteBuf buffer = Unpooled.buffer(128);
        buffer.writeBytes(content.getBytes());
        ctx.writeAndFlush(buffer);
    }
}
