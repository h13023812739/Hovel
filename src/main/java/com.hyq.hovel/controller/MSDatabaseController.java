package com.hyq.hovel.controller;

import com.hyq.hovel.entity.HovelTask;
import com.hyq.hovel.model.ResultBean;
import com.hyq.hovel.service.MSDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class MSDatabaseController {
    @Resource
    private MSDataService msDataService;

    @GetMapping("/MSTest")
    public ResultBean getTaskName(Long taskId){
        HovelTask hovelTask = msDataService.getHovekTask(taskId);
        return ResultBean.ok(hovelTask.getTaskName());
    }

}
