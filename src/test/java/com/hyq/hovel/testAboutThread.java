package com.hyq.hovel;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class testAboutThread {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {


//        new Thread(() -> System.out.println("通过Runnable方式执行任务")).start();
//
//        FutureTask task = new FutureTask(() -> {
//            System.out.println("通过Callable方式执行任务");
//            Thread.sleep(3000);
//            return Thread.currentThread().getName() + "返回任务结果";
//        });
//        new Thread(task).start();
//        try {
//            System.out.println(task.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello " + Thread.currentThread().getName() + "`s");

//        CompletableFuture<String> result1 = future.thenApply(param -> param + " World! " + Thread.currentThread().getName());
        CompletableFuture<String> result1 = future.thenApplyAsync((param)->{
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return param + " World! " + Thread.currentThread().getName();
        });

        CompletableFuture<String> result2 =
//                future.thenCompose(param -> CompletableFuture.supplyAsync(() -> param + " World！"));
                future.thenCompose(param -> CompletableFuture.supplyAsync(() -> param + " World！" + Thread.currentThread().getName()));

        result2.thenAccept((parm)->{
            System.out.println("先输出一次："+parm);
        });

        result1.thenAcceptBoth(result2,(param1,param2)->{
            System.out.println(param1);
            System.out.println(param2);
        });

//        try {
//            System.out.println(result1.get());
//            System.out.println(result2.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
