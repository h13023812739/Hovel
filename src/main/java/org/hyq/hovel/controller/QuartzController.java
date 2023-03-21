package org.hyq.hovel.controller;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import org.hyq.hovel.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.hyq.hovel.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.mail.Session;
import java.util.Date;

@Slf4j
@Controller
public class QuartzController {

    @Resource
    EmailService emailService;

    @Scheduled(cron = "0 53 15 30 1 ?")
//    @Scheduled(cron = "0 0 0 * * ?")
    public void testJob(){
        Date tody = new Date();
        long anchorOne = System.currentTimeMillis();

        ChineseDate date = new ChineseDate(2023, 1, 9);
        if(DateUtil.isSameDay(tody,date.getGregorianDate())){
            log.info("=============定时邮件测试=={}===========", date.getGregorianDate());
            Session session = emailService.create163Session();
            emailService.send163Email(session);
            log.info("=============邮件测试耗时=={}===========", System.currentTimeMillis()-anchorOne);
        }
    }

    public static void main(String[] args) {
        Date tody = new Date();
        ChineseDate date = new ChineseDate(2023, 1, 9);
        System.out.println(tody);
        System.out.println(date.getGregorianDate());
        System.out.println(tody.equals(date.getGregorianDate()));
        System.out.println(DateUtil.isSameDay(tody,date.getGregorianDate()));
        System.out.println(DateUtil.isSameTime(tody,date.getGregorianDate()));

    }
}

