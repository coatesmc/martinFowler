package com.coates.cloud.bus.springbus.event;

import com.alibaba.fastjson.JSON;

import com.coates.tools.cache.JedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;*/

/**
 * @ClassName CubeRouteLocator
 * @Description TODO
 * @Author mc
 * @Date 2019/2/22 10:20
 * @Version 1.0
 **/
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {

    public final static Logger logger = LoggerFactory.getLogger(CustomRouteLocator.class);

    private JdbcTemplate jdbcTemplate;

    private ZuulProperties properties;


    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        logger.info("servletPath:{}", servletPath);
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routeMap = new LinkedHashMap<>();
        //从application.properties 中加载路由信息
        routeMap.putAll(super.locateRoutes());
        //从redis里面加载请求路径
        routeMap.putAll(locateRoutesFromRedis());

        //优化配置
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routeMap.entrySet()) {
            String path = entry.getKey();

            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getServletPath() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            logger.info("优化过后的数据格式key:{},value:{}", path, entry.getValue());

            values.put(path, entry.getValue());
        }

        return values;
    }

    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromRedis() {
        List<ZuulRouteVO> results = null;
        Map<String, ZuulProperties.ZuulRoute> route = new LinkedHashMap<>();
        Object routeObject = JedisCache.get("route");
        if (StringUtils.isEmpty(routeObject)) {
            results = jdbcTemplate.query("select * from gateway_api_define where enabled = true",
                    new BeanPropertyRowMapper<>(ZuulRouteVO.class));
            JedisCache.set("route", JSON.toJSONString(results));
        } else {
            results = JSON.parseArray(routeObject.toString(), ZuulRouteVO.class);
        }

        for (ZuulRouteVO result : results) {
            if (StringUtils.isEmpty(result.getPath())) {
                continue;
            }
            if (StringUtils.isEmpty(result.getServiceId()) && StringUtils.isEmpty(result.getUrl())) {
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                BeanUtils.copyProperties(result, zuulRoute);
            } catch (Exception e) {
                logger.error("===============================load zuul route info from db with " +
                        "error========================================", e);
            }
            route.put(zuulRoute.getPath(), zuulRoute);
        }

        return route;
    }
}
