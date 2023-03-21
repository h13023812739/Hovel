package org.hyq.hovel;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private static final Lock lockA = new ReentrantLock();
    private static final Lock lockB = new ReentrantLock();
    //参数为true为公平锁
    private static final Lock lockC = new ReentrantLock(true);

    public static void main(String[] args) {
        Thread threadA = new Thread(new ThreadDemo1(lockA, lockB));
        Thread threadB = new Thread(new ThreadDemo1(lockA, lockB));
        threadA.start();
        threadB.start();
        threadA.interrupt();//第一个线程中断
    }

    static class ThreadDemo1 implements Runnable {
        Lock firstLock;
        Lock secondLock;
        public ThreadDemo1(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }
        @Override
        public void run() {
            try {
                firstLock.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + "获取到了资源"+ firstLock.hashCode()+"开始睡！ " + LocalDateTime.now());
                TimeUnit.SECONDS.sleep(5);
                firstLock.unlock();

                secondLock.lockInterruptibly();
                System.out.println(Thread.currentThread().getName() + "获取到了资源"+ secondLock.hashCode()+"开始睡！ " + LocalDateTime.now());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
//                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName() + "获取到了资源，正常结束！");
            }
        }
    }

//    public static void main(String[] args) {
//        final MyWRQueue queue = new MyWRQueue();
//        //一共启动6个线程，3个读线程，3个写线程
//        for (int i = 0; i < 3; i++) {
//            //启动1个读线程
//            new Thread() {
//                public void run() {
////                    while (true) {
//                        queue.get();
////                    }
//                }
//
//            }.start();
//            //启动1个写线程
//            new Thread() {
//                public void run() {
////                    while (true) {
//                        queue.put(new Random().nextInt(10000));
////                    }
//                }
//            }.start();
//        }
//    }


}