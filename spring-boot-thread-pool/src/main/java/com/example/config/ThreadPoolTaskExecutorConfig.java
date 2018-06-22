package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * spring线程池配置
 * <p>
 * 直接注入使用即可
 * <code> @Autowired
 * private ThreadPoolTaskExecutor threadPoolTaskExecutor;
 * </code>
 *
 * @author QuiFar
 * @version V1.0
 **/
@Configuration
public class ThreadPoolTaskExecutorConfig {

    /**
     * 线程池维护线程的最少数量
     */
    private final static int CORE_POOL_SIZE = 20;

    /**
     * 线程池维护线程的最大数量(超过最大值，workQueue将拒绝执行任务)
     */
    private final static int MAX_POOL_SIZE = 30;

    /**
     * 线程池空闲线程存活的时间
     */
    private final static int KEEP_ALIVE_SECONDS = 60;

    /**
     * 线程池被阻塞线程队列容量
     */
    private final static int BLOCKING_QUEUE_CAPACITY = 600;

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        executor.setQueueCapacity(BLOCKING_QUEUE_CAPACITY);
        return executor;
    }
}
