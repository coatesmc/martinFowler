package com.coates.helloservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* <p>
    * 菜单表
    * </p>
*
* @author jobob
* @since 2019-05-07
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 菜单名称
            */
        @TableField("menuName")
    private String menuName;

            /**
            * 父级菜单ID
            */
    private String pid;

            /**
            * 连接地址
            */
    private String url;

            /**
            * 图标
            */
    private String icon;

            /**
            * 排序
            */
    private Integer sort;

            /**
            * 深度
            */
    private Integer deep;

            /**
            * 编码
            */
    private String code;

            /**
            * 资源名称
            */
    private String resource;


}
