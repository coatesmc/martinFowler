package com.coates.tools.sms.impl;

import com.coates.tools.sms.Sender;

/**
 * @ClassName MailSender
 * @Description TODO
 * @Author mc
 * @Date 2019/4/24 11:21
 * @Version 1.0
 **/
public class MailSender implements Sender {
    @Override
    public void Send() {
        System.out.println("The mail has been sent");
    }

}
