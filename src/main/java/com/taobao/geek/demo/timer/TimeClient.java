package com.taobao.geek.demo.timer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * User: kyle
 * Date: 13-11-19
 * Time: PM5:59
 */
public class TimeClient {

    public static void main(String[] args)  throws  Exception{
        String host = "localhost";
        int port = 8081;

        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //类似于ServerBootstrap,为非Server端来创建连接通道的
            Bootstrap b = new Bootstrap();
            b.group(workGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch) throws Exception {

                    ch.pipeline().addLast(new TimeClientHandler());
                }

            });

            //connect方法代替了bind()方法
            ChannelFuture f= b.connect(host, port).sync();

            f.channel().close().sync();



        } finally {

            workGroup.shutdownGracefully();

        }
    }
}
