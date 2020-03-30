package com.coates.oauthresources.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserAccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @JsonProperty("user_name")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "账号密码不能为空")
    @JsonProperty("password")
    private String password;

    /**
     * 用户邮箱
     */
    @JsonProperty("e_mail")
    private String email;

    /**
     * 用户手机
     */
    @JsonProperty("mobile")
    private String mobile;

    /**
     * 注册时间
     */
    @JsonProperty("reg_time")
    private Date regTime;

    /**
     * 注册IP
     */
    @JsonProperty("reg_ip")
    private String regIp;

    /**
     * 最后登录时间
     */
    @JsonProperty("last_login_time")
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    @JsonProperty("last_login_ip")
    private String lastLoginIp;

    /**
     * 更新时间
     */
    @JsonProperty("update_time")
    private Date updateTime;

    /**
     * 用户状态
     */
    @JsonProperty("status")
    private Integer status;

    /**
     * 盐值
     */
    @JsonProperty("salt")
    private String salt;

    /**
     * 登录token
     */
    @JsonProperty("token")
    private String token;

    private Integer deleted;
}
