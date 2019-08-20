package com.coates.gateway.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName GatewayDefine
 * @Description TODO
 * @Author mc
 * @Date 2019/6/13 16:10
 * @Version 1.0
 **/
@Entity
@Table(name = "gateway_define")
@Data
public class GatewayDefine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    //映射路劲
    @Column(name = "path")
    private String path;
    //映射服务
    @Column(name = "service_id")
    private String serviceId;

    //映射外连接
    @Column(name = "url")
    private String url;

    //令牌桶流速
    @Column(name = "limiter_rate")
    private String limiterRate;

    //令牌桶容量
    @Column(name = "limiter_capacity")
    private String limiterCapacity;

    //是否启用
    @Column(name = "enabled")
    private String enabled;

    //是否忽略前缀
    @Column(name = "strip_prefix")
    private Integer stripPrefix;
    //重试次数
    @Column(name = "retries")
    private String retries;

    @Column(name = "route_order")
    private String routeOrder;

}
