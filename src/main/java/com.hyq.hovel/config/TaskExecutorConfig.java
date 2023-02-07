package com.hyq.hovel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class TaskExecutorConfig {


    /**
     * 自定义线程池
     */
    @Bean(name = "myTestProcess")
    protected AsyncTaskExecutor nodeBackProcessTaskExecutor() {
        return createExecutor(20, 100, 1500, 3, "my-test-process-exec-");
    }

    private AsyncTaskExecutor createExecutor(int corePoolSize, int maxPoolSize, int queueCapacity, int keepAliveSeconds,
                                             String threadNamePrefix){
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.initialize();
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
