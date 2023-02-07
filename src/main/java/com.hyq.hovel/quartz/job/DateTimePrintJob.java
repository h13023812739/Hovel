package com.hyq.hovel.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class DateTimePrintJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println(jobExecutionContext.getJobDetail().getJobDataMap().get("key1")+"=========================="+dateFormat.format(Calendar.getInstance().getTime()));
        System.out.println(jobExecutionContext.getJobDetail().getJobDataMap().get("key2")+"=========================="+LocalDate.now());

    }
}
