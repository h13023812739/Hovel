package org.hyq.hovel;

import org.hyq.hovel.service.MSDataService;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class TestAboutThread {

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Resource
    MSDataService msDataService;

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
                future.thenCompose(param -> CompletableFuture.supplyAsync(() -> param + " World！=====" + Thread.currentThread().getName()));

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

    /**
     * synchronized关键字 阻塞
     * sleep不释放锁对象（wait会释放锁对象，wait和notify都一定要在synchronized代码块内）
     *
     *     // 1 sync 修饰普通方法
     *     public synchronized int add() {
     *         return 0;
     *     }
     *
     *     // 2 sync 修饰静态方法 static等于对class进行加锁
     *     private synchronized static int sub() {
     *         return 0;
     *     }
     *
     *     // 3 sync 同步代码块
     *     public void method() {
     *         Object o = new Object();
     *         synchronized (o) {
     *
     *         }
     *     }
     *
     *     // 4 sync 同步代码块
     *     public void method2() {
     *         synchronized (DelayedOrder.class) {
     *
     *         }
     *     }
     */
    @Test
    public void synchronizedTest() {
        AtomicReference<String> s1 = new AtomicReference<>("l am lock1");
        String s2 = "l am lock2";
        AtomicReference<Integer> i= new AtomicReference<>(1);
        Integer j=100;
        Thread t1 = new Thread(()->{
            synchronized (s1){
                System.out.println("t1进来了");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1:"+s1);
            }
        });

        Thread t2 = new Thread(()->{
            System.out.println("t2进来了");

            synchronized (s1){
                try {
                    s1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                i.getAndSet(i.get() + 1);
                s1.set("s1 has changed");
                System.out.println("t2:"+s1);
            }
        });

        Thread t3 = new Thread(()->{
            System.out.println("t3进来了");

            synchronized (s1){

                s1.notify();

//                i.getAndSet(i.get() + 1);
                s1.set("s1 has changed again");
                System.out.println("t3:"+s1);
            }
        });

        try {

            t1.start();
            t2.start();
            t3.start();
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * AtomicReference非阻塞原子性
     * CAS乐观非阻塞赋值，因此也会存在更新失败的情况
     *
     */
    private static AtomicReference<BigDecimal> debitCardRef
            = new AtomicReference<>(new BigDecimal("0.01"));
    @Test
    public void casNoBlockAdd() {
        for (int i = 0; i < 100; i++) {
            new Thread("T-" + i) {
                @Override
                public void run() {
//                    System.out.println(Thread.currentThread().getName() + "来过");
                    // 获取AtomicReference的当前值
                    final BigDecimal dc = debitCardRef.get();
                    // 基于AtomicReference的当前值创建一个新的DebitCard
                    BigDecimal newDC = dc.add(new BigDecimal("0.01"));
                    // 基于CAS算法更新AtomicReference的当前值
                    if(debitCardRef.compareAndSet(dc,newDC)){
                        // 更新成功
                        System.out.println(Thread.currentThread().getName() + "得出结果:" + newDC + "=========" + debitCardRef.get());
                    }else {
                        System.out.println(Thread.currentThread().getName() + "CAS算法赋值失败:" + newDC + "=========" + debitCardRef.get());
                    }
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(current().nextInt(20));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }.start();
        }
    }

    /**
     * Thread内部操作全局或外部AtomicReference对象 风险！！
     * 如果操作对象和循环相关（用作循环条件） 会影响循环预期
     * 如果操作对象在主线程中用作判断，则判断是否生效的时机会不稳定
     */
    @Test
    public void threadRiskTest() {
        AtomicReference<String> str = new AtomicReference<>("太行山");
        AtomicInteger i = new AtomicInteger(0);
        final String a = "福泽万里";

        for (; i.getAndIncrement() < 20; ) {
            System.out.println(i);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            new Thread(()->{
                Integer in =i.get();
                if(i.compareAndSet(in,in-1)){
                    System.out.println(Thread.currentThread().getName()+"CAS成功"+in);
                }else {
                    System.out.println(Thread.currentThread().getName()+"CAS失败"+in);
                }
                System.out.println(Thread.currentThread().getName()+"想要去"+str);
                str.compareAndSet("太行山","长白山");
                System.out.println(a);
            },"Thread-"+i).start();
        }
    }
}
