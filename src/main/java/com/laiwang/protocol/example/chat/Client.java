package com.laiwang.protocol.example.chat;

import ch.qos.logback.classic.Level;
import com.google.common.base.Charsets;
import com.laiwang.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkState;
import static com.laiwang.protocol.Content.content;
import static com.laiwang.protocol.LWP.*;
import static com.laiwang.protocol.transport.ResponseStatus.*;

/**
 * User: kyle
 * Date: 13-11-27
 * Time: PM2:22
 */
public class Client {
    static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static final String CONNECT_LOCAL = "tcp://localhost:12306";

    public static void main(String[] args) throws Exception {
        setLogInfoLevel();
        println("Welcome chat room!");
        println("Please tell me what is your name:");
        String name = reader.readLine();
        joinAs(name);
        chatingAs(name);
    }

    private static void setLogInfoLevel() {
        final ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.setLevel(Level.INFO);
    }

    private static void chatingAs(final String name) throws InterruptedException {
        try {
            for (; ; ) {
                String line = reader.readLine();

                if (":q".equals(line)) {
                    quit(name);
                    return;
                }

                call(request("/chat", content(name + ':' + line)), name);
            }
        } catch (IOException e) {
            e.printStackTrace();
            quit(name);
        }

    }

    private static void quit(String name) {
        ask(CONNECT_LOCAL, request("/quit", content(name + " quit the room.")), new BaseReplyOn(name) {
            @Override
            public void unhandled(Response response) throws Exception {
                close();
                System.exit(-1);
            }
        });
    }

    private static void joinAs(final String name) throws InterruptedException {
        connect(CONNECT_LOCAL, new Listen.Sharable() {
            @Path("/broadcast")
            void echo(Request request, Context context) {
                reply(context, response(OK));
                println(new String(request.body().bytes(), Charsets.UTF_8));
            }

            @Override
            public void unhandled(Request request, Context context) throws Exception {
                reply(context, response(BAD_REQUEST));
            }

            @Override
            public void caught(Throwable t, Context context) {
                t.printStackTrace();
            }
        });


        println("Input ':q' to quit room.");
        call(request("/join", content(name + " joined the room.")), name);
    }

    private static void call(Request request, final String name) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        ask(CONNECT_LOCAL, request, new BaseReplyOn(name) {
            @Status(Code.OK)
            void confirm(Response response) {
                latch.countDown();
            }
        });
        checkState(latch.await(2L, TimeUnit.SECONDS));
    }

    private static void println(String s) {
        System.out.println(s);
    }

    private abstract static class BaseReplyOn extends ReplyOn {

        private final String name;

        protected BaseReplyOn(String name) {
            super(1000L);
            this.name = name;
        }


        @Override
        public void unhandled(Response response) throws Exception {
            println("WARN:" + response);
            quit(name);
        }

        @Override
        public void caught(Throwable t) {
            t.printStackTrace();
        }
    }
}
