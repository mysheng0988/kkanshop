package com.mysheng.office.kkanshop.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * Created by myaheng on 2018/11/19.
 */

public class BackgroundSpan extends ImageSpan {
    private Rect mTextBound;
    private int maxHeight = 0;
    private int maxWidth = 0;
    private int mPaddingLeft = 20;
    private int mPaddingRight = 20;
    private int mPaddingTop = 20;
    private int mPaddingBottom = 20;
    private int mTextColor = Color.GREEN;
    private int mTextSize = 50;

    public BackgroundSpan(Drawable d, int verticalAlignment) {
        super(d, verticalAlignment);
        mTextBound = new Rect();
    }

    public BackgroundSpan setTimerTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        return this;
    }

    public BackgroundSpan setTimerTextSize(int textSize) {
        this.mTextSize = textSize;
        return this;
    }

    public BackgroundSpan setTimerPadding(int left, int top, int right, int bottom) {
        this.mPaddingLeft = left;
        this.mPaddingRight = right;
        this.mPaddingBottom = bottom;
        this.mPaddingTop = top;
        return this;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        //繪制文本的內容的背景
        paint.setTextSize(mTextSize);
        //測量文本的寬度和高度，通過mTextBound得到
        paint.getTextBounds(text.toString(), start, end, mTextBound);
        //設定文本背景的寬度和高度，傳入的是left,top,right,bottom四個參數
        maxWidth = maxWidth < mTextBound.width() ? mTextBound.width() : maxWidth;
        maxHeight = maxHeight < mTextBound.height() ? mTextBound.height() : maxHeight;
        //設定最大寬度和最大高度是為了防止在倒計時在數字切換的過程中會重繪，會導致倒計時邊框的寬度和高度會抖動，
        // 所以每次取得最大的高度和寬度而不是每次都去取測量的高度和寬度
        getDrawable().setBounds(0, 0, maxWidth + mPaddingLeft + mPaddingRight, mPaddingTop + mPaddingBottom + maxHeight);
        //設定文本的顏色
        paint.setColor(mTextColor);
        //設定字體的大小
        paint.setTextSize(mTextSize);
        int mGapX = (getDrawable().getBounds().width() - maxWidth) / 2;
        //繪制文本內容
        canvas.drawText(text.subSequence(start, end).toString(), x + mGapX, y , paint);
        //解决设置ImageSpan后其它文字不居中的问题
        Drawable b = getDrawable();
        canvas.save();
        int transY = 0;
        //获得将要显示的文本高度-图片高度除2等居中位置+top(换行情况)
        transY = ((bottom-top) - b.getBounds().bottom)/2+top;
        //偏移画布后开始绘制
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        if (fm != null) {
            //解决设置ImageSpan后其它文字不居中的问题
            Paint.FontMetricsInt fmPaint=paint.getFontMetricsInt();
            //获得文字、图片高度
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight=rect.bottom-rect.top;

            int top= drHeight/2 - fontHeight/4;
            int bottom=drHeight/2 + fontHeight/4;

            fm.ascent=-bottom;
            fm.top=-bottom;
            fm.bottom=top;
            fm.descent=top;
        }
        return rect.right;
    }

}
