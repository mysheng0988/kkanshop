package com.mysheng.office.kkanshop.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by myaheng on 2018/9/12.
 */

/**
 * 颜色渐变的TextView
 */
@SuppressLint("AppCompatCustomView")
public class ChangeTextView extends TextView{
    private LinearGradient mLinearGradient;
    private int[] color=  new int[] {  0xFFFFCC00, 0xFFFF9900 ,0xFFFF6600,0xFFFF3300,0xFFFF6600, 0xFFFF9900,0xFFFFCC00};
    private Paint mPaint;
    private int mViewWidth = 0;
    private Rect mTextBound = new Rect();
    public ChangeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"font/running.ttf");
        setTypeface(typeface);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, color, null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 +   mTextBound.height()/2, mPaint);
    }

    public void setColor(int[] color) {
        this.color = color;
        invalidate();
    }
}
