package com.mysheng.office.kkanshop.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class BorderTextView extends TextView{
    private int borderWidth = 8;
    private  int normalColor=0xFFFFFFFF;
    private Paint mPaint;
    public BorderTextView(Context context, int color) {
        this(context,null,color);
        setTextSize(12);
    }

    public BorderTextView(Context context, @Nullable AttributeSet attrs,int color) {
        super(context, attrs);
        this.normalColor=color;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFakeBoldText(true);
        mPaint.setStrokeWidth(borderWidth);
        mPaint.setColor(normalColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //  画TextView的4个边
//        canvas.drawLine(0, 0, this.getWidth() - borderWidth, 0, mPaint);
//        canvas.drawLine(0, 0, 0, this.getHeight() - borderWidth, mPaint);
        canvas.drawLine(this.getWidth() , 20, this.getWidth() , this.getHeight() - borderWidth-20, mPaint);
       // canvas.drawLine(0, this.getHeight() - borderWidth-20, this.getWidth() - borderWidth, this.getHeight() - borderWidth-20, mPaint);
        super.onDraw(canvas);
    }


}
