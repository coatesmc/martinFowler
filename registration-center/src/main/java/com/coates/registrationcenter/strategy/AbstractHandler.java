package com.coates.registrationcenter.strategy;

import java.util.Map;

/**
 * @ClassName AbstractHandler
 * @Description TODO
 * @Author mc
 * @Date 2019/5/22 9:36
 * @Version 1.0
 **/
public abstract class AbstractHandler {
    abstract public String handle(Map<String,Object> map);
}
