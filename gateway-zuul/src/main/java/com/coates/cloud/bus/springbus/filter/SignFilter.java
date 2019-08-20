package com.coates.cloud.bus.springbus.filter;

import com.alibaba.fastjson.JSON;
import com.coates.cloud.bus.springbus.enums.EvaluationEnum;
import com.coates.cloud.bus.springbus.event.Parameter;
import com.coates.cloud.bus.springbus.event.ParameterFrom;
import com.coates.cloud.bus.springbus.util.SignUtil;
import com.coates.tools.cache.JedisCache;
import com.coates.tools.enums.HttpStatusCode;
import com.coates.tools.util.MapHumpTransition;
import com.coates.tools.util.StringUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SignFilter
 * @Description TODO
 * @Author mc
 * @Date 2019/5/7 9:20
 * @Version 1.0
 **/
@Configuration
public class SignFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(SignFilter.class);

    @Autowired
    private RouteLocator routeLocator;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        try {
            // 获取到request
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            //参数重写
            //获取所有参数进行参数验证 参数格式 [k:[{"请求参数名"，value},{"转发参数"，value}],]
            Route matchingRoute = this.routeLocator.getMatchingRoute(request.getRequestURI());
            Object parameter = JedisCache.get(matchingRoute.getPath());
            if (StringUtils.isEmpty(parameter)) {
                return null;
            }
            ParameterFrom fromList = JSON.parseObject(parameter.toString(), ParameterFrom.class);
            //是否参数验证
            if (fromList.getIsValidate()) {
                logger.info("this  is isValidate -->>[{}]", true);
                dataVerification(fromList, request, ctx);
            }
            if (fromList.getIsVerify()) {
                logger.info("this  is isVerify -->>[{}]", true);
                // 获取请求参数name
                Map<String, String[]> param = request.getParameterMap();
                SignUtil.getSingleton().verifySignature(ctx, param);
                logger.info("{} >>> {}", request.getMethod(), request.getRequestURI());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 参数校验
     *
     * @param fromList
     * @param request
     * @param ctx
     * @return
     * @throws IOException
     */
    private void dataVerification(ParameterFrom fromList, HttpServletRequest request, RequestContext ctx) throws IOException {
        InputStream in = ctx.getRequest().getInputStream();
        String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
        logger.info("body:[{}]", body);

        List<Parameter> parameter = JSON.parseArray(fromList.getParameters(), Parameter.class);
        HttpServletResponse response = ctx.getResponse();
        Map<String, String> bodys = new HashMap<>();
        for (Parameter para : parameter) {
            String value = null;
            if (EvaluationEnum.HEAD_STATUS.getCode() == para.getFrom()) {
                value = request.getHeader(para.getAttr());
            }
            if (EvaluationEnum.QUERY_STATUS.getCode() == para.getFrom()) {
                value = request.getParameter(para.getAttr());
            }
            if (EvaluationEnum.BODY_STATUS.getCode() == para.getFrom()) {
                value = request.getParameter(para.getAttr());
            }
            if (para.getRequired()) {
                if (StringUtils.isEmpty(value)) {
                    ctx.setSendZuulResponse(false);
                    response.sendError(HttpStatusCode.MISSING_PARAMETER.getCode(), para.getAttr() + " is empty");
                  return;
                }
            }
            if (!StringUtils.isEmpty(value)){
                bodys.put(para.getToName(), value);
            }
        }

        String newBody =  MapHumpTransition.buildMap(bodys);
        final byte[] reqBodyBytes = newBody.getBytes();
        ctx.setRequest(new HttpServletRequestWrapper(request) {
            @Override
            public ServletInputStream getInputStream() throws IOException {
                return new ServletInputStreamWrapper(reqBodyBytes);
            }

            @Override
            public int getContentLength() {
                return reqBodyBytes.length;
            }

            @Override
            public long getContentLengthLong() {
                return reqBodyBytes.length;
            }
        });
    }
}
