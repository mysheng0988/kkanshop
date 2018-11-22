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
    private Paint topPaint;
    private Path topPath;
    private Paint bottomPaint;
    private Path bottomPath;
    public FuckView(Context context) {
        super(context);
        init();
    }

    public FuckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FuckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    private void init(){
        topPaint=new Paint();
        topPaint.setColor(Color.parseColor("#E89F38"));
        topPaint.setStyle(Paint.Style.FILL);
        topPath=new Path();

        bottomPaint=new Paint();
        bottomPaint.setColor(Color.parseColor("#EBAD45"));
        bottomPaint.setStyle(Paint.Style.FILL);
        bottomPath=new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //draw a translucent in top
        topPath.reset();
        topPath.moveTo(0,0);
        topPath.lineTo(width-150,0);
        topPath.lineTo(150,height);
        topPath.lineTo(0,height);
        topPath.close();
        canvas.drawPath(topPath,topPaint);
        bottomPath.reset();
        bottomPath.moveTo(width-150,0);
        bottomPath.lineTo(width,0);
        bottomPath.lineTo(width,height);
        bottomPath.lineTo(150,height);
        bottomPath.close();
        canvas.drawPath(bottomPath,bottomPaint);
        //canvas.drawRect(0f, height / 2f, width, height , timPaint2);
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
