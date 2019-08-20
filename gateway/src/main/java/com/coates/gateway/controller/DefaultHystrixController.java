package com.coates.gateway.controller;

import com.coates.tools.entity.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DefaultHystrixController
 * @Description TODO 默认降级处理
 * @Author mc
 * @Date 2019/7/1 14:05
 * @Version 1.0
 **/
@RestController
@Slf4j
public class DefaultHystrixController {

    @RequestMapping("/defaultfallback")
    public ApiResult defaultfallback(){
        log.info("降级操作...");
        return ApiResult.ServerErrorInstance();
    }
}
