package com.coates.cloud.bus.springbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class SpringbusApplication   {

    public static void main(String[] args) {
        SpringApplication.run(SpringbusApplication.class, args);
    }
}