package com.coates.tools.util;

import com.coates.tools.entity.MailConfig;
import com.coates.tools.entity.MailInfo;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.List;

/**
 * @ClassName MailUtil
 * @Description TODO
 * @Author mc
 * @Date 2019/4/25 16:43
 * @Version 1.0
 **/
public class MailUtil {
    /**
     * 发送 邮件方法 (Html格式，支持附件)
     *
     * @return void
     */
    public static void sendEmail(MailInfo mailInfo) {
        MailConfig mailConfig = new MailConfig();
        try {
            HtmlEmail email = new HtmlEmail();
            // 配置信息
            email.setHostName(mailConfig.getMailServerHost());
            email.setFrom(mailConfig.getMailSenderAddress(),mailConfig.getMailSenderNick());
            email.setAuthentication(mailConfig.getMailSenderUsername(), mailConfig.getMailSenderPassword());
            email.setCharset("UTF-8");
            email.setSubject(mailInfo.getSubject());
            email.setHtmlMsg(mailInfo.getContent());

            // 添加附件
            List<EmailAttachment> attachments = mailInfo.getAttachments();
            if (null != attachments && attachments.size() > 0) {
                for (int i = 0; i < attachments.size(); i++) {
                    email.attach(attachments.get(i));
                }
            }

            // 收件人
            List<String> toAddress = mailInfo.getToAddress();
            if (null != toAddress && toAddress.size() > 0) {
                for (int i = 0; i < toAddress.size(); i++) {
                    email.addTo(toAddress.get(i));
                }
            }
            // 抄送人
            List<String> ccAddress = mailInfo.getCcAddress();
            if (null != ccAddress && ccAddress.size() > 0) {
                for (int i = 0; i < ccAddress.size(); i++) {
                    email.addCc(ccAddress.get(i));
                }
            }
            //邮件模板 密送人
            List<String> bccAddress = mailInfo.getBccAddress();
            if (null != bccAddress && bccAddress.size() > 0) {
                for (int i = 0; i < bccAddress.size(); i++) {
                    email.addBcc(ccAddress.get(i));
                }
            }
            email.send();
            System.out.println("邮件发送成功！");
        } catch (EmailException e) {
            e.printStackTrace();
        }

    }
}
