package com.coates.gateway.filter;

import com.coates.tools.cache.JedisCache;
import com.coates.tools.constant.KeyConstants;
import com.coates.tools.util.StringUtils;
import com.coates.tools.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName LoginFilter
 * @Description TODO
 * @Author mc
 * @Date 2019/7/1 14:16
 * @Version 1.0
 **/
@Component
@Slf4j
public class LoginFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("elcome to LoginFilter");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String tonken = request.getHeaders().get("token").toString();
        log.info("Verify that the user is logged in:[{}]", tonken);
        String serviceTonken = JedisCache.get(tonken);
        if (StringUtils.isEmpty(serviceTonken)) {
//            response.setStatusCode();
//            return response.setComplete();
        }
        boolean type = TokenUtil.checkToken(tonken, KeyConstants.LOGIN_TOKEN_KEY);
        if (!type) {
//            response.setStatusCode();
//            return response.setComplete();
        }
        log.info("token为:", type);
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
