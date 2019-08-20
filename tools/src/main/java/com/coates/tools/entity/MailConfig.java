package com.coates.tools.entity;

import lombok.Data;

/**
 * @ClassName MailConfig
 * @Description TODO
 * @Author mc
 * @Date 2019/4/25 16:45
 * @Version 1.0
 **/
@Data
public class MailConfig {
    //服务邮箱
    private  String mailServerHost;
    //服务地址
    private  String mailSenderAddress;
    //发放者姓名
    private  String mailSenderNick;
    //发放用户名
    private  String mailSenderUsername;
    //发放邮箱密码
    private  String mailSenderPassword;

    public MailConfig(){
        super();
    }


}
