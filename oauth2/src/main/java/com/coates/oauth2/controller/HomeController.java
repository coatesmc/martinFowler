package com.coates.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @ClassName HomeController
 * @Description TODO
 * @Author mc
 * @Date 2019/8/6 9:48
 * @Version 1.0
 **/
@RestController
@Slf4j
public class HomeController {
    @GetMapping({"/", "/index", "/home"})
    public String root(){
        return "index";
    }



    @GetMapping("/user")
    public String user(@AuthenticationPrincipal Principal principal){
      log.info("成功进入");
        return "success";
    }
}
