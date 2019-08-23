package com.coates.cloud.bus.springbus.util;

import com.coates.cloud.bus.springbus.event.SysInterfaceLog;
import com.coates.tools.util.IpUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @ClassName LogSave
 * @Description TODO
 * @Author mc
 * @Date 2019/5/31 10:37
 * @Version 1.0
 **/
public class LogSave {
    private static Logger logger = LoggerFactory.getLogger(LogSave.class);

    @Async("asyncServiceExecutor")
    public void add(JdbcTemplate jdbcTemplate, HttpServletRequest request, Route matchingRoute) {
        addAsync(jdbcTemplate, request, matchingRoute);
    }

    public void addAsync(JdbcTemplate jdbcTemplate, HttpServletRequest request, Route matchingRoute) {
        Map<String, String[]> map = request.getParameterMap();
        Set<String> keys = map.keySet();
        StringBuffer para = new StringBuffer();
        for (String key : keys) {
            String[] value = map.get(key);
            para.append(key).append("=").append(value[0]).append("&");
        }
        SysInterfaceLog log = new SysInterfaceLog();
        log.setServiceId(matchingRoute.getId());
        log.setServiceIp(matchingRoute.getLocation());
        log.setSoftwareEnvironment(matchingRoute.getPath());
        log.setLogName("zuul");
        log.setRequestInterface(request.getRequestURI());
        log.setLogTime(new Date());
        log.setRequestMethod(request.getMethod());
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent")); //req就是request请求
        Browser browser = userAgent.getBrowser(); //获取浏览器信息 
        OperatingSystem os = userAgent.getOperatingSystem(); //获取操作系统信息
        StringBuffer userInfo = new StringBuffer();
        userInfo.append("操作系统：" + os.toString() + " 浏览器：" + browser.toString());
        logger.info("this is os info --->[{}]", userInfo.toString());
        log.setUserAgent(request.getHeader("User-Agent").toString());
        log.setRequestData(para.deleteCharAt(para.length() - 1).toString());
        log.setRequestIp(IpUtil.getRemortIP(request));
        logger.info("this is log -->>[{}]", log);

        save(jdbcTemplate, log);
    }

    private static void save(JdbcTemplate jdbcTemplate, SysInterfaceLog log) {
        String sql = "INSERT INTO `sys_interface_log` (`id`,`service_id`,`service_ip`,`software_environment`,`log_name`,`request_method`," +
                "`request_interface`,`log_time`,`user_agent`,`request_data`,`request_ip`,`request_code`,`request_code_name`) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object args[] = {null, log.getServiceId(), log.getServiceIp(), log.getSoftwareEnvironment(), log.getLogName(), log.getRequestMethod(),
                log.getRequestInterface(), log.getLogTime(), log.getUserAgent(), log.getRequestData(), log.getRequestIp(), null, null};
        try {
            int temp = jdbcTemplate.update(sql, args);
            if (temp > 0) {
                logger.info("Inserted successfully");
            } else {
                logger.info("Insert failed");
            }
        } catch (Exception e) {
            logger.info("this is Error-->", e.getMessage());
            e.printStackTrace();
        }
    }
}
