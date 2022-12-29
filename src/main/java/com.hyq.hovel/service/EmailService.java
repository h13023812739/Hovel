package com.hyq.hovel.service;


import javax.mail.Session;

public interface EmailService {

    Session create163Session();

    void send163Email(Session session);
}
