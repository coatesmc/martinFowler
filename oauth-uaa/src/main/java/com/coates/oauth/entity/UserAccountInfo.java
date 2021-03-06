package com.coates.oauth.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName(value = "user_account_info")
public class UserAccountInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @JsonProperty("user_name")
    @TableField("user_name")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "账号密码不能为空")
    @JsonProperty("password")
    @TableField("password")
    private String password;

    /**
     * 用户邮箱
     */
    @JsonProperty("e_mail")
    @TableField("e_mail")
    private String email;

    /**
     * 用户手机
     */
    @JsonProperty("mobile")
    @TableField("mobile")
    private String mobile;

    /**
     * 注册时间
     */
    @JsonProperty("reg_time")
    @TableField(fill =FieldFill.INSERT)
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
    @TableField("last_login_time")
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    @JsonProperty("last_login_ip")
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 更新时间
     */
    @JsonProperty("update_time")
    @TableField(fill =FieldFill.INSERT_UPDATE)
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

    @TableLogic
    @TableField(select = false)
    private Integer deleted;
}
