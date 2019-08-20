package com.coates.helloservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author jobob
 * @since 2019-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @TableField("roleName")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField("roleDesc")
    private String roleDesc;

    /**
     * 状态,1-启用,-1禁用
     */
    @TableField("roleState")
    private Integer roleState;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Data createTime;


}
