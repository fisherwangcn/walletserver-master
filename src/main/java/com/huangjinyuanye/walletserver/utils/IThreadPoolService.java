package com.huangjinyuanye.walletserver.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface IThreadPoolService {

    Future<?> submit(Runnable runnable);

    <T> Future<T> submit(Callable<T> callable);

}
