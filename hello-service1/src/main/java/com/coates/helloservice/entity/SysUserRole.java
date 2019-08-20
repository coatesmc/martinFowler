package com.coates.helloservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 用户角色关联表
    * </p>
*
* @author jobob
* @since 2019-05-07
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键
            */
            @TableId("Id")
    private String Id;

            /**
            * 用户主键
            */
        @TableField("userId")
    private String userId;

            /**
            * 角色主键
            */
        @TableField("roleId")
    private String roleId;


}
