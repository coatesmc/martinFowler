package component;

import com.coates.helloservice.common.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import service.TestService;

/**
 * @ClassName AsyncService
 * @Description 执行异步任务
 * @Author mc
 * @Date 2019/8/23 10:34
 * @Version 1.0
 **/
@Slf4j
public class AsyncService {

    private static TestService testService;

    static {
        testService = (TestService) ApplicationContextHolder.getBean("testService");
    }


    @Async("asyncServiceExecutor")
    public void executeAsync(int test) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("int : 【{}】", test);
    }
}
