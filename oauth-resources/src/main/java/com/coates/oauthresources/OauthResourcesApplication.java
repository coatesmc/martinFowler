package com.coates.oauthresources;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OauthResourcesApplication {
    public static void main(String[] args) {
        SpringApplication.run(OauthResourcesApplication.class, args);
    }

}
