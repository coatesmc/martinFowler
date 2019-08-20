package com.coates.cloud.bus.springbus.filter;


import com.coates.tools.cache.JedisCache;
import com.coates.tools.constant.KeyConstants;
import com.coates.tools.enums.HttpStatusCode;
import com.coates.tools.util.StringUtils;
import com.coates.tools.util.TokenUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginFilter
 * @Description TODO
 * @Author mc
 * @Date 2019/4/23 15:36
 * @Version 1.0
 **/
@Component
public class LoginFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    @Autowired
    private RouteLocator routeLocator;
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取当前请求
        HttpServletRequest request = ctx.getRequest();
        Route matchingRoute = this.routeLocator.getMatchingRoute(request.getRequestURI());
        logger.info("Whether to enter the interceptor");
        if ("/userAccountInfo/userLogin".equalsIgnoreCase(matchingRoute.getPath())) {
            logger.info("Calling the login interface does not require login verification");
            return false;
        }
        return true;
    }

    @Override
    public Object run() {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            //获取当前请求
            HttpServletRequest request = ctx.getRequest();
            HttpServletResponse response = ctx.getResponse();
            logger.info("Enter the access filter and access url:[{}],Method of access:[{}]", request.getRequestURI(), request.getMethod());
            String tonken = request.getHeader("token");
            logger.info("Verify that the user is logged in:[{}]", tonken);
            String serviceTonken = JedisCache.get(tonken);
            if (StringUtils.isEmpty(serviceTonken)) {
                ctx.setSendZuulResponse(false);
                response.sendError(HttpStatusCode.TOKEN_EXPIRED.getCode(), HttpStatusCode.TOKEN_EXPIRED.getMsg());
            }
            boolean type = TokenUtil.checkToken(tonken, KeyConstants.LOGIN_TOKEN_KEY);
            if (!type) {
                ctx.setSendZuulResponse(false);
                response.sendError(HttpStatusCode.INVALID.getCode(), HttpStatusCode.INVALID.getMsg());
                return null;
            }
            logger.info("token为:", type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
