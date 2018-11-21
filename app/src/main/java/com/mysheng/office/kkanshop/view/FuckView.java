package com.mysheng.office.kkanshop.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class FuckView extends TextView{
    private int defaultRotate = 315;
    private String labelText = "default";
    private int labelColor = Color.WHITE;
    private int textColor = Color.BLACK;

    public FuckView(Context context) {
        super(context);
    }

    public FuckView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FuckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //draw a translucent in top
        Paint timPaint = new Paint();
        timPaint.setColor(Color.parseColor("#E89F38"));
        timPaint.setStyle(Paint.Style.FILL);
        timPaint.setStrokeWidth(5);
        Path path=new Path();
        path.moveTo(0,0);
        path.moveTo(width-100,0);
        path.moveTo(100,height);
        path.moveTo(0,height);
        path.close();
        //canvas.drawPath(path,timPaint);
        canvas.drawRect(0f, 0f, width, height / 2f, timPaint);
        Paint timPaint2 = new Paint();
        timPaint2.setColor(Color.parseColor("#EBAD45"));
        timPaint2.setStyle(Paint.Style.FILL);
        Path path2=new Path();
        path2.moveTo(width-100,0);
        path2.moveTo(width,0);
        path2.moveTo(width,height);
        path2.moveTo(100,height);
        path2.close();

        //canvas.drawPath(path2,timPaint2);
        canvas.drawRect(0f, height / 2f, width, height , timPaint2);

//        //draw a left mark
//        Paint discountPaint = new Paint();
//        discountPaint.setColor(labelColor);
//        discountPaint.setStyle(Paint.Style.FILL);
//        canvas.rotate(defaultRotate, 0, height / 2 + height / 8);
//        canvas.translate(0, height / 8);
//        Rect rect = new Rect(0, height / 4, width, height / 2);
//        canvas.drawRect(rect, discountPaint);
//
//        //draw the left mark text
//        Paint textPaint = new Paint();
//        textPaint.setColor(textColor);
//        textPaint.setTextAlign(Paint.Align.LEFT);
//        textPaint.setTextSize(rect.height() / 2);
//        canvas.drawText(labelText, rect.left + height / 7, height / 2 - rect.bottom / 7, textPaint);

    }

    public FuckView retateView(int rotate) {
        this.defaultRotate = rotate;
        return this;
    }

    public FuckView setLabelText(String str) {
        this.labelText = str;
        return this;
    }
    public FuckView setLabelColor(int labelColor) {
        this.labelColor = labelColor;
        return this;
    }
    public FuckView setLabelTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

}
