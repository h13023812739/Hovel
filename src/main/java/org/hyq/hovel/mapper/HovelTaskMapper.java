package org.hyq.hovel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.hyq.hovel.entity.HovelTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HovelTaskMapper extends BaseMapper<HovelTask> {
    HovelTask findHovelTaskById(Long taskId);
}
