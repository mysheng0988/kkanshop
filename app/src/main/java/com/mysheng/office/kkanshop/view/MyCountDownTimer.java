package com.mysheng.office.kkanshop.view;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;

import org.apache.commons.lang.time.DurationFormatUtils;

import java.util.ArrayList;
import java.util.List;

import static android.text.style.DynamicDrawableSpan.ALIGN_BASELINE;

/**
 * Created by myaheng on 2018/11/19.
 */

public class MyCountDownTimer extends CountDownTimer {

    private Context mContext;
    protected TextView mDateTv;
    private String mTimePattern = "HH:mm:ss";
    private String mTimeStr;
    private int mDrawableId;
    private boolean flag = false;
    protected String[] numbers;//此数组用于生存每个倒计时字符拆分后的天,时,分,秒的数值
    protected List mBackSpanList;
    protected List mTextColorSpanList;
    //用于倒計時樣式的內間距,字體大小,字體顏色,倒計時間隔的顏色
    private int mSpanPaddingLeft, mSpanPaddingRight, mSpanPaddingTop, mSpanPaddingBottom;
    private int mSpanTextSize;
    private int mSpanTextColor;
    protected int mGapSpanColor;
    private SpannableString mSpan;


    public MyCountDownTimer(Context context, long millisInFuture, long countDownInterval, String timePattern, int drawableId, TextView textView) {
        super(millisInFuture, countDownInterval);
        mContext = context;
        mTimePattern = timePattern;
        mDrawableId = drawableId;
        mBackSpanList = new ArrayList<>();
        mTextColorSpanList = new ArrayList<>();
        mDateTv = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        //此处也是一个核心所在,DurationFormatUtils.formatDuration这是apache的org.apache.commons的lang里面的,所以还需要引入它的jar包
        mTimeStr = DurationFormatUtils.formatDuration(millisUntilFinished, mTimePattern);
        setBackgroundSpan(mTimeStr);
    }

    @Override
    public void onFinish() {

    }

    public void setBackgroundSpan(String timeStr) {
        if (!flag) {
            initSpanData(timeStr);
            flag = true;
        }
        int mGapLen = 1;
        mSpan = new SpannableString(timeStr);
        for (int i = 0; i < mBackSpanList.size(); i++) {
            int start = i * numbers[i].length() + i * mGapLen;
            int end = start + numbers[i].length();
            //为数字设置ImageSpan
            setContentSpan(mSpan, mBackSpanList.get(i), start, end);
            if (i < mTextColorSpanList.size()) {//这里为了就是防止12:36:27这种样式，这种样式距离只有２个以是须要做断定，防止数组越界
                setContentSpan(mSpan, mTextColorSpanList.get(i), end, end + mGapLen);
            }
        }
        mDateTv.setMovementMethod(LinkMovementMethod.getInstance());//此要领很主要须要挪用，不然绘制出来的倒计时就是重叠的样式
        mDateTv.setText(mSpan);
        Log.d("mDateTv", "setBackgroundSpan: "+mDateTv.getText().toString());
    }

    public void initSpanData(String timeStr) {
        numbers = getNumInTimerStr(timeStr);
        for (int i = 0; i < numbers.length; i++) {
            BackgroundSpan backgroundColorSpan = new BackgroundSpan(ContextCompat.getDrawable(mContext, mDrawableId), ALIGN_BASELINE);
            initBackSpanStyle(backgroundColorSpan);
            mBackSpanList.add(backgroundColorSpan);
        }
    }

    protected void initBackSpanStyle(BackgroundSpan mBackSpan) {
        mBackSpan.setTimerPadding(mSpanPaddingLeft, mSpanPaddingTop, mSpanPaddingRight, mSpanPaddingBottom);
        mBackSpan.setTimerTextColor(mSpanTextColor);
        mBackSpan.setTimerTextSize(mSpanTextSize);
    }


    public void cancelTimer() {
        this.cancel();
    }

    public void startTimer() {
        this.start();
    }

    public String getmTimeStr() {
        return mTimeStr;
    }

    public MyCountDownTimer setTimerTextSize(int textSize) {
        this.mSpanTextSize = textSize;
        return this;
    }

    public MyCountDownTimer setTimerPadding(int left, int top, int right, int bottom) {
        this.mSpanPaddingLeft = left;
        this.mSpanPaddingBottom = bottom;
        this.mSpanPaddingRight = right;
        this.mSpanPaddingTop = top;
        return this;
    }

    public MyCountDownTimer setTimerTextColor(int color) {
        this.mSpanTextColor = color;
        return this;
    }

    public MyCountDownTimer setTimerGapColor(int color) {
        this.mGapSpanColor = color;
        return this;
    }
    //得到倒计时数字部分
    public static String[] getNumInTimerStr(String mTimerStr){
        return mTimerStr.split("[^\\d]");
    }
    //设置内容的Span
    public static void setContentSpan(SpannableString mSpan, Object span, int start,
                                      int end) {
        mSpan.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

}
