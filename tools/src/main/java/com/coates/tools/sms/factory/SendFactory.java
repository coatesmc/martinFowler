package com.coates.tools.sms.factory;

import com.coates.tools.sms.Sender;
import com.coates.tools.sms.impl.MailSender;
import com.coates.tools.sms.impl.SmsSender;

/**
 * @ClassName SendFactory
 * @Description TODO
 * @Author mc
 * @Date 2019/4/24 11:23
 * @Version 1.0
 **/
public class SendFactory {
    public Sender produceMail() {
        return new MailSender();
    }

    public Sender produceSms() {
        return new SmsSender();
    }
}
