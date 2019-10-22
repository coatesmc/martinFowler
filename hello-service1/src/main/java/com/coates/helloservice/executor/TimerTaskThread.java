package com.coates.helloservice.executor;

import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName TimerTaskThread
 * @Description  线程池监控
 * @Author mc
 * @Date 2019/9/4 9:42
 * @Version 1.0
 **/
@Slf4j
public class TimerTaskThread implements Runnable , Serializable {

    private static final long serialVersionUID = -3139813688598533740L;
    /**
     * 初始化缓存线程池
     * 当线程池满了以后，抛出RejectedExecutionException异常
     */
    private  ThreadPoolExecutor CACHE_POOL = null;

    public TimerTaskThread(ThreadPoolExecutor CACHE_POOL) {
        this.CACHE_POOL = CACHE_POOL;
    }

    @Override
    public void run() {
        log.debug("监控线程池状态线程启动。。。");
        try {
            log.debug("线程池的状态： activeCount = {} ; queueSize = {} , completedTaskCount ={} ; taskCount = {} ; corePoolSize = {} ; " +
                            "largestPoolSize = {} ; maximumPoolSize = {} ; poolSize = {} ;",
                    CACHE_POOL.getActiveCount(), //激活的线程数， 此参数 <= 小于等于 最大线程数，  有锁
                    CACHE_POOL.getQueue().size(), //线程池缓存队列大小
                    CACHE_POOL.getCompletedTaskCount(), //已完成任务总数， 此参数 <= 小于等于 任务总数，  有锁
                    CACHE_POOL.getTaskCount(), //任务总数，
                    CACHE_POOL.getCorePoolSize(), //核心线程数
                    CACHE_POOL.getLargestPoolSize(), //线程池支持的最大线程数
                    CACHE_POOL.getMaximumPoolSize(), //线程池支持的最大线程数
                    CACHE_POOL.getPoolSize()
            );
            Thread.sleep(1000 * 60 * 5);
        }catch (Exception e){
            log.error("监控费线程池状态线程出错", e);
        }
    }
}
