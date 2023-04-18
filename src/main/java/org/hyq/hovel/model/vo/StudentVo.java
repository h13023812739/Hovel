package org.hyq.hovel.model.vo;

import lombok.Data;
import org.hyq.hovel.entity.mongo.ClassInfo;

import java.util.List;

@Data
public class StudentVo {

    public Long classId;

    private String className;

    private String studentName;

    private Integer studentCount;

}
