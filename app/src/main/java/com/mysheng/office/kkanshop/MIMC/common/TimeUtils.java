package com.mysheng.office.kkanshop.MIMC.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by houminjiang on 18-1-19.
 */

public class TimeUtils {

    public static long local2UTC(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new GregorianCalendar(year, month, day, hour, minute).getTime());

        return calendar.getTimeInMillis();
    }

    public static String utc2Local(long utcDateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(utcDateTime));
    }
}
