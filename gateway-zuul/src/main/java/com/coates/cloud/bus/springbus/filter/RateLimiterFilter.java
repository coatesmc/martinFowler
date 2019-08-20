package com.coates.cloud.bus.springbus.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName RateLimiterFilter
 * @Description TODO
 * @Author mc
 * @Date 2019/4/23 14:40
 * @Version 1.0
 **/
@Component
public class RateLimiterFilter extends ZuulFilter {
    @Autowired
    private RouteLocator routeLocator;

    private static Logger logger = LoggerFactory.getLogger(RateLimiterFilter.class);

    //每秒产生1000个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(1000);


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -4;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取当前请求
        HttpServletRequest request = ctx.getRequest();
        Route matchingRoute = this.routeLocator.getMatchingRoute(request.getRequestURI());
        logger.info("Whether to enter the interceptor");

        //需要做限流的接口
        if ("/api/center/userAccountInfo/getUserInfo".equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() {
        try {
            RequestContext requestContext = RequestContext.getCurrentContext();
            HttpServletResponse response = requestContext.getResponse();
            //就相当于每调用一次tryAcquire()方法，令牌数量减1，当1000个用完后，那么后面进来的用户无法访问上面接口
            //当然这里只写类上面一个接口，可以这么写，实际可以在这里要加一层接口判断。
            if (!RATE_LIMITER.tryAcquire()) {
                logger.error("Traffic operation limit");
                requestContext.setSendZuulResponse(false);
                //HttpStatus.TOO_MANY_REQUESTS.value()里面有静态代码常量
                response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(), HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
