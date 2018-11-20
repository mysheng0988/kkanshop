package com.mysheng.office.kkanshop.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtils 类
 */
public class DateUtils {
    public static String SimpleDateFormatString = "yyyy-MM-dd";
    public static String SimpleDateFormatForHouse = "yyyy-MM-dd HH:mm";
    public static String SimpleDateFormatForHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static String SimpleDateFormatForHM = "HH:mm";
    public static String SimpleDateFormatForHMS = "HH:mm:ss";
    public static String SimpleDateFormatForEEEENOYYYY = "MM月dd日 EEEE HH:mm";
    public static String SimpleDateFormatForEEEE = "yyyy年MM月dd日 EEEE HH:mm";
    public static String SimpleDateFormatForMMDDHHMM = "MM-dd HH:mm";
    public static String SimpleDateFormatForMMDDEEEE = "MM-dd EEEE";
    public static String SimpleDateFormatForYYYYMMDDEEEE = "yyyy-MM-dd EEEE";

    /**
     * yyyy-MM-dd
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(SimpleDateFormatString);
        return format.format(date);
    }

    /**
     * 自定义时间格式
     *
     * @param time         long
     * @param FormatString 自定义格式
     * @return
     */
    public static String returnCustomTime(long time, String FormatString) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(FormatString);
        return format.format(date);
    }



    public static String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(SimpleDateFormatForHouse);
        return format.format(date);
    }

    public static String getDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(SimpleDateFormatForHouse);
        return format.format(date);
    }

    public static String getTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(SimpleDateFormatForHMS);
        return format.format(date);
    }


    /**
     * 同一天判断
     */
    private static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        return date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate();
    }

    /**
     * 是否同一年
     */
    private static boolean isSameYear(Date date1, Date date2) {
        return date1.getYear() == date2.getYear();
    }

    public static String displayDatePicker(Date date) {
        if (isSameYear(date, new Date())) {
            SimpleDateFormat format = new SimpleDateFormat(SimpleDateFormatForMMDDEEEE);
            return format.format(date);
        } else {
            SimpleDateFormat format = new SimpleDateFormat(SimpleDateFormatForYYYYMMDDEEEE);
            return format.format(date);
        }
    }

    public static Date getDateByFormat(String format, String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 显示间隔的整天
     * 1.当天显示今天
     * 2.[ n 天前]
     */
    public static String displayDateDistance(long date) {
        return displayDateDistance(new Date(date));
    }

    /**
     * 显示间隔的整天
     * 1.当天显示今天
     * 2.[ n 天前]
     */
    public static String displayDateDistance(Date date) {
        Date currentDate = new Date();
        currentDate.setHours(0);
        currentDate.setMinutes(0);
        currentDate.setSeconds(0);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        long distance = (currentDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);
        if (distance == 0) {
            return "今天";
        } else {
            return distance + "天前";
        }
    }


}

