package org.hyq.hovel.dao.mongo;

import org.hyq.hovel.entity.mongo.StudentInfo;
import org.hyq.hovel.model.vo.StudentVo;

import java.util.List;

public interface StudentDao {
    /**
     * 单表查询案例
     * @return
     */
    List<StudentInfo> findStudent();

    /**
     * 连表查询案例
     * @return
     */
    List<StudentVo> findStudentVo();

    boolean saveStudent(StudentInfo studentInfo);
}
