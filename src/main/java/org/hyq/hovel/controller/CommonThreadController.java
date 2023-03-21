package org.hyq.hovel.controller;

import org.hyq.hovel.model.ResultBean;
import org.hyq.hovel.service.AsyncTaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
@Controller
public class CommonThreadController {

    @Resource
    AsyncTaskService asyncTaskService;

    @GetMapping("/contextLoads")
    @ResponseBody
    public ResultBean contextLoads() {
        System.out.println("hello");
        for(int i=0;i<10;i++){
            asyncTaskService.executeAsyncTask1(i);
            asyncTaskService.executeAsyncTask2(i);
        }
        return ResultBean.ok();
    }
}
