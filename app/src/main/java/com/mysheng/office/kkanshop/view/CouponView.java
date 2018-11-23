package com.mysheng.office.kkanshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.util.DisplayUtils;

/**
 * 自定义的一个卡劵效果的view
 *
 */
public class CouponView extends LinearLayout {

    /**
     * 圆间距
     */
    private int gap =3;
    /**
     * 圆圈颜色
     */
    private int radiusColor=Color.WHITE;
    /**
     * 半径
     */
    private int radius = 3;
    /**
     * 圆数量
     */
    private int circleNum;
    private int topColor=0xffE89F38;
    private  int bottomColor=0xffEBAD45;
    private int bigTextSize=30;
    private int smallTextSize=16;
    private int leftWidth=20;
    private float remain;
    private Paint mPaint;
    private Paint dottedLine;
    private Path dottedPath;

    private Paint topPaint;
    private Path topPath;
    private Paint bottomPaint;
    private Path bottomPath;
    public CouponView(Context context) {
        this(context,null);
    }

    public CouponView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VoucherView);
        radiusColor = typedArray.getColor(R.styleable.VoucherView_radiusColor, radiusColor);
        topColor = typedArray.getColor(R.styleable.VoucherView_topColor, topColor);
        bottomColor = typedArray.getColor(R.styleable.VoucherView_bottomColor, bottomColor);
        gap = typedArray.getDimensionPixelSize(R.styleable.VoucherView_gap, gap);
        radius = typedArray.getDimensionPixelSize(R.styleable.VoucherView_circleRadius,radius);
        bigTextSize = typedArray.getDimensionPixelSize(R.styleable.VoucherView_bigTextSize,bigTextSize);
        smallTextSize = typedArray.getDimensionPixelSize(R.styleable.VoucherView_smallTextSize,smallTextSize);
        gap=DisplayUtils.dpToPx(gap);
        radius=DisplayUtils.dpToPx(radius);
        bigTextSize=DisplayUtils.dpToPx(bigTextSize);
        smallTextSize=DisplayUtils.dpToPx(smallTextSize);
        leftWidth=DisplayUtils.dpToPx(leftWidth);
        typedArray.recycle();
        inflate(context,R.layout.coupon_layout,this);
        init();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain==0){
            //计算不整除的剩余部分
            remain = (int)((h-gap)%(2*radius+gap));
        }
        circleNum = (int) ((h-gap)%(2*radius+gap));
        Log.e("CouponView", "onSizeChanged: "+ circleNum);
        Log.e("CouponView", "onSizeChanged: "+h );
        Log.e("CouponView", "onSizeChanged: "+remain );
    }
    private void init(){

        topPaint=new Paint();
        topPaint.setColor(topColor);
        topPaint.setStyle(Paint.Style.FILL);
        topPath=new Path();

        bottomPaint=new Paint();
        bottomPaint.setColor(bottomColor);
        bottomPaint.setStyle(Paint.Style.FILL);
        bottomPath=new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(radiusColor);
        mPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //draw a translucent in top
        topPath.reset();
        topPath.moveTo(0,0);
        topPath.lineTo(width*4/5,0);
        topPath.lineTo(width/5,height);
        topPath.lineTo(0,height);
        topPath.close();
        canvas.drawPath(topPath,topPaint);
        bottomPath.reset();
        bottomPath.moveTo(width*4/5,0);
        bottomPath.lineTo(width,0);
        bottomPath.lineTo(width,height);
        bottomPath.lineTo(width/5,height);
        bottomPath.close();
        canvas.drawPath(bottomPath,bottomPaint);
        for (int i=0;i<circleNum;i++){
            float y =remain/2+gap/2+radius+((gap+radius*2)*i);
            Log.e("CouponView", "onDraw: "+y);
            canvas.drawCircle(0,y,radius,mPaint);
            canvas.drawCircle(width,y,radius,mPaint);

        }
      }

}
