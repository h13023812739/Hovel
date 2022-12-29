package com.hyq.hovel.controller;

import com.hyq.hovel.model.ResultBean;
import com.hyq.hovel.util.ConverUtil;
import com.hyq.hovel.util.OpenCVUtil;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/krpanoTest")
public class KrpanoController {

    @Resource
    private OpenCVUtil openCVUtil;

    @GetMapping("/test1")
    @ResponseBody
    public ResultBean test1(@RequestParam("list") List<String> filepathList){
        log.info("传入文件地址：{}",filepathList);
        return ResultBean.ok(openCVUtil.handels(filepathList));
    }

    @GetMapping("/test2")
    @ResponseBody
    public ResultBean test2(String filepath){
        log.info("传入文件地址：{}",filepath);
        List<Mat> list = OpenCVUtil.handel(filepath);
        log.info("切割结果：{}",list);
        return ResultBean.ok(list);
    }



}
