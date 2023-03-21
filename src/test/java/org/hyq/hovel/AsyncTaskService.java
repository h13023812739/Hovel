package org.hyq.hovel;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class  AsyncTaskService {

    @Async
    public void executeAsyncTask1(Integer i){
        System.out.println(Thread.currentThread().getId() + "-" + Thread.currentThread().getName()+"执行异步任务1："+i);
    }

    @Async
    public void executeAsyncTask2(Integer i){
        System.out.println(Thread.currentThread().getId() + "-" + Thread.currentThread().getName()+"执行异步任务2："+i);
    }

}
