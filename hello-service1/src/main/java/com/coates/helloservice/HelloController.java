package com.coates.helloservice;

import com.coates.helloservice.executor.ExecutorManager;
import com.coates.tools.entity.ApiResult;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.RejectedExecutionException;

/**
 * @author Administrator
 */
@RestController
public class HelloController {

    public static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${score-thread-pool.core}")
    private Integer corePoolSize = 10;

    @Value("${score-thread-pool.maximum}")
    private Integer maximumPoolSize = 30;

    @Value("${score-thread-pool.queue}")
    private Integer blockingQueue = 80000;

    private static Integer i = 0;

    private static long startTime = 0;

    @Value("${server.port}")
    String port;

    @Autowired
    private RestClient client;

    @RequestMapping(value = "/hello")
    public String Hello(@RequestParam String name) {
        logger.info("hello world-{}", port);

        return "hi " + name + ",i am from port:" + port;
    }


    @RequestMapping("/test")
    public ApiResult submit() {
        logger.info("start submit");
        try {
            if (i == 0) {
                startTime = System.currentTimeMillis();
            }
            //调用service层的任务
            ExecutorManager.getInstance(corePoolSize, maximumPoolSize, blockingQueue).exchange(i++);
            long end = System.currentTimeMillis();
            logger.info("end submit------消时【{}】ms---开始时间:【{}】------结束时间:【{}】", end - startTime, startTime, end);
        } catch (RejectedExecutionException rej) {
            logger.error("积分缓存队列已满，缓存业务繁忙，请稍后再调用 {}", rej.getMessage());
            return new ApiResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), "积分缓存队列已满，缓存业务繁忙，请稍后再调用");

        } catch (Exception e) {
            logger.error("积分消费出错:", e);
            //return new ApiResult("153", HttpStatus.OK.value());
        }
        return ApiResult.successInstance();
    }


    @RequestMapping(value = "/add")
    public String add() {
        try {
            Request request = new Request("PUT", "/content/doc/20190909");
            String jsonString = "{\"id\":\"20190909\",\"name\":\"测试\",\"age\":22}";
            HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
            request.setEntity(entity);
            Response response = client.performRequest(request);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (Exception e) {

        }
        return "success";
    }

    @RequestMapping(value = "/search")
    public String search() {
        String result = null;
        try {
            Request request = new Request("GET", "/content/_search");
            String jsonString = "{\"query\":{\"match\":{\"name\":\"测试\"}}}";
            HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
            request.setEntity(entity);
            Response response = client.performRequest(request);
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/update")
    public String update() {
        try {
            Request request = new Request("PUT", "/content/doc/20190909");
            String jsonString = "{\"id\":\"20190909\",\"name\":\"测试-update\",\"age\":22}";
            HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
            request.setEntity(entity);
            Response response = client.performRequest(request);
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (Exception e) {

        }
        return "success";
    }

    @RequestMapping(value = "/get")
    public String get() {
        String result = null;
        try {
            Request request = new Request("GET", "/content/doc/20190909");
            Response response = client.performRequest(request);
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/delete")
    public String delete() {
        String result = null;
        try {
            Request request = new Request("DELETE", "/content/doc/20190909");
            Response response = client.performRequest(request);
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
