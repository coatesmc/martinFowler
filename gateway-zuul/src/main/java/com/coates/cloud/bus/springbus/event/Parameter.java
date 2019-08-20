package com.coates.cloud.bus.springbus.event;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Parameter
 * @Description TODO
 * @Author mc
 * @Date 2019/6/11 9:59
 * @Version 1.0
 **/
@Data
public class Parameter implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 来源
     */
    private  String  attr;
    /**
     * 取值 0 head,1 query,2 body
     */
    private int from;
    /**
     * 输出
     */
    private String toName;

    /**
     * 存值 0 head,1 query,2 body
     */
    private int to;

    /**
     * 是否必填
      */
    private Boolean required;

}
