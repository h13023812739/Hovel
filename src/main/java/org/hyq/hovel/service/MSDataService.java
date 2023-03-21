package org.hyq.hovel.service;

import org.hyq.hovel.entity.HovelTask;

public interface MSDataService {

    HovelTask getHovekTask(Long taskId);

    String getTestString();

}
