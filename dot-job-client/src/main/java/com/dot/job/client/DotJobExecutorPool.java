package com.dot.job.client;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author shuxun
 *
 * job任务执行线程池
 */
public class DotJobExecutorPool {

    private static ThreadPoolExecutor threadPoolExecutor;

    public DotJobExecutorPool(int queueSize) {
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue(queueSize);
        threadPoolExecutor = new ThreadPoolExecutor(1000, 1000,
                600L, TimeUnit.SECONDS,
                linkedBlockingQueue,
                new ThreadFactoryBuilder().setNameFormat("dot-job-execute-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
    }


    public void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }


}
