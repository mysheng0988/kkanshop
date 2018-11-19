package com.mysheng.office.kkanshop.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import org.apache.commons.lang.time.DurationFormatUtils;

/**
 * Created by myaheng on 2018/11/19.
 */

public class AppCountDownTimer extends CountDownTimer {

    private Context mContext;
    protected TextView mDateTv;
    private String mTimePattern = "HH:mm:ss";
    private String mTimeStr;
    public AppCountDownTimer(long millisInFuture, long countDownInterval,String format,TextView textView) {
        super(millisInFuture, countDownInterval);
        this.mDateTv=textView;
        this.mTimePattern=format;

    }
    public AppCountDownTimer(long millisInFuture, long countDownInterval,TextView textView) {
        super(millisInFuture, countDownInterval);
        this.mDateTv=textView;

    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTimeStr = DurationFormatUtils.formatDuration(millisUntilFinished, mTimePattern);
        mDateTv.setText(mTimeStr);
    }


    @Override
    public void onFinish() {

    }
}
