package org.hyq.hovel.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;


@Data
@Document(collection = "student_info")
public class StudentInfo {
    public Long id;

    @NotNull(message = "学生姓名不可为空！")
    public String name;

    public Integer age;

    public Long classId;

}
