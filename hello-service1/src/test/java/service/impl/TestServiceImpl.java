package service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import service.TestService;

/**
 * @ClassName TestServiceImpl
 * @Description TODO
 * @Author mc
 * @Date 2019/8/23 10:54
 * @Version 1.0
 **/
@Service("testService")
@Slf4j
public class TestServiceImpl implements TestService {
    @Override
    public void test(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("int : 【{}】",i);

    }
}
