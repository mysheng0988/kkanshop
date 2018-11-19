package com.mysheng.office.kkanshop.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mysheng.office.kkanshop.util.AppCountDownTimer;

import org.apache.commons.lang.time.DurationFormatUtils;

/**
 * Created by myaheng on 2018/11/19.
 */

@SuppressLint("AppCompatCustomView")
public class CountDownTextView extends TextView {
    private AppCountDownTimer countDownTimer;
    private String mTimeStr;
    private String mTimePattern = "HH:mm:ss";
    private  OnFinish onFinish;
    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public void initData(final long millisInFuture){
        countDownTimer=new AppCountDownTimer(millisInFuture,1000,this){
            @Override
            public void onFinish() {
                super.onFinish();
                if(onFinish!=null){
                    onFinish.countDownFinish();
                }
            }

            @Override
            public void onTick(long millisUntilFinished) {
                mTimeStr = DurationFormatUtils.formatDuration(millisUntilFinished, mTimePattern);
                setText(mTimeStr);
            }
        };

    }
    public void cancelTimer() {
        countDownTimer.cancel();
    }

    public void startTimer() {
        countDownTimer.start();
    }

    public String getmTimeStr() {
        return mTimeStr;
    }

    public void setOnFinish(OnFinish onFinish) {
        this.onFinish = onFinish;
    }

    public interface OnFinish{
        /**
         * 倒计时结束回调
         */
        public void countDownFinish();

    }
}
