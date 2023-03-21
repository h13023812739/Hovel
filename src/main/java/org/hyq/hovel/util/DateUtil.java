package org.hyq.hovel.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @Classname DateUtil
 * @Description 时间处理工具类
 * @Date 2020/8/7 9:40
 * @Author Fan
 */
public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    // 用来全局控制 上一周，本周，下一周的周数变化
    private static int weeks = 0;

    private static int MaxDate;// 一月最大天数

    private static int MaxYear;// 一年最大天数

    private static String defaultDatePattern = null;
    private static String timePattern = "HH:mm";

    /**
     * 日期格式yyyy-MM字符串常量
     */
    public static final String MONTH_FORMAT = "yyyy-MM";
    /**
     * 日期格式yyyyMM字符串常量
     */
    public static final String MONTH_FORMAT_SHORT = "yyyyMM";
    /**
     * 日期格式yyyy-MM-dd字符串常量
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_NEW = "yyyy/MM/dd";
    public static final String DATE_FORMAT_DIT = "yyyy.MM.dd";
    /**
     * 日期格式yyyyMMdd字符串常量
     */
    public static final String DATE_FORMAT_SHORT = "yyyyMMdd";
    /**
     * 日期格式yyyyMMddHHmm字符串常量
     */
    public static final String DATE_FORMAT_LONG = "yyyyMMddHHmm";

    public static final String DATE_FORMAT_MD = "MMdd";

    public static final String DATE_FORMAT_CHN_MD = "M月dd日";

    /**
     * 日期格式yyyy MM dd字符串常量
     */
    public static final String DATE_FORMAT_BANK = "yyyy MM dd";


    /**
     * 日期格式HH:mm:ss字符串常量
     */
    public static final String HOUR_FORMAT = "HH:mm:ss";
    /**
     * 日期格式yyyy-MM-dd HH:mm:ss字符串常量
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式yyyy-MM-dd HH:mm:ss字符串常量
     */
    public static final String DATETIME_FORMAT1 = "yyyy-MM-dd 00:00:00";
    /**
     * 日期格式yyyy-MM-dd HH:mm:ss字符串常量
     */
    public static final String DATETIME_FORMAT0 = "yyyy-MM-dd 01:00:00";
    /**
     * 日期格式yyyy-MM-dd HH:mm:ss字符串常量
     */
    public static final String DATETIME_FORMAT2 = "yyyy-MM-dd 23:59:59";
    /**
     * 格式化到小时
     */
    public static final String HOUR_DATETIME_FORMAT = "yyyy-MM-dd HH";

    /**
     * 日期格式yyyy-MM-dd HH:mm:ss字符串常量
     */
    public static final String MILLISECOND_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 日期格式yyyy-MM-dd HH:mm:ss字符串常量
     */
    public static final String MILLI3SECOND_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 日期格式yyyy-MM-dd HH:mm:ss字符串常量
     */
    public static final String YYYYMMDDHHMMSS_FORMAT = "yyyyMMddHHmmss";

    public static final String YYYYMMDDHHMMSSSSS_FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * 某天开始时分秒字符串常量 00:00:00
     */
    public static final String DAY_BEGIN_STRING_HHMMSS = " 00:00:00";
    /**
     * 某天结束时分秒字符串常量 23:59:59
     */
    public static final String DAY_END_STRING_HHMMSS = " 23:59:59";
    private static SimpleDateFormat sdf_date_format = new SimpleDateFormat(DATE_FORMAT);

    private static SimpleDateFormat sdf_date_format_bank = new SimpleDateFormat(DATE_FORMAT_BANK);

    private static SimpleDateFormat sdf_hour_format = new SimpleDateFormat(HOUR_FORMAT);
    private static SimpleDateFormat sdf_datetime_format = new SimpleDateFormat(DATETIME_FORMAT);
    private static SimpleDateFormat sdf_datetime_format2 = new SimpleDateFormat(YYYYMMDDHHMMSS_FORMAT);

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    //LocalDate -> Date
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    //LocalDateTime -> Date
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime string2LocalDateTime(String dateString, String df) {
        try {
            return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(df));
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static String localDateTime2String(LocalDateTime localDateTime, String df) {
        try {
            return DateTimeFormatter.ofPattern(df).format(localDateTime);
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static LocalDate string2LocalDate(String dateString, String df) {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(df));
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static long calTowDateOfDay(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        return day;
    }

    public static long calTowDateOfHour(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少小时
        long hour = diff % nd / nh;
        return hour;
    }

    public static long calTowDateOfMin(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        return min;
    }

    public static long calTowDateOfSecond(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少分钟
        long second = diff % nd % nh % nm / ns;
        return second;
    }

    public static String calTime(Date nowDate, Date create_time) {
        String subTime = "";
        long min = 0;
        long day;
        long hour = 0;
        long second = DateUtil.calTowDateOfSecond(nowDate, create_time);
        min = DateUtil.calTowDateOfMin(nowDate, create_time);
        hour = DateUtil.calTowDateOfHour(nowDate, create_time);
        day = DateUtil.calTowDateOfDay(nowDate, create_time);
        if (day > 0) {
            subTime = day + "天前";
        } else if (hour > 0) {
            subTime = hour + "小时前";
        } else if (min > 0) {
            subTime = min + "分钟前";
        } else if (second > 0) {
            subTime = second + "秒前";
        }
        return subTime;
    }

    public DateUtil() {
    }


    /**
     * 自定义格式化日期,
     *
     * @param format
     * @param date
     * @return
     */
    public static String formatDateTime(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.format(date.getTime());
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getDateTime():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器当前日期及时间，以格式为：yyyy-MM-dd HH:mm:ss的日期字符串形式返回
     *
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static String getDateTime() {
        try {
            return sdf_datetime_format.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getDateTime():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器当前日期，以格式为：yyyy-MM-dd的日期字符串形式返回
     *
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static String getDate() {
        try {
            return sdf_date_format.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getDate():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器当前日期，以格式为：yyyy MM dd的日期字符串形式返回
     *
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static String getDateWithBank() {
        try {
            return sdf_date_format_bank.format(Calendar.getInstance().getTime());
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getDateWithBank():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器当前时间，以格式为：HH:mm:ss的日期字符串形式返回
     *
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static String getTime() {
        String temp = " ";
        try {
            temp += sdf_hour_format.format(Calendar.getInstance().getTime());
            return temp;
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getTime():" + e.getMessage());
            return "";
        }
    }

    /**
     * 统计时开始日期的默认值
     *
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static String getStartDate() {
        try {
            return getYear() + "-01-01";
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getStartDate():" + e.getMessage());
            return "";
        }
    }

    /**
     * 统计时结束日期的默认值
     *
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static String getEndDate() {
        try {
            return getDate();
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getEndDate():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器当前日期的年份
     *
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static String getYear() {
        try {
            return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getYear():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器当前日期的年份
     *
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static Date getLongAgoYear() {
        Date date = null;
        try {

            Calendar calendar = Calendar.getInstance();
            calendar.set(1900, 0, 0, 00, 00, 00);
            date = calendar.getTime();
            return date;
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getYear():" + e.getMessage());

            return null;
        }
    }


    /**
     * 获得服务器当前日期的月份
     *
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static String getMonth() {
        try {
            java.text.DecimalFormat df = new java.text.DecimalFormat();
            df.applyPattern("00;00");
            return df.format((Calendar.getInstance().get(Calendar.MONTH) + 1));
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getMonth():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器在当前月中天数
     *
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static String getDay() {
        try {
            return String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getDay():" + e.getMessage());
            return "";
        }
    }

    /**
     * 比较两个日期相差的天数
     *
     * @param date1
     * @param date2
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static int getMargin(String date1, String date2) {
        int margin;
        try {
            ParsePosition pos = new ParsePosition(0);
            ParsePosition pos1 = new ParsePosition(0);
            Date dt1 = sdf_date_format.parse(date1, pos);
            Date dt2 = sdf_date_format.parse(date2, pos1);
            long l = dt1.getTime() - dt2.getTime();
            margin = (int) (l / (24 * 60 * 60 * 1000));
            return margin;
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getMargin():" + e.toString());
            return 0;
        }
    }

    /**
     * 比较两个日期相差的天数
     *
     * @param date1
     * @param date2
     * @return
     * @author dylan_xu
     * @date Mar 11, 2012
     */
    public static double getDoubleMargin(String date1, String date2) {
        double margin;
        try {
            ParsePosition pos = new ParsePosition(0);
            ParsePosition pos1 = new ParsePosition(0);
            Date dt1 = sdf_datetime_format.parse(date1, pos);
            Date dt2 = sdf_datetime_format.parse(date2, pos1);
            long l = dt1.getTime() - dt2.getTime();
            margin = (l / (24 * 60 * 60 * 1000.00));
            return margin;
        } catch (Exception e) {
            LOGGER.debug("DateUtil.getMargin():" + e.toString());
            return 0;
        }
    }

    /**
     * 获得当前时间，格式yyyy-MM-dd hh:mm:ss
     *
     * @param
     * @return
     */
    public static String getCurrentDate() {
        return getCurrentDate(DATETIME_FORMAT);
    }

    /**
     * 获得当前时间，格式自定义
     *
     * @param format
     * @return
     */
    public static String getCurrentDate(String format) {
        if (StringUtils.isNotBlank(format)) {
            format = DATETIME_FORMAT;
        }
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, 0);
        SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
        String date = sdf.format(day.getTime());
        return date;
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
    public static String getCron(java.util.Date  date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }

}