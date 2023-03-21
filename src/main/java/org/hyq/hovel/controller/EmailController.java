package org.hyq.hovel.controller;

import org.hyq.hovel.model.ResultBean;
import org.hyq.hovel.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Session;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @GetMapping("/sendEmailExample")
    public ResultBean sendEmailExample(){

        Session session = emailService.create163Session();
        emailService.send163Email(session);

        return ResultBean.ok();
    }
}
