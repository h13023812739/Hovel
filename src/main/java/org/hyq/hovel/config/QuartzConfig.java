package org.hyq.hovel.config;

import cn.hutool.core.date.ChineseDate;
import org.hyq.hovel.quartz.job.DateTimePrintJob;
import org.hyq.hovel.quartz.job.DateTimePrintJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail printTimeJobDetail() {
        return JobBuilder.newJob(DateTimePrintJob.class)
                .withIdentity("JobDetail-1")
                .usingJobData("key1","l am key 1")
                .usingJobData("key2","l am key 2")
                .storeDurably(true)
                .build();
    }

    @Bean
    public Trigger printTimeJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(printTimeJobDetail())//关联上述的JobDetail
                .withIdentity("quartzTaskService")//给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
    }

    private static String formatDateByPattern(Date date, String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * Date转为cron表达式
     * @param date
     * @return
     */
    public static String getCron(Date date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }

    public static void main(String[] args) {
        Date ddate = new Date();
        System.out.println(getCron(ddate));

        LocalDate localDate = LocalDate.now();
        ChineseDate date = new ChineseDate(2023, 1, 4);
        ChineseDate ldate = new ChineseDate(localDate);
        System.out.println(date);
        System.out.println(date.getCyclical());
        System.out.println(date.getCyclicalYMD());
        System.out.println(date.getGregorianDate());

    }


}

