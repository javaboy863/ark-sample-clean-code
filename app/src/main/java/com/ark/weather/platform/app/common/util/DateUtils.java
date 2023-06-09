package com.ark.weather.platform.app.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目中日期处理方法
 *
 */
@Slf4j
public final class DateUtils {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyyMMddHHmm";
    public static final String YYYY_MM_DD_HH = "yyyyMMddHH";
    public static final String YYYY_MM_DD = "yyyyMMdd";

    /**
     * 日期格式化对象缓存容器
     */
    private static final Map<String, DateFormat> DATE_FORMATTER_MAPPER = new HashMap<>();

    /**
     * 获取缓存日期格式化对象
     *
     * @param patten
     * @return
     */
    private static DateFormat cacheFormatterAndGet(String patten) {
        DateFormat dateFormat = DATE_FORMATTER_MAPPER.get(patten);
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(patten);
            DATE_FORMATTER_MAPPER.put(patten, dateFormat);
        }
        return dateFormat;
    }

    /**
     * 格式化日期成为给定模板字符串
     *
     * @param date   日期
     * @param patten 格式化字符串
     * @return
     */
    public static String format(Date date, String patten) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(patten);
            return dateFormat.format(date);
        } catch (Exception e) {
            log.error("格式日期错误", e);
        }
        return null;
    }

    /**
     * 格式化日期成为给定模板字符串
     *
     * @param dateStr 日期字符串
     * @param patten  格式化字符串
     * @return
     */
    public static Date parse(String dateStr, String patten) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(patten);
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            log.error("转换日期错误", e);
        }
        return null;
    }

    /**
     * 添加天数
     *
     * @param date
     * @param dayNum
     * @return
     */
    public static Date plusDay(Date date, Integer dayNum) {
        if (dayNum == null || dayNum == 0 || date == null) {
            return date;
        }
        return plus(date, Calendar.DATE, dayNum);
    }

    /**
     * 添加指定日期维度的数量
     *
     * @param date 原日期
     * @param field  例如： Calendar.DAY_OF_MONTH
     * @param num
     * @return
     */
    public static Date plus(Date date, int field, Integer num) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(field, num);

        return ca.getTime();
    }
}
