package com.coates.cloud.bus.springbus.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ClassName CustomZuulConfig
 * @Description TODO
 * @Author mc
 * @Date 2019/2/22 15:41
 * @Version 1.0
 **/
@Configuration
public class CustomZuulConfig {

   public final ZuulProperties zuulProperties;
    public final ServerProperties server;
    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomZuulConfig(ZuulProperties zuulProperties, ServerProperties server, JdbcTemplate jdbcTemplate) {
        this.zuulProperties = zuulProperties;
        this.server = server;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public CustomRouteLocator routeLocator(){
        CustomRouteLocator routeLocator = new CustomRouteLocator(this.server.getServlet().getContextPath(),this.zuulProperties);
        routeLocator.setJdbcTemplate(jdbcTemplate);
        return routeLocator;
    }
}
