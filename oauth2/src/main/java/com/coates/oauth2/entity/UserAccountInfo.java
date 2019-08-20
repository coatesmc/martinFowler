package com.coates.oauth2.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jobob
 * @since 2019-04-28
 */
@Data
public class UserAccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户手机
     */
    private String mobile;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 注册IP
     */
    private String regIp;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 登录token
     */
    private String token;



}
