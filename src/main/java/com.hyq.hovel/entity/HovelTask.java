package com.hyq.hovel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("hovel_task")
public class HovelTask {
    @TableId(type = IdType.AUTO)
    @TableField("task_id")
    public Long taskId;

    @TableField("task_name")
    public Long taskName;

}
