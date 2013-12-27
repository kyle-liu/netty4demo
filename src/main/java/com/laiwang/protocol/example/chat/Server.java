package com.laiwang.protocol.example.chat;

import com.google.common.collect.Sets;
import com.laiwang.protocol.*;


import java.util.Set;

import static com.laiwang.protocol.LWP.*;
import static com.laiwang.protocol.transport.ResponseStatus.OK;

/**
 * User: kyle
 * Date: 13-11-27
 * Time: PM2:11
 */
public class Server {
    public static void main(String[] args) {

        bind("tcp://localhost:12306", new Listen.Sharable() {
            Set<Context> clients = Sets.newHashSet();

            @Override
            public void unhandled(Request request, Context context) throws Exception {
                // handle all request: /chat, /join, /quit
                clients.add(context);
                reply(context, response(OK));
                for (Context c : clients) {
                    ask(c, request("/broadcast", request.body()), new ReplyOn(1000L) {
                        @Override
                        public void unhandled(Response response) throws Exception {
                        }

                        @Override
                        public void caught(Throwable t) {
                        }
                    });
                }
            }

            @Override
            public void caught(Throwable t, Context context) {
                t.printStackTrace();
            }

        });


        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                close();
            }
        }));
    }
}
