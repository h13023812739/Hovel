package org.hyq.hovel.aop;

import org.hyq.hovel.annotation.SharedDataSource;
import org.hyq.hovel.config.DataSourceType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAop {
    @Before("@annotation(dataSource)")
    public void changeDataSource( SharedDataSource dataSource) {
        String type = dataSource.value();
        if (DataSourceType.DataBaseType.Master.getName().equals(type)){
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Master);
        } else if (DataSourceType.DataBaseType.Slave.getName().equals(type)){
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Slave);
        } else {
            DataSourceType.setDataBaseType(DataSourceType.DataBaseType.Master);
        }
    }

    @After("@annotation(dataSource)")
    public void restoreDataSource(JoinPoint point, SharedDataSource dataSource) {
        // 注意清除，不影响其他使用默认数据源master
        DataSourceType.clearDataBaseType();
    }
}
