package org.hyq.hovel.service.impls;

import org.hyq.hovel.dao.mongo.StudentDao;
import org.hyq.hovel.entity.mongo.StudentInfo;
import org.hyq.hovel.model.ResultBean;
import org.hyq.hovel.model.vo.StudentVo;
import org.hyq.hovel.service.StudentOpreateService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentOpreateServiceImpl implements StudentOpreateService {
    @Resource
    private StudentDao studentDao;

    @Override
    public List<StudentInfo> getStudentInfo() {
        return studentDao.findStudent();
    }

    @Override
    public List<StudentVo> getStudentVo() {
        return studentDao.findStudentVo();
    }

    @Override
    public boolean saveStudentInfo(@Validated(StudentInfo.class) @RequestBody StudentInfo studentInfo) {
        return studentDao.saveStudent(studentInfo);
    }
}
