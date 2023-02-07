package com.hyq.hovel.service.impls;

import com.hyq.hovel.entity.HovelTask;
import com.hyq.hovel.service.MSDataService;
import com.hyq.hovel.mapper.HovelTaskMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MSDataServiceImpl implements MSDataService {

    @Resource
    private HovelTaskMapper hovelTaskMapper;

    @Override
    public HovelTask getHovekTask(Long taskId) {
        HovelTask hovelTask = hovelTaskMapper.selectById(1l);
        return hovelTask;
    }
}
