package com.coates.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * @ClassName DebugFilter
 * @Description TODO
 * @Author mc
 * @Date 2019/6/14 11:25
 * @Version 1.0
 **/
@Component
@Slf4j
public class DebugFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        Route requestUrl = exchange.getRequiredAttribute(GATEWAY_ROUTE_ATTR);

        log.info("this is requestUrl -->【{}】", requestUrl.toString());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
