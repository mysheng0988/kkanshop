package com.mysheng.office.kkanshop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by myaheng on 2018/7/12.
 */

public class UtilDate {
    public static String showDate(Date date,String pattern){
        String  dateStr=format(date,pattern);
        String year = dateStr.substring(0,4);
        Long yearNum =Long.parseLong(year);
        int month = Integer.parseInt(dateStr.substring(5,7));
        int day = Integer.parseInt(dateStr.substring(8,10));
        String hour = dateStr.substring(11,13);
        String minute = dateStr.substring(14,16);

        long addtime =date.getTime();
        long today = System.currentTimeMillis();//当前时间的毫秒数
        Date now = new Date(today);
        String  nowStr=format(now,pattern);
        int  nowDay = Integer.parseInt(nowStr.substring(8,10));
        String result="";
        long l=today-addtime;//当前时间与给定时间差的毫秒数
        long days=l/(24*60*60*1000);//这个时间相差的天数整数，大于1天为前天的时间了，小于24小时则为昨天和今天的时间
        long hours=(l/(60*60*1000)-days*24);//这个时间相差的减去天数的小时数
        long min=((l/(60*1000))-days*24*60-hours*60);//
        long s=(l/1000-days*24*60*60-hours*60*60-min*60);
        String second=s>10?s+"":"0"+s;
        if(days > 0){
            if(days>0 && days<2){
                result ="前天 "+hour+":"+minute+":"+second;
            } else {
                result = year+"年"+month+"月 "+day+"日"+hour+":"+minute+":"+second;
            }
        }else if(hours > 0 ) {
            if(day!=nowDay){
                result = "昨天 "+hour+":"+minute+":"+second;
            }else{
                result="今天 "+hour+":"+minute+":"+second;
            }
        }else if(min>0){
            result=min+"分钟前";
        }else{
            result="刚刚";
        }
        return result;
    }

    /**
     * 日期格式化
     * @param date      需要格式化的日期
     * @param pattern   时间格式，如：yyyy-MM-dd HH:mm:ss
     * @return          返回格式化后的时间字符串
     */
    public static String format(Date date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    /**
    * 字符串转换成日期
    * @param str
    * @return date
    */
     public static Date StrToDate(String str) {

       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date date = null;
         try {
             date = format.parse(str);
         } catch (ParseException e) {
             e.printStackTrace();
         }

         return date;
     }
}
