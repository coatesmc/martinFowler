package com.coates.registrationcenter.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName NormalHandler
 * @Description TODO
 * @Author mc
 * @Date 2019/5/22 9:35
 * @Version 1.0
 **/
@Component
@HandlerType("1")
public class NormalHandler extends AbstractHandler {
    @Override
    public String handle(Map<String, Object> map) {
        return "处理普通订单";
    }
}
