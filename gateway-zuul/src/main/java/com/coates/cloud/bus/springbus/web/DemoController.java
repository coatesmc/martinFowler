package com.coates.cloud.bus.springbus.web;



import com.alibaba.fastjson.JSON;
import com.coates.cloud.bus.springbus.event.RefreshRouteService;
import com.coates.cloud.bus.springbus.event.ZuulRouteVO;

import com.coates.tools.cache.JedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
/**
 * @ClassName DemoController
 * @Description TODO
 * @Author mc
 * @Date 2019/2/22 16:15
 * @Version 1.0
 **/
@RestController
public class DemoController {
    private static Logger logger = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    RefreshRouteService refreshRouteService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/refreshRoute")
    public String refreshRoute() {
        List<ZuulRouteVO> results =  jdbcTemplate.query("select * from gateway_api_define where enabled = true",
                new BeanPropertyRowMapper<>(ZuulRouteVO.class));
        JedisCache.setex("route", JSON.toJSONString(results), 1000 * 60);
        refreshRouteService.refreshRoute();
        return "refreshRoute";
    }

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    @RequestMapping("/watchNowRoute")
    public Object watchNowRoute(){
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return handlerMap;
    }

    @RequestMapping("/addRoute")
    @ResponseBody
    public String addRoute(ZuulRouteVO zuulRouteVO) {
        String sql = "INSERT INTO gateway_api_define (id,api_name,path,service_id,url,retryable,enabled,strip_prefix) VALUES  (?,?,?,?,?,?,?,?)";
        Object args[] = {zuulRouteVO.getId(),zuulRouteVO.getApiName(),zuulRouteVO.getPath(),zuulRouteVO.getServiceId(),
                zuulRouteVO.getUrl(),zuulRouteVO.getRetryable(),zuulRouteVO.getEnabled(),zuulRouteVO.getStripPrefix()==true?1:0};
        int temp = jdbcTemplate.update(sql,args);
        if (temp > 0){
            logger.info("Inserted successfully");
            List<ZuulRouteVO> results =  jdbcTemplate.query("select * from gateway_api_define where enabled = true",
                    new BeanPropertyRowMapper<>(ZuulRouteVO.class));
            JedisCache.set("route", JSON.toJSONString(results));
            refreshRouteService.refreshRoute();
        }else {
            logger.info("Insert failed");
        }

        return "refreshRoute";
    }
}
