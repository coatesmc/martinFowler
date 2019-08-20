package com.coates.registrationcenter.entity;

    import java.time.LocalDateTime;
    import java.io.Serializable;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author jobob
* @since 2019-05-31
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysInterfaceLog implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 服务id
            */
    private String serviceId;

            /**
            * 服务ip
            */
    private String serviceIp;

            /**
            * 软件环境
            */
    private String softwareEnvironment;

            /**
            * 日志名
            */
    private String logName;

            /**
            * 请求方法
            */
    private String requestMethod;

            /**
            * 请求接口
            */
    private String requestInterface;

            /**
            * 日志时间
            */
    private LocalDateTime logTime;

            /**
            * 用户代理，浏览器ORmobile信息
            */
    private String userAgent;

            /**
            * 请求数据
            */
    private String requestData;

            /**
            * ip4-ip6
            */
    private String requestIp;

            /**
            * 状态
            */
    private Integer requestCode;

            /**
            * 冗余
            */
    private String requestCodeName;


}
