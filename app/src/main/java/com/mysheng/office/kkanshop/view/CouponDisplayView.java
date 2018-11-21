package com.mysheng.office.kkanshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mysheng.office.kkanshop.R;

/**
 * 自定义的一个卡劵效果的view
 * yangqiangyu on 5/16/16 11:15
 * 博客:http://blog.csdn.net/yissan
 *
 */
public class CouponDisplayView extends LinearLayout {

    private Paint mPaint;
    /**
     * 圆间距
     */
    private int gap = 10;
    /**
     * 圆圈颜色
     */
    private int radiusColor=Color.WHITE;
    /**
     * 半径
     */
    private int radius = 10;
    /**
     * 圆数量
     */
    private int circleNum;

    private float remain;


    public CouponDisplayView(Context context) {
        super(context);
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CouponDisplayView);
        radiusColor = typedArray.getColor(R.styleable.CouponDisplayView_radiusColor, radiusColor);
        gap = typedArray.getDimensionPixelSize(R.styleable.CouponDisplayView_gap, gap);
        radius = typedArray.getDimensionPixelSize(R.styleable.CouponDisplayView_circleRadius,radius);
        typedArray.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(radiusColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain==0){
            //计算不整除的剩余部分
            remain = (int)(h-gap)%(2*radius+gap);
        }
        circleNum = (int) ((h-gap)/(2*radius+gap));
    }


    public CouponDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0;i<circleNum;i++){
            float y = gap+radius+remain/2+((gap+radius*2)*i);
            canvas.drawCircle(0,y,radius,mPaint);
        }
    }
}
