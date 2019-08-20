package com.coates.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName ExampleController
 * @Description TODO
 * @Author mc
 * @Date 2019/8/2 16:35
 * @Version 1.0
 **/
@RestController
public class ExampleController {
    @GetMapping("helloworld")
    public List<String> helloworld() {
        return Arrays.asList("Spring Security simple demo");
    }
}
