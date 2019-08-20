package com.coates.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.tomcat.jni.User;

import java.lang.reflect.Method;

/**
 * @ClassName CglibProxy
 * @Description TODO
 * @Author mc
 * @Date 2019/8/15 9:31
 * @Version 1.0
 **/
@Slf4j
public class CglibProxy  implements MethodInterceptor {

    private  Object target;

    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        log.info("事务开启");
        Object invoke = proxy.invoke(target, objects);
        log.info("关闭事务：【{}】"+invoke);

        return invoke;
    }

}
