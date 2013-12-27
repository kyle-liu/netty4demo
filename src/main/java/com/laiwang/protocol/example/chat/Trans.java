package com.laiwang.protocol.example.chat;

import com.laiwang.protocol.transport.CompressHandler;
import com.laiwang.protocol.transport.NettyTransport;
import com.laiwang.protocol.transport.NettyTransportSupport;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ServerChannel;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.handler.ssl.SslHandler;

import javax.annotation.Nullable;

/**
 * User: kyle
 * Date: 13-11-27
 * Time: PM2:23
 */
public class Trans extends NettyTransport {

    @Override
    protected NettyTransportSupport transportSuppport() {
        return new NettyTransportSupport(new OioEventLoopGroup(1)) {
            @Override
            protected Class<? extends Channel> channelClass() {
                return OioSocketChannel.class;
            }

            @Override
            protected Class<? extends ServerChannel> serverChannelClass() {
                return OioServerSocketChannel.class;
            }

            @Override
            protected SslHandler newSslHandler() {
                return null;
            }

            @Nullable
            @Override
            protected ChannelHandler loggingHandler() {
                return null;
            }

            @Override
            protected CompressHandler newCompressHandler() {
                return null;
            }
        };
    }
}
