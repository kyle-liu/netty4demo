package com.taobao.geek.demo.unixtime;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * User: kyle
 * Date: 13-11-20
 * Time: PM4:20
 */
public class UnixTimeEncoder extends ChannelOutboundHandlerAdapter {


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        UnixTime m = (UnixTime) msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeInt(m.value());
        ctx.write(encoded, promise); // (1)
    }
}
