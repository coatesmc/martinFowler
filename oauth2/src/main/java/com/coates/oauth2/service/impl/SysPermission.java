package com.coates.oauth2.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.security.core.GrantedAuthority;

/**
 * @ClassName SysPermission
 * @Description TODO
 * @Author mc
 * @Date 2019/8/5 11:46
 * @Version 1.0
 **/
@Data
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class SysPermission implements GrantedAuthority {

    private Long id;
    private String name;
    private String code;
    private String url;
    private String method;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.code + ":" + this.method.toUpperCase();
    }
}
