package com.stodger.util;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Stodger
 * @version V1.0
 * @date 2019-07-09 15:16
 */
public class MailUtil {
    public static void sendMail(String email, String emailMsg)
            throws AddressException, MessagingException {
        // 1.创建一个程序与邮件服务器会话对象 Session
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "SMTP");
        props.setProperty("mail.host", "smtp.qq.com");
        props.setProperty("mail.smtp.port", "587");
        //指定验证为true
        props.setProperty("mail.smtp.auth", "true");

        // 创建验证器
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                //设置 账号和SMTP授权码
                return new PasswordAuthentication("1014165248", "pkbopxzolmkdbehg");
            }
        };

        Session session = Session.getInstance(props, auth);

        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);
        // 设置发送者
        message.setFrom(new InternetAddress("1014165248@qq.com"));

        // 设置发送方式与接收者
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));

        message.setSubject("会员申请");
        message.setContent(emailMsg, "text/html;charset=utf-8");

        // 3.创建 Transport用于将邮件发送
        Transport.send(message);
    }
}
