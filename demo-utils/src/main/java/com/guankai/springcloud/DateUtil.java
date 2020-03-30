package com.guankai.springcloud;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author: guan.kai
 * @date: 2020/3/30 16:26
 **/
public class DateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** 常用日期格式 */
    private static final String[] FORMAT_ARR = new String[]{"yyyyMM","yyyy-MM","yyyy/MM","yyyyMMdd","yyyy-MM-dd","yyyy/MM/dd",
                                                "yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss"};

    /**
     * 获取指定时间
     *
     * @param year 年
     * @param mouth 月
     * @param day 日
     * @param hour 时
     * @param minute 分
     * @param second 秒
     * @return
     */
    public static Date getAppointedDatetime(Integer year,Integer mouth,Integer day, Integer hour, Integer minute,Integer second) {
        Calendar cal = Calendar.getInstance();
        if (year != null) {
            cal.set(Calendar.YEAR,year);
        }
        if (mouth != null) {
            cal.set(Calendar.MONTH,mouth-1);
        }
        if (day != null) {
            cal.set(Calendar.DAY_OF_MONTH,day);
        }
        if (hour != null) {
            cal.set(Calendar.HOUR_OF_DAY,hour);
        }
        if (minute != null) {
            cal.set(Calendar.MINUTE,minute);
        }
        if (second != null) {
            cal.set(Calendar.SECOND,second);
        }
        return cal.getTime();
    }

    /**
     * 将日期根据指定格式转字符串
     *
     * @param date 日期
     * @param format 格式
     * @return
     */
    public static String date2str(Date date,String format) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isBlank(format)) {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 将日期字符串根据指定格式反转为日期
     *
     * @param date 日期字符串
     * @param format 格式
     * @return
     */
    public static Date str2date(String date,String format) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        if (StringUtils.isBlank(format)) {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(),e);
            return null;
        }
    }
}
