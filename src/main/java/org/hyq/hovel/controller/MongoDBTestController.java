package org.hyq.hovel.controller;

import org.hyq.hovel.entity.mongo.StudentInfo;
import org.hyq.hovel.model.ResultBean;
import org.hyq.hovel.model.vo.StudentVo;
import org.hyq.hovel.service.StudentOpreateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class MongoDBTestController {

    @Resource
    private StudentOpreateService studentOpreateService;

    @GetMapping("/findDocument")
    public ResultBean findDocument(){
        List<StudentInfo> studentInfos = studentOpreateService.getStudentInfo();
        return ResultBean.ok(studentInfos);
    }

    @GetMapping("/findDocumentStuVo")
    public ResultBean findDocumentStuVo(){
        List<StudentVo> studentInfos = studentOpreateService.getStudentVo();
        return ResultBean.ok(studentInfos);
    }

    @GetMapping("/setDocument")
    public ResultBean setDocument(){
        StudentInfo studentInfo  =new StudentInfo();
        studentInfo.setId(1006l);
        studentInfo.setName("Alice");
        studentInfo.setAge(16);
        studentInfo.setClassId(202305l);
        boolean res = studentOpreateService.saveStudentInfo(studentInfo);
        if(res){
            return ResultBean.ok();
        } else {
            return ResultBean.error("mongo插入操作异常!");
        }

    }

}
