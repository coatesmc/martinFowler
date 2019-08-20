package com.coates.gateway.routes;

import com.alibaba.fastjson.JSON;
import com.coates.gateway.entity.GatewayDefine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GatewayService
 * @Description TODO
 * @Author mc
 * @Date 2019/6/14 10:32
 * @Version 1.0
 **/
@Slf4j
@Service
public class GatewayService implements ApplicationEventPublisherAware, CommandLineRunner {

    @Autowired
    private RedisRouteDefinitionRepository routeDefinitionWriter;
    private ApplicationEventPublisher publisher;
    @Autowired
    private GatewayDefineRepository gatewayDefineRepository;


    private String save() {
        //从数据库拿到路由配置
        List<GatewayDefine> gatewayRouteList = gatewayDefineRepository.findAll();
        log.info("网关配置信息：=====>" + JSON.toJSONString(gatewayRouteList));
        gatewayRouteList.forEach(gatewayDefine -> {
            RouteDefinition definition = new RouteDefinition();
            Map<String, String> predicateParams = new HashMap<>(8);
            PredicateDefinition predicate = new PredicateDefinition();
            FilterDefinition filterDefinition = new FilterDefinition();

            Map<String, String> filterParams = new HashMap<>(8);
            definition.setId(gatewayDefine.getId().toString());
            predicate.setName("Path");
            predicateParams.put("pattern", gatewayDefine.getPath());
            predicateParams.put("pathPattern", gatewayDefine.getPath());
            URI uri = UriComponentsBuilder.fromUriString(gatewayDefine.getUrl()).build().toUri();
            filterDefinition.setName("StripPrefix");
            // 路径去前缀
            filterParams.put("_genkey_0", gatewayDefine.getStripPrefix().toString());
            filterParams.put("retries",gatewayDefine.getRetries());
            // 令牌桶流速
            filterParams.put("redis-rate-limiter.replenishRate", gatewayDefine.getLimiterRate());
            //令牌桶容量
            filterParams.put("redis-rate-limiter.burstCapacity", gatewayDefine.getLimiterCapacity());
            // 限流策略(#{@BeanName})
            filterParams.put("key-resolver", "#{@remoteAddrKeyResolver}");
            predicate.setArgs(predicateParams);
            filterDefinition.setArgs(filterParams);
            definition.setPredicates(Arrays.asList(predicate));
            definition.setFilters(Arrays.asList(filterDefinition));
            definition.setUri(uri);


            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        });
        this.publisher.publishEvent(new RefreshRoutesEvent(this));

        return "success";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


    @Override
    public void run(String... args) throws Exception {
        this.save();
    }

    public void deleteRoute(String routeId) {
        routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
    }

}
