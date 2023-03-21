package org.hyq.hovel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: VisiableThreadPoolTaskExecutor
 * @Description: 线程池监控
 * @author lkf
 * @date 2020年4月15日 上午10:48:41
 *
 */
@SuppressWarnings("serial")
@Slf4j
public class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    /**
     * @Title: showThreadPoolInfo
     * @Description: 展示当前线程池任务：线程池-已提交任务-已完成任务-活跃线程-队列缓存数
     * void
     * @throws
     */
    private void showThreadPoolInfo() {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        log.info("线程池监控>>>{}, taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                this.getThreadNamePrefix(), threadPoolExecutor.getTaskCount(),
                threadPoolExecutor.getCompletedTaskCount(), threadPoolExecutor.getActiveCount(),
                threadPoolExecutor.getQueue().size());
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        showThreadPoolInfo();
        return super.submit(task);
    }

    @Override
    public void execute(Runnable task) {
        showThreadPoolInfo();
        super.execute(task);
    }

}
