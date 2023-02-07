package com.hyq.hovel.service.impls;

import com.hyq.hovel.config.TaskExecutorConfig;
import com.hyq.hovel.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import javax.activation.DataHandler;
import javax.annotation.Resource;
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
import java.time.LocalDate;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Resource(name = "myTestProcess")
    private ThreadPoolTaskExecutor myTestProcessExecutor;

    @Value("${hovel.mail.host}")
    private String smtpName;

    @Value("${hovel.mail.transport.protocol}")
    private String protocol;

    @Value("${hovel.mail.smtp.auth}")
    private String smtpAuth;

    @Value("${hovel.mail.smtp.starttls.enable}")
    private String smtpStarttlsEnable;

    @Value("${hovel.mail.username}")
    private String username;

    @Value("${hovel.mail.password}")
    private String password;

    private Properties properties = new Properties();

    @Override
    public Session create163Session(){

        log.info("==============初始化163邮件Session==============");
        properties.setProperty("mail.host",smtpName);
        properties.setProperty("mail.transport.protocol",protocol);
        properties.setProperty("mail.smtp.auth",smtpAuth);
        properties.setProperty("mail.smtp.starttls.enable",smtpStarttlsEnable);

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

            log.info("mail: "+Thread.currentThread().getName());

            CompletableFuture<BodyPart> futureText = CompletableFuture.supplyAsync(()->{
                log.info("text: "+Thread.currentThread().getName());
                // 正文
                BodyPart textPart = new MimeBodyPart();
                StringBuffer buf = new StringBuffer();
                buf.append("<h1>我来组成头部</h1>");
                try {
                    textPart.setContent(buf.toString(),"text/html;charset=utf-8");
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                return textPart;
            }, myTestProcessExecutor);

            CompletableFuture<BodyPart> futureImage = CompletableFuture.supplyAsync(()->{
                log.info("image: "+Thread.currentThread().getName());
                // 附件
                BodyPart imagePart = new MimeBodyPart();
                try {
                    imagePart.setFileName("MyPicture.jpg");
                    imagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(
                            Files.readAllBytes(Paths.get("C:\\Users\\PC\\Desktop\\Snipaste.jpg")),"image/jpeg"
                    )));
                    imagePart.setHeader("Content-ID","<huyeqiang>");
                } catch (MessagingException | IOException e) {
                    e.printStackTrace();
                }
                return imagePart;
            }, myTestProcessExecutor);

            futureText.thenAcceptBothAsync(futureImage, (textPart,imagePart)->{
                Multipart multipart = new MimeMultipart();
                log.info("send: "+Thread.currentThread().getName());
                try {
                    multipart.addBodyPart(textPart);
                    multipart.addBodyPart(imagePart);
                    msg.setContent(multipart);

                    // 发送邮件
                    Transport.send(msg);
                    log.info("邮件发完的时间{}", LocalDate.now());
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }, myTestProcessExecutor);

        } catch (MessagingException e) {
            log.error("邮件发送异常",e);
        }


    }
}
