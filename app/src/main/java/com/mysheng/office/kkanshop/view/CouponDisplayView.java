package com.mysheng.office.kkanshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mysheng.office.kkanshop.R;

/**
 * 自定义的一个卡劵效果的view
 *
 */
public class CouponDisplayView extends LinearLayout {

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
    private Paint mPaint;
    private Paint dottedLine;
    private Path dottedPath;

    private Paint topPaint;
    private Path topPath;
    private Paint bottomPaint;
    private Path bottomPath;
    public CouponDisplayView(Context context) {
        this(context,null);
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CouponDisplayView);
        radiusColor = typedArray.getColor(R.styleable.CouponDisplayView_radiusColor, radiusColor);
        gap = typedArray.getDimensionPixelSize(R.styleable.CouponDisplayView_gap, gap);
        radius = typedArray.getDimensionPixelSize(R.styleable.CouponDisplayView_circleRadius,radius);
        typedArray.recycle();
        init();

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int childWidthSize = getMeasuredWidth();
//
////        widthMeasureSpec = MeasureSpec.makeMeasureSpec( childWidthSize, MeasureSpec.EXACTLY);
////        // 高度和宽度一样
////        heightMeasureSpec = widthMeasureSpec/3;
//        super.onMeasure(widthMeasureSpec, childWidthSize/3);
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain==0){
            //计算不整除的剩余部分
            remain = (int)(h-gap)%(2*radius+gap);
        }
        circleNum = (int) ((h-gap)/(2*radius+gap));
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

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(radiusColor);
        mPaint.setStyle(Paint.Style.FILL);

        dottedLine=new Paint();
        dottedLine.setColor(Color.WHITE);
        dottedLine.setStyle(Paint.Style.STROKE);
        dottedLine.setStrokeWidth(1);
        dottedPath =new Path();



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
            float y = gap+radius+remain/2+((gap+radius*2)*i);
            canvas.drawCircle(0,y,radius,mPaint);
        }
        canvas.drawCircle(width*3/5,0,radius,mPaint);
        canvas.drawCircle(width*3/5,height,radius,mPaint);
        dottedPath.reset();
        dottedPath.moveTo(width*3/5,0);
        dottedPath.lineTo(width*3/5,height);
        dottedLine.setPathEffect(new DashPathEffect(new float[]{20f,10f}, 0));
        canvas.drawPath(dottedPath, dottedLine);

    }
}
