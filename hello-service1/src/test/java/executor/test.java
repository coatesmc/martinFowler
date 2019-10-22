package executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName test
 * @Description TODO
 * @Author mc
 * @Date 2019/9/9 10:30
 * @Version 1.0
 **/
public class test {
    private static ExecutorService es = new ThreadPoolExecutor(100, 150, 0L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(100000));

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 100000; i++) {
            es.execute(() -> {
                System.out.print(1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) es);

        while (true) {
            System.out.println();

            int queueSize = tpe.getQueue().size();
            System.out.println("当前排队线程数：" + queueSize);

            int activeCount = tpe.getActiveCount();
            System.out.println("当前活动线程数：" + activeCount);

            long completedTaskCount = tpe.getCompletedTaskCount();
            System.out.println("执行完成线程数：" + completedTaskCount);

            long taskCount = tpe.getTaskCount();
            System.out.println("总线程数：" + taskCount);

            Thread.sleep(100);
        }
    }
}
