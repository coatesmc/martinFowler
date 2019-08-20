package com.coates.cloud.bus.springbus.config;

import com.alibaba.fastjson.JSON;
import com.coates.cloud.bus.springbus.event.ParameterFrom;
import com.coates.tools.cache.JedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName ParameterStartTask
 * @Description TODO 定时远程调度 （待完善）
 * @Author mc
 * @Date 2019/6/11 11:29
 * @Version 1.0
 **/
@Component
@RequestMapping(value = "/gsLogin")
public class ParameterStartTask  implements CommandLineRunner {
    public Logger logger = LoggerFactory.getLogger(ParameterStartTask.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        logger.info("query validation data storage redis");
        List<ParameterFrom> results =  jdbcTemplate.query("SELECT * FROM `gateway_parameter`",
                new BeanPropertyRowMapper<>(ParameterFrom.class));
        logger.info("this is  parameterFrom size -->[{}]",results.size());
        results.forEach(parameterFrom -> JedisCache.set(parameterFrom.getUrl(), JSON.toJSONString(parameterFrom)));
    }
}
