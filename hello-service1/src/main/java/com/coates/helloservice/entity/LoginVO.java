package com.coates.helloservice.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName LoginVO
 * @Description TODO
 * @Author mc
 * @Date 2019/4/26 11:19
 * @Version 1.0
 **/
@Data
public class LoginVO {
    @NotBlank(message = "登录名不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String verificationCode;

    private int type;
}
