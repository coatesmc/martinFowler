package com.coates.cloud.bus.springbus.event;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName ParameterFrom
 * @Description TODO
 * @Author mc
 * @Date 2019/6/11 10:05
 * @Version 1.0
 **/
@Data
public class ParameterFrom implements Serializable {

    private static  final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private int routesId;
    /**
     * 菜单
     */
    private String parameters;

    /**
     * 菜单地址
     */
    private  String url;

    /**
     * 是否需要参数验证
     */
    private Boolean  isValidate;

    /**
     * 是否验签
     */
    private Boolean isVerify;

}
