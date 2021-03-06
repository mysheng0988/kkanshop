package com.mysheng.office.kkanshop.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;
import org.apache.commons.lang.time.DurationFormatUtils;

/**
 * Created by myaheng on 2018/11/19.
 */

@SuppressLint("AppCompatCustomView")
public class CountDownTextView extends TextView {
    private CountDownTimer countDownTimer;
    private String mTimeStr;
    private String mTimePattern = "HH:mm:ss";
    private  OnFinish onFinish;
    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public void initData(long millisInFuture){
        countDownTimer=new CountDownTimer(millisInFuture,1000){
            @Override
            public void onFinish() {
                if(onFinish!=null){
                    onFinish.countDownFinish();
                }
            }

            @Override
            public void onTick(long millisUntilFinished) {
                mTimeStr = DurationFormatUtils.formatDuration(millisUntilFinished,mTimePattern);
                setText(mTimeStr);
            }
        };
        startTimer();

    }
    public void cancelTimer() {
        if(countDownTimer!=null)
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
