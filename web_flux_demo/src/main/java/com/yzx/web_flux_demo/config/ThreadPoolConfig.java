package com.yzx.web_flux_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @className: ThreadPoolConfig
 * @author: yzx
 * @date: 2025/11/8 9:24
 * @Version: 1.0
 * @description:
 */
@Configuration
public class ThreadPoolConfig {
    @Bean("jdbcExecutor")
    public ExecutorService threadPoolExecutorConfig() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public Scheduler jdbcScheduler(ExecutorService executorService) {
        return Schedulers.fromExecutorService(executorService);
    }
}
