package com.coates.oauth2.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName SysUser
 * @Description TODO
 * @Author mc
 * @Date 2019/8/5 11:46
 * @Version 1.0
 **/
@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class SysUser {
    private Long id;
    private String username;
    private String password;

    private List<SysPermission> sysPermissions;
}
