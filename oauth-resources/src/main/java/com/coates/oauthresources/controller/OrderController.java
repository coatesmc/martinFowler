package com.coates.oauthresources.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OrderController
 * @Description TODO
 * @Author mc
 * @Date 2020/3/27 11:01
 * @Version 1.0
 **/
@RestController
public class OrderController {
    @GetMapping(value = "/r1")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String r1() {
        return "访问资源1";
    }
    @GetMapping(value = "/r2")
    @PreAuthorize("hasAnyAuthority('user')")
    public String r2() {
        return "访问资源1";
    }
}
