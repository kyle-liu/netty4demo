package com.nettyinaction.example.chapter1;

/**
 * User: kyle
 * Date: 14-1-3
 * Time: AM10:19
 */
public class CallbackFetcherExample {

    public interface Fetcher {
        void fetchData(FetchCallback callback);
    }


    public interface FetchCallback {
        void onData(Data data);

        void onError(Throwable cause);

    }


    public class Worker {

        public void doWork() {
            Fetcher fetcher = null;
            fetcher.fetchData(new FetchCallback() {
                public void onData(Data data) {
                    System.out.println("Data received: " + data);
                }

                public void onError(Throwable cause) {
                    System.out.println("An error accour: " + cause.getMessage());
                }
            });

        }
    }


    public class Data {

    }
}
