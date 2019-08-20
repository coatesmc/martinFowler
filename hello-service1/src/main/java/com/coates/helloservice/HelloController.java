package com.coates.helloservice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    public static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Value("${server.port}")
    String port;


    @RequestMapping(value = "/hello")
    public String Hello(@RequestParam String name) {
        logger.info("hello world-{}", port);

        return "hi " + name + ",i am from port:" + port;
    }


}
