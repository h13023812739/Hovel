package org.hyq.hovel;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyWRQueue {
    //共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据。
    private Object data = null;

    ReadWriteLock lock = new ReentrantReadWriteLock();

    // 读数据
    public void get() {
        // 加读锁
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " be ready to read data!");
            Thread.sleep((long) (Math.random() * 1000));
            System.out.println(Thread.currentThread().getName() + " have read data :" + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放读锁
            lock.readLock().unlock();
        }
    }

    // 写数据
    public void put(Object data) {
        // 加写锁
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " be ready to write data!");
            Thread.sleep((long) (Math.random() * 1000));
            this.data = data;
            System.out.println(Thread.currentThread().getName() + " have write data: " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放写锁
            lock.writeLock().unlock();
        }

    }
}

