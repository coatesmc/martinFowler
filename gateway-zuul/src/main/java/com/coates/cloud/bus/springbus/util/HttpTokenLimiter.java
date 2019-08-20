package com.coates.cloud.bus.springbus.util;

import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName HttpTokenLimiter
 * @Description TODO
 * @Author mc
 * @Date 2019/5/28 11:11
 * @Version 1.0
 **/
public class HttpTokenLimiter {

    final RateLimiter rateLimiter = RateLimiter.create(10);
    AtomicInteger i = new AtomicInteger(1);
    public void doSomething() {
        boolean type = rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS);
        if (type) {
            //尝试获得令牌.为true则获取令牌成功
            System.out.println("正常处理");
        } else {
            System.out.println(i.getAndIncrement()+":短期无法获取令牌，真不幸，排队也瞎排");
            System.out.println("处理失败");
        }
    }

    public static void main(String args[]) throws IOException {
        /*
         * CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量，此值是线程将要等待的操作数（线程的数量）。
         * 当某个线程为了想要执行这些操作而等待时， 它要使用 await()方法。
         * 此方法让线程进入休眠直到操作完成。
         * 当某个操作结束，它使用countDown() 方法来减少CountDownLatch类的内部计数器，计数器的值就会减1。
         * 当计数器到达0时，它表示所有的线程已经完成了任务，这个类会唤醒全部使用await() 方法休眠的线程们恢复执行任务。
         *
         * */
        CountDownLatch latch = new CountDownLatch(1);

        Random random = new Random(10);
        HttpTokenLimiter tokenDemo = new HttpTokenLimiter();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    //  Thread.sleep(random.nextInt(1000));
                    tokenDemo.doSomething();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        latch.countDown();
        System.in.read();
    }



}
