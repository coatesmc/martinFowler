package com.coates.registrationcenter.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName GroupHandler
 * @Description TODO
 * @Author mc
 * @Date 2019/5/22 9:39
 * @Version 1.0
 **/
@Component
@HandlerType("2")
public class GroupHandler extends  AbstractHandler {
    @Override
    public String handle(Map<String, Object> map) {
        return "团购处理";
    }
}
