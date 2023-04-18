package org.hyq.hovel.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "class_info")
public class ClassInfo {
    public Long id;

    public String name;

    public Integer studentCount;
}
