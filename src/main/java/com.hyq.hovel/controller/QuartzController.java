package com.hyq.hovel.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class QuartzController {

    @Scheduled(cron = "0 0/20 * * * ?")
    public void testJob(){
        System.out.println("=============今天没有比赛===========");
    }
}
