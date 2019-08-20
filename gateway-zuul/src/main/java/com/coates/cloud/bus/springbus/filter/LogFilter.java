package com.coates.cloud.bus.springbus.filter;

import com.coates.cloud.bus.springbus.util.LogSave;
import com.coates.tools.util.IpUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LogFilter
 * @Description TODO
 * @Author mc
 * @Date 2019/5/30 16:07
 * @Version 1.0
 **/
@Component
public class LogFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(LogFilter.class);
    @Autowired
    private RouteLocator routeLocator;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取当前请求
        HttpServletRequest request = ctx.getRequest();
        Route matchingRoute = this.routeLocator.getMatchingRoute(request.getRequestURI());
        logger.info("this is request-->>[{}]-[{}]-[{}]-[{}]", request.getMethod(), request.getRequestURI(),
                UserAgent.parseUserAgentString(request.getHeader("User-Agent")).toString());
        logger.info("this is route ----->> [{}],Ip -->[{}]", matchingRoute, IpUtil.getRemortIP(request));
        new LogSave().add(jdbcTemplate,request,matchingRoute);

        return null;
    }
}
