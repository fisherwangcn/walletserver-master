package com.huangjinyuanye.walletserver.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class ThreadPoolService implements IThreadPoolService {

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 40,
            60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(300),
            new ThreadPoolExecutor.CallerRunsPolicy());
    //这里的keepAliveTime意味着线程在空闲时只存活60秒

    @Override
    public Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        return executor.submit(callable);
    }

}
