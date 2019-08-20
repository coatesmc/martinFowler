package com.coates.helloservice.controller;


import com.coates.helloservice.config.MyRuntimeException;
import com.coates.helloservice.service.ISysMenuService;

import com.coates.tools.enums.HttpStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-05-07
 */

@RestController("/sys-menu")
public class SysMenuController {

    private final ISysMenuService iSysMenuService;

    @Autowired
    public SysMenuController(ISysMenuService iSysMenuService) {
        this.iSysMenuService = iSysMenuService;
    }

    @RequestMapping("/getRoleMenu")
    public void getListMenu(String id) {
        if (id != null) {
            throw new MyRuntimeException(HttpStatusCode.MISSING_PARAMETER.getCode(),HttpStatusCode.MISSING_PARAMETER.getMsg());
        }
        iSysMenuService.getListMenu(id);

    }

}
