package com.rekha.usecase.ordermgmt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Value("${app.async.thread_size}")
    private Integer threadSize;

    @Bean(name = "threadPoolTaskExecutor")
    public ExecutorService threadPoolTaskExecutor() {
        return Executors.newFixedThreadPool(threadSize);
    }
}
