package com.hyq.hovel.service.impls;

import com.hyq.hovel.annotation.SharedDataSource;
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
//    @SharedDataSource("slave")
    public HovelTask getHovekTask(Long taskId) {
        System.out.println(hovelTaskMapper.getClass());
//        HovelTask hovelTask = hovelTaskMapper.findHovelTaskById(10086l);
        HovelTask hovelTask = hovelTaskMapper.selectById(10086l);

        return hovelTask;
    }
}
