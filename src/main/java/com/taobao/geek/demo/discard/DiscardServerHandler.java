package com.taobao.geek.demo.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;


/**
 * User: kyle
 * Date: 13-11-19
 * <p/>
 * Time: PM4:16
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // Discard the received data silently.

        ByteBuf in = (ByteBuf)msg;
        try{
            while(in.isReadable()) {
                System.err.println((char)in.readByte());
                System.err.flush();
            }
        }
        finally {
            ReferenceCountUtil.release(msg);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
