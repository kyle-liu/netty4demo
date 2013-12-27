package com.laiwang.protocol.example;

import com.laiwang.protocol.transport.NettyTransport;
import com.laiwang.protocol.transport.CompressHandler;
import com.laiwang.protocol.transport.NettyTransportSupport;
import com.laiwang.protocol.transport.NioTransportSupport;
import io.netty.channel.ChannelHandler;
import io.netty.handler.logging.LoggingHandler;

import javax.annotation.Nullable;

/**
 * User: kyle
 * Date: 13-11-27
 * Time: PM2:36
 */
public class ExampleTransport extends NettyTransport {

    @Override
    protected NettyTransportSupport transportSuppport() {
        return new NioTransportSupport() {
            @Nullable
            @Override
            protected ChannelHandler loggingHandler() {
                return new LoggingHandler();
            }

            @Override
            protected CompressHandler newCompressHandler() {
                return null;
            }
        };
    }
}
