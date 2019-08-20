package com.coates.helloservice.Threads;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @ClassName BusinessThread
 * @Description TODO
 * @Author mc
 * @Date 2019/4/10 11:45
 * @Version 1.0
 **/
@Component
@Scope("prototype")
@Slf4j
public class BusinessThread implements Runnable {
    private String acceptStr;

    public String getAcceptStr() {
        return acceptStr;
    }

    public void setAcceptStr(String acceptStr) {
        this.acceptStr = acceptStr;
    }

    public BusinessThread(String acceptStr) {
        this.acceptStr = acceptStr;
    }


    @Override
    public void run() {
        //业务操作
        log.info("多线程已处理订单插入系统，订单号：[{}]", acceptStr);
    }

}
