package com.nettyinaction.example.chapter1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * User: kyle
 * Date: 14-1-3
 * Time: AM10:25
 */
public class FutureExample {


    public static  void run(){
        Runnable task1 = new Runnable() {
            public void run() {
                doSomeHeavyWork();

            }
        };

        Callable<Integer> task2 = new Callable<Integer>() {

            public Integer call() {
                return doSomeHeavyWorkResult();
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<?> future1= executorService.submit(task1);
        Future<Integer> future2 =  executorService.submit(task2);
        while(!future1.isDone() || !future2.isDone()) {

        }



    }


    private  static  void doSomeHeavyWork() {

    }

    private static  Integer doSomeHeavyWorkResult() {
        return 1;
    }
}
