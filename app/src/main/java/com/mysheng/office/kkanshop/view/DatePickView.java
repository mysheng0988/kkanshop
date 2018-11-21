package com.mysheng.office.kkanshop.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.util.UtilsDate;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;


/**
 * pickView
 * Created by kerui on 2016/11/27.
 */


public class DatePickView extends RelativeLayout implements NumberPicker.OnValueChangeListener {
    private NumberPicker dateYear;
    private NumberPicker dateMonth;
    private NumberPicker dateDay;
    private NumberPicker dateHour;
    private int year;
    private int month;
    private int day;
    private String time;
    private String[] stringsTimes = {"0:00", "0:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30", "5:00", "5:30", "6:00", "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"};

    public void setReturnTimeListener(ReturnTimeListener returnTimeListener) {
        this.returnTimeListener = returnTimeListener;
    }

    private ReturnTimeListener returnTimeListener;

    public DatePickView(Context context) {
        super(context);
    }

    public DatePickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        initListener();
        initPicker();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_date_picker, this);
        dateYear = view.findViewById(R.id.dateYear);
        dateMonth = view.findViewById(R.id.dateMonth);
        dateDay = view.findViewById(R.id.dateDay);
        dateHour = view.findViewById(R.id.dateHour);

        setNumberPickerDividerColor(dateYear);
        setNumberPickerDividerColor(dateMonth);
        setNumberPickerDividerColor(dateDay);
        setNumberPickerDividerColor(dateHour);
        dateYear.setMinValue(1990);
        dateYear.setMaxValue(2099);

        dateMonth.setMinValue(1);
        dateMonth.setMaxValue(12);

        dateDay.setMinValue(1);
        dateDay.setMaxValue(31);


        dateHour.setDisplayedValues(stringsTimes);
        dateHour.setMinValue(0);
        dateHour.setMaxValue(stringsTimes.length - 1);
        dateHour.setValue(4);


        dateYear.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        dateHour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        dateDay.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        dateHour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


    }

    private void initPicker() {
        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);  //刻度值 实际： now.get(Calendar.YEAR)
        int nowMonth = now.get(Calendar.MONTH) + 1;       //刻度值 实际： now.get(Calendar.MONTH) + 1
        int nowDay = now.get(Calendar.DAY_OF_MONTH);  //刻度值 实际： now.get(Calendar.DAY_OF_MONTH
        int nowHour = now.get(Calendar.HOUR_OF_DAY) * 2; //刻度值 实际：now.get(Calendar.HOUR_OF_DAY)


        dateYear.setValue(nowYear);
        dateMonth.setValue(nowMonth);
        dateDay.setValue(nowDay);
        dateHour.setValue(nowHour);

        year = dateYear.getValue();
        month = dateMonth.getValue();
        monthDays(month);
        day = dateDay.getValue();
        time = stringsTimes[dateHour.getValue()];

        if (returnTimeListener != null) {
            returnTimeListener.returnTime(jointTime());
        }
    }

    public String getDisplayTime(){
        return year+"-"+month+"-"+day+" "+time;
    }
    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值 透明
                    pf.set(numberPicker, new ColorDrawable(this.getResources().getColor(android.R.color.transparent)));
                } catch (IllegalArgumentException | Resources.NotFoundException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void initListener() {
        dateYear.setOnValueChangedListener(this);
        dateMonth.setOnValueChangedListener(this);
        dateDay.setOnValueChangedListener(this);
        dateHour.setOnValueChangedListener(this);

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()) {
            case R.id.dateYear:
                year = newVal;
                if (leapYear(year)&&month==2) {
                    dateDay.setMinValue(1);
                    dateDay.setMaxValue(29);
                } else if (!leapYear(year)&&month==2) {
                    dateDay.setMinValue(1);
                    dateDay.setMaxValue(28);
                }
                if (returnTimeListener != null) {
                    returnTimeListener.returnTime(jointTime());
                }

                break;
            case R.id.dateMonth:
                month = newVal;
                monthDays(month);
                if (returnTimeListener != null) {
                    returnTimeListener.returnTime(jointTime());
                }
                break;
            case R.id.dateDay:
                day = newVal;
                if (returnTimeListener != null) {
                    returnTimeListener.returnTime(jointTime());
                }
                break;
            case R.id.dateHour:
                time = stringsTimes[picker.getValue()];
                if (returnTimeListener != null) {
                    returnTimeListener.returnTime(jointTime());
                }
                break;
            default:
                break;


        }

    }

    /**
     * 拼接时间
     */
    private long jointTime() {
        String stringBuffer = String.valueOf(year) + "-" +
                month + "-" +
                day + " " +
                time;
        //Date date = DateUtils.getDateByFormat(DateUtils.SimpleDateFormatForHouse, stringBuffer);
        Date date= UtilsDate.StrToDate(stringBuffer,"yyyy-MM-dd HH:mm");
        assert date != null;
        return date.getTime();
    }

    public interface ReturnTimeListener {
        void returnTime(long time);
    }

    /**
     * 闰年
     *
     * @param year 年
     * @return 是否为平年
     */
    private boolean leapYear(int year) {

        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {

                    return true;// "\(year) is a leap year"
                } else {

                    return false;// "\(year) is not a leap year"
                }
            } else {

                return true;// "\(year) is a leap year"
            }

        }
        return false;
    }

    /**
     * 月份天数
     *
     * @param m 月份
     */
    private void monthDays(int m) {
        if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
            dateDay.setMinValue(1);
            dateDay.setMaxValue(31);
        } else if (m == 4 || m == 6 || m == 9 || m == 11) {
            dateDay.setMinValue(1);
            dateDay.setMaxValue(30);
        } else if (m == 2) {
            if (leapYear(year)) {
                dateDay.setMinValue(1);
                dateDay.setMaxValue(29);
            } else if (!leapYear(year)) {
                dateDay.setMinValue(1);
                dateDay.setMaxValue(28);
            }
        }

    }


}
