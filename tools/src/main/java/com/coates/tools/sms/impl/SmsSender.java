package com.coates.tools.sms.impl;

import com.coates.tools.sms.Sender;

/**
 * @ClassName SmsSender
 * @Description TODO
 * @Author mc
 * @Date 2019/4/24 11:21
 * @Version 1.0
 **/
public class SmsSender implements Sender {
    @Override
    public void Send() {
        System.out.println("SMS has been sent");
    }
}
