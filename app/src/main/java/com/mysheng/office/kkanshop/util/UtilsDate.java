package com.mysheng.office.kkanshop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by myaheng on 2018/11/2.
 */

public class UtilsDate {

    /**
     *时间格式
     */
    public final static String FORMAT_TIME = "HH:mm";
    public final static String FORMAT_TIME_SECOND = "HH:mm:ss";
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    public final static String FORMAT_DATE_ALL = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_MONTH_DAY_TIME = "MM-dd HH:mm";
    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_MONTH_DAY = "MM-dd";

    /**
     * 将日期转字符串
     * @param date      需要格式化的日期
     * @param format   时间格式，如：yyyy-MM-dd HH:mm:ss
     * @return          返回格式化后的时间字符串
     */
    public static String DateToStr(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    /**
     * 字符串转换成日期
     * @param str
     * @param format   时间格式，如：yyyy-MM-dd HH:mm:ss
     * @return date
     */
    public static Date StrToDate(String str,String format) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 日期字符转自定义日期格式字符串
     * @param str
     * @param format 日期的常见格式字符串
     * @return
     */
    public static String StrToCustomStrDate(String str,String format) {

        SimpleDateFormat sf1 = new SimpleDateFormat(FORMAT_DATE_ALL);
        SimpleDateFormat sf2 = new SimpleDateFormat(format);
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    /**
     * 返回月和日 eg：11-01
     * @param str 日期字符串
     * @return  月和日
     */
    public static String formatDateToMD(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat(FORMAT_DATE);
        SimpleDateFormat sf2 = new SimpleDateFormat(FORMAT_MONTH_DAY);
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    /**
     * @param str 字符串日期
     * @return 返回日期：eg：2018-11-01
     */
    public static String formatDateToYMD(String str) {
        SimpleDateFormat sf = new SimpleDateFormat(FORMAT_DATE);
        String formatStr = "";
        try {
            formatStr = sf.format(sf.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    /**
     * 获取相差几天的日期
     * @param strDate 参考日期
     * @param day 前后几天
     * @return
     */
    public static String getDifferDate(String strDate, int day) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date=sf1.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return sf1.format(now.getTime());
    }

    /**
     * 多久之前创建
     * @param strDate
     * @return
     */
    public static String showDateBefore(String strDate){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month=now.get(Calendar.MONTH);
        int day=now.get(Calendar.DAY_OF_MONTH);
        int hour=now.get(Calendar.HOUR_OF_DAY);
        int minute=now.get(Calendar.DAY_OF_MONTH);
        int second=now.get(Calendar.DAY_OF_MONTH);
        Calendar strCal = Calendar.getInstance();
        Date date=StrToDate(strDate,FORMAT_DATE_ALL);
        strCal.setTime(date);
        int yearStr = strCal.get(Calendar.YEAR);
        int monthStr=strCal.get(Calendar.MONTH);
        int dayStr=strCal.get(Calendar.DAY_OF_MONTH);
        int hourStr=strCal.get(Calendar.HOUR_OF_DAY);
        int minuteStr=strCal.get(Calendar.DAY_OF_MONTH);
        int secondStr=strCal.get(Calendar.DAY_OF_MONTH);
        if((year-yearStr!=0)&&(month-monthStr!=0)){
          return   StrToCustomStrDate(strDate,FORMAT_DATE_TIME);
        }else{
            if(Math.abs(day-dayStr)==0){
                int hourNum=Math.abs(hour-hourStr);
                if(hourNum==0){
                    int minuteNum=Math.abs(minute-minuteStr);
                    if(minuteNum==0){
                        int secondNum=second-secondStr;
                        if(secondNum<10){
                            return "刚刚";
                        }else {
                            return secondNum+"秒前";
                        }
                    }else if(minuteNum>30){
                        return "半时前";
                    }else {
                        return minuteNum+"分钟前";
                    }
                }else{
                    return hourNum+"小时前";
                }

            }else if(Math.abs(day-dayStr)==1){
                return "昨天"+StrToCustomStrDate(strDate,FORMAT_TIME);
            }else if(Math.abs(day-dayStr)==2){
                return "前天"+StrToCustomStrDate(strDate,FORMAT_TIME);
            }else{
                return   StrToCustomStrDate(strDate,FORMAT_DATE_TIME);
            }
        }


    }

    /**
     * 显示数据的创建的时间
     * @param strDate
     * @return
     */
    public static String showDateBeforeTime(String strDate){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month=now.get(Calendar.MONTH);
        int day=now.get(Calendar.DAY_OF_MONTH);
        Calendar strCal = Calendar.getInstance();
        Date date=StrToDate(strDate,FORMAT_DATE_ALL);
        strCal.setTime(date);
        int yearStr = strCal.get(Calendar.YEAR);
        int monthStr=strCal.get(Calendar.MONTH);
        int dayStr=strCal.get(Calendar.DAY_OF_MONTH);
        if((year-yearStr!=0)&&(month-monthStr!=0)){
            return   StrToCustomStrDate(strDate,FORMAT_DATE_TIME);
        }else{
            switch (day-dayStr){
                case 0:
                    return "今天"+StrToCustomStrDate(strDate,FORMAT_TIME);
                case 1:
                    return "昨天"+StrToCustomStrDate(strDate,FORMAT_TIME);
                case 2:
                    return "昨天"+StrToCustomStrDate(strDate,FORMAT_TIME);
                default:
                    return   StrToCustomStrDate(strDate,FORMAT_DATE_TIME);

            }
        }

    }

    /**
     * 获取当前月的第一天
     */
    public static String getMonthFirstDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
       return format.format(c.getTime());
    }
    /**
     * 获取当前月的最后一天
     */
    public static String getMonthLastDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return  format.format(ca.getTime());
    }
}
