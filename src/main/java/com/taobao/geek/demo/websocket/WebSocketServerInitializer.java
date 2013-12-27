package com.taobao.geek.demo.websocket;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * User: kyle
 * Date: 13-11-20
 * Time: PM4:50
 */
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("codec-http", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));

        ChannelHandlerAdapter handler = new WebSocketServerHandler();
        System.err.println(handler.isSharable());
        pipeline.addLast("handler", handler);

    }
}
