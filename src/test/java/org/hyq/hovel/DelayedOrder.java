package org.hyq.hovel;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedOrder implements Delayed {
    //延迟时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private long time;
    String name;

    public DelayedOrder() {
        this.name = "defultName";
        this.time = System.currentTimeMillis();
    }

    public DelayedOrder(String name, long time, TimeUnit unit) {
        this.name = name;
        this.time = System.currentTimeMillis() + (time > 0 ? unit.toMillis(time) : 0);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return time - System.currentTimeMillis();
    }
    @Override
    public int compareTo(Delayed o) {
        DelayedOrder order = (DelayedOrder) o;
        long diff = this.time - order.time;
        if (diff <= 0) {
            return -1;
        } else {
            return 1;
        }
    }


    /**
     * DelayQueue 延时队列测试
     */
    public static void main(String[] args) throws InterruptedException {
        DelayedOrder Order1 = new DelayedOrder("Order1", 5000, TimeUnit.MILLISECONDS);
        DelayedOrder Order2 = new DelayedOrder("Order2", 200, TimeUnit.MILLISECONDS);
        DelayedOrder Order3 = new DelayedOrder("Order3", 100, TimeUnit.MILLISECONDS);
        DelayQueue<DelayedOrder> delayQueue = new DelayQueue<>();
        delayQueue.put(Order1);
        delayQueue.put(Order2);
        delayQueue.put(Order3);

        System.out.println("订单延迟队列开始时间:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
        while (delayQueue.size() != 0) {
            //取队列头部元素是否过期
            DelayedOrder task = delayQueue.poll();
            if (task != null) {
                System.out.format("订单:{%s}被取消, 取消时间:{%s}\n", task.name, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS")));
            }
//            Thread.sleep(100);
        }
    }










}


