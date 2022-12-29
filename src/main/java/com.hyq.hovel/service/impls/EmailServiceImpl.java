package com.hyq.hovel.service.impls;

import com.hyq.hovel.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    Properties properties = new Properties();

    private final String smtpName = "smtp.163.com";
    private final String username = "13023812739@163.com";
    private final String password = "PTJDWAFDQEZYBUBW";

    @Override
    public Session create163Session(){

        properties.setProperty("mail.host",smtpName);
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        session.setDebug(true);

        return session;
    }

    @Override
    public void send163Email(Session session){

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress("782598354@qq.com")));
            msg.setSubject("测试邮件，请勿回复","utf-8");

            // 正文
            BodyPart textPart = new MimeBodyPart();
            StringBuffer buf = new StringBuffer();
            buf.append("<h1>我来组成头部</h1>");
            textPart.setContent(buf.toString(),"text/html;charset=utf-8");

            // 附件
            BodyPart imagePart = new MimeBodyPart();
            imagePart.setFileName("MyPicture.jpg");
            imagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(
                    Files.readAllBytes(Paths.get("C:\\Users\\PC\\Desktop\\Snipaste.jpg")),"image/jpeg"
            )));
            imagePart.setHeader("Content-ID","<huyeqiang>");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(imagePart);

            msg.setContent(multipart);

            // 发送邮件
            Transport.send(msg);
        } catch (MessagingException | IOException e) {
            log.error("邮件发送异常",e);
        }


    }
}
