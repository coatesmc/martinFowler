package com.coates.tools.resubmit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName AvoidRepeatableCommit
 * @Description TODO
 * @Author mc
 * @Date 2019/11/13 10:19
 * @Version 1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidRepeatableCommit {
    /**
     * 指定时间内不可重复提交,单位秒
     * @return
     */
    long timeout() default 500;
}
