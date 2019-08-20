package com.coates.helloservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 角色菜单关联表
    * </p>
*
* @author jobob
* @since 2019-05-07
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 角色主键
            */
        @TableField("roleId")
    private String roleId;

            /**
            * 菜单主键
            */
        @TableField("menuId")
    private String menuId;


}
