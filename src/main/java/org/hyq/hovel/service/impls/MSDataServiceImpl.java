package org.hyq.hovel.service.impls;

import org.hyq.hovel.entity.HovelTask;
import org.hyq.hovel.service.MSDataService;
import org.hyq.hovel.mapper.HovelTaskMapper;
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

    @Override
    public String getTestString() {
        return "YOU TEST STRING";
    }
}
