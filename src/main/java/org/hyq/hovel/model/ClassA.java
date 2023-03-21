package org.hyq.hovel.model;

import javax.annotation.PostConstruct;

public class ClassA {
    @PostConstruct
    public void ClassA(){
        System.out.println("===============ClassA 构造方法=====因@PostConstruct执行在initMethod之前===============");
    }

    public void init(){
        System.out.println("================ClassA init 方法===================");
    }

    public void destroy(){
        System.out.println("ClassA destroy 方法");
    }

}
