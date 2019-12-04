package com.coates.tools.resubmit;


import com.coates.tools.cache.JedisCache;
import com.coates.tools.util.IpUtil;
import com.coates.tools.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @ClassName AvoidRepeatableCommitAspect
 * @Description TODO
 * @Author mc
 * @Date 2019/11/13 9:13
 * @Version 1.0
 **/
@Order
@Aspect
@Component
public class AvoidRepeatableCommitAspect {
    public static org.slf4j.Logger logger = LoggerFactory.getLogger(AvoidRepeatableCommitAspect.class);


    /**
     * 默认年月日格式化
     */
    public static final String DATE_DEFAULT_FORMAT = "YYYY-MM-DD";

    /**
     * 默认日期时间格式化
     */
    public static final String DATETIME_DEFAULT_FORMAT = "YYYY-MM-DD HH:mm:ss";

    /**
     * 默认时间格式化
     */
    public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";

    /**
     * 重复提交默认时间500毫秒
     */
    public static final long AVOID_REPEATABLE_TIMEOUT = 500;


    /**
     * @param point
     */
    @Around("@annotation(com.coates.tools.resubmit.AvoidRepeatableCommit)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = IpUtil.getRemortIP(request);
        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //目标类、方法
        String className = method.getDeclaringClass().getName();
        String name = method.getName();
        String ipKey = String.format("%s#%s", className, name);
        int hashCode = Math.abs(ipKey.hashCode());
        String key = String.format("%s_%d", ip, hashCode);
        logger.info("ipKey={},hashCode={},key={}", ipKey, hashCode, key);
        AvoidRepeatableCommit avoidRepeatableCommit = method.getAnnotation(AvoidRepeatableCommit.class);
        long timeout = avoidRepeatableCommit.timeout();
        if (timeout < 0) {
            timeout = AVOID_REPEATABLE_TIMEOUT;
        }
        String value = JedisCache.get(key);
        logger.info("value={}", value);
        if (StringUtils.isNotEmpty(value)) {
            throw new RuntimeException("请勿重复提交");
        }
        logger.info("timeout={}", timeout);
        String uuId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        JedisCache.setRedisObjectPexpireAt(key, uuId, timeout);
        //执行方法
        Object object = point.proceed();
        return object;
    }
}
