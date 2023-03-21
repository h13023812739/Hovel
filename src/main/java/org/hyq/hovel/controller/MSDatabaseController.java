package org.hyq.hovel.controller;

import org.hyq.hovel.entity.HovelTask;
import org.hyq.hovel.model.ResultBean;
import org.hyq.hovel.service.MSDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class MSDatabaseController {
    @Resource
    private MSDataService msDataService;

    @GetMapping("/MSTest")
    @ResponseBody
    public ResultBean getTaskName(Long taskId){
        HovelTask hovelTask = msDataService.getHovekTask(taskId);
        if(hovelTask != null){
            return ResultBean.ok(hovelTask.getTaskName());
        }
        return ResultBean.error("查询结果为空");
    }

}
