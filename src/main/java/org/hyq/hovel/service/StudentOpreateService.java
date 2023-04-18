package org.hyq.hovel.service;

import org.hyq.hovel.entity.mongo.StudentInfo;
import org.hyq.hovel.model.vo.StudentVo;

import java.util.List;

public interface StudentOpreateService {
    List<StudentInfo> getStudentInfo();

    List<StudentVo> getStudentVo();

    boolean saveStudentInfo(StudentInfo studentInfo);
}
