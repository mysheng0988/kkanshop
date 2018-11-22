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
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.util.DisplayUtils;

/**
 * 自定义的一个卡劵效果的view
 *
 */
public class CouponDisplayView extends LinearLayout {

    /**
     * 圆间距
     */
    private int gap =5;
    /**
     * 圆圈颜色
     */
    private int radiusColor=Color.WHITE;
    /**
     * 半径
     */
    private int radius = 5;
    /**
     * 圆数量
     */
    private int circleNum;
    private int topColor=0xffE89F38;
    private  int bottomColor=0xffEBAD45;
    private int bigTextSize=30;
    private int smallTextSize=16;
    private int leftWidth=20;
    private String shopName="新爱我服饰旗舰店";
    private String reduce="￥50.00";
    private String name="代金券";
    private String limit="满299减50";
    private String subhead="副券";
    private String startDate="2018-11-01";
    private String endDate="2018-11-30";


    private float remain;
    private Paint mPaint;
    private Paint dottedLine;
    private Path dottedPath;

    private Paint topPaint;
    private Path topPath;
    private Paint bottomPaint;
    private Path bottomPath;

    private Paint textPaint;
    private Path pathText;
    public CouponDisplayView(Context context) {
        this(context,null);
    }

    public CouponDisplayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CouponDisplayView);
        radiusColor = typedArray.getColor(R.styleable.CouponDisplayView_radiusColor, radiusColor);
        topColor = typedArray.getColor(R.styleable.CouponDisplayView_topColor, topColor);
        bottomColor = typedArray.getColor(R.styleable.CouponDisplayView_bottomColor, bottomColor);
        gap = typedArray.getDimensionPixelSize(R.styleable.CouponDisplayView_gap, gap);
        radius = typedArray.getDimensionPixelSize(R.styleable.CouponDisplayView_circleRadius,radius);
        bigTextSize = typedArray.getDimensionPixelSize(R.styleable.CouponDisplayView_bigTextSize,bigTextSize);
        smallTextSize = typedArray.getDimensionPixelSize(R.styleable.CouponDisplayView_smallTextSize,smallTextSize);
        gap=DisplayUtils.dpToPx(gap);
        radius=DisplayUtils.dpToPx(radius);
        bigTextSize=DisplayUtils.dpToPx(bigTextSize);
        smallTextSize=DisplayUtils.dpToPx(smallTextSize);
        leftWidth=DisplayUtils.dpToPx(leftWidth);
        typedArray.recycle();
        inflate(context,R.layout.voucher_layout,this);
        init();

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
