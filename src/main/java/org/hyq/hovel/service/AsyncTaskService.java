package org.hyq.hovel.service;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class AsyncTaskService {

    @Async("myTestProcess")
    public void executeAsyncTask1(Integer i){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getId() + "---" + Thread.currentThread().getName()+"执行异步任务1："+i);
    }

    @Async("sendDownstreamProcess")
    public void executeAsyncTask2(Integer i){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getId() + "---" + Thread.currentThread().getName()+"执行异步任务2："+i);
    }

}
