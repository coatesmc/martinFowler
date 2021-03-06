package com.coates.helloservice.executor;

import com.coates.helloservice.common.ApplicationContextHolder;
import com.coates.helloservice.common.Config;
import com.coates.helloservice.exception.InterfaceBusyException;
import com.coates.helloservice.service.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ExecutorManager
 * @Description TODO
 * @Author mc
 * @Date 2019/8/23 16:59
 * @Version 1.0
 **/

@Slf4j
public class ExecutorManager {


    /**
     * 目前采用阻塞，有界队列
     */
    private static BlockingQueue<ExecutorManager> cacheQueue = new LinkedBlockingQueue<ExecutorManager>(Config._CACHE_SIZE);

    /**
     * 服务运行状态
     */
    private static boolean isRunning = false;


    private static ExecutorManager executorManager;
    /**
     * 初始化缓存线程池
     * 当线程池满了以后，抛出RejectedExecutionException异常
     */
    private static ThreadPoolExecutor CACHE_POOL = null;

    private static TestService testService;


    private final ObjectMapper objectMapper = new ObjectMapper();


    static {
        testService = (TestService) ApplicationContextHolder.getBean("testService");
    }


    /**
     * 申明构造方法
     *
     * @param corePoolSize    核心大小
     * @param maximumPoolSize 最大线程数
     * @param blockingQueue   队列大小
     */
    public ExecutorManager(Integer corePoolSize, Integer maximumPoolSize, Integer blockingQueue) {

        CACHE_POOL = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(blockingQueue),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public static void main(String[] args) {
        System.out.println("time: "+ TimeUnit.SECONDS);
    }


    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public static void monitoringThreadpoolState() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        while (isRunning) {
            TimerTaskThread thread = new TimerTaskThread(CACHE_POOL);
            new Thread(thread).start();

        }
    }


    /**
     * <p>获取单利</p>`
     * 懒汉模式，并双重检查，线程安全
     *
     * @return
     */
    public static ExecutorManager getInstance(int corePoolSize, int maximumPoolSize, int blockingQueue) {
        if (executorManager == null) {
            synchronized (ExecutorManager.class) {
                if (executorManager == null) {
                    executorManager = new ExecutorManager(corePoolSize, maximumPoolSize, blockingQueue);
                    monitoringThreadpoolState();
                }
            }
        }
        return executorManager;
    }

    /**
     * 应用
     *
     * @param
     * @return
     * @throws Exception
     */
    public boolean exchange(final int num) throws Exception {
        CACHE_POOL.execute(() -> {
            int code = testService.test(num);
            if (code == 0) {
                log.info("处理成功：scoreUserId:{}  token:{}", code);
            } else if (code == 1704) {
                for (int i = 0; i < 3; i++) {
                    try {
                        log.info("为获取到用户锁，启动重试机制为3次,当前次数为：{}  重试休眠时间为：{}", i, i);
                        Thread.sleep(i * 1000);
                        code = testService.test(num);
                        if (code == 0) {
                            log.info("为获取到用户锁，启动重试机制为3次,当前次数为：{}  重试休眠时间为：{}", i, i);
                            break;
                        }
                        if (i == 2) {
                            log.info("请求错误");
                            //setErrorLogInfo(num, code);
                        }
                    } catch (InterruptedException e) {
                        log.error("睡眠中断异常 {}", e.getMessage());
                        throw new InterfaceBusyException("积分重试睡眠中断异常，请稍后再调用");
                    }
                }
            } else {
                // setErrorLogInfo(objectMapper, code);
            }
        });
        return true;
    }

    private void setErrorLogInfo(T consumeForm, int code) {
        /*scoreService.delToken(consumeForm.getUserId(), consumeForm.getToken());*/
        try {
    /*        scoreErrorLogService.addErrorLog(consumeForm.getOperType(), consumeForm.getScorType(),
                    objectMapper.writeValueAsString(consumeForm), consumeForm.getRemark() + "错误：" + code);*/
        } catch (Exception e) {
            log.error("异步记录异常：", e);
        }
        log.error("异步记录失败：code : {}", code);
    }
}
