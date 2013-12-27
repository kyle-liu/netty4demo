package com.taobao.geek.demo.unixtime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * User: kyle
 * Date: 13-11-20
 * Time: PM3:44
 */
public class UnixTimeClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        UnixTime m = (UnixTime) msg;
        System.out.println(m);
        ctx.close();


    }
}
