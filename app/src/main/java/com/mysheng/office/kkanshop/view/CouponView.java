package com.mysheng.office.kkanshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.VoucherModel;
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
    private int colorIndex;
    private int[] topColor={0xffE89F38,0xff89AA3A,0xff67ADD0,0xffC34B62,0xffc5c5c5};
    private  int[] bottomColor={0xffEBAD45,0xff9BB752,0xff7DB9D7,0xffCB657B,0xffcdcdcd};
    private int leftWidth=20;

    private TextView shopName;
    private TextView reduce;
    private TextView limit;
    private TextView endDate;
    private int status=0;
    private Bitmap bitmap;


    private float remain;
    private Paint mPaint;
    private Paint dottedLine;
    private Path dottedPath;
    private Paint mBitPaint;
    private Rect mSrcRect, mDestRect;

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
        gap = typedArray.getDimensionPixelSize(R.styleable.VoucherView_gap, gap);
        radius = typedArray.getDimensionPixelSize(R.styleable.VoucherView_circleRadius,radius);
        gap=DisplayUtils.dpToPx(gap);
        radius=DisplayUtils.dpToPx(radius);
        leftWidth=DisplayUtils.dpToPx(leftWidth);
        typedArray.recycle();
        inflate(context,R.layout.coupon_layout,this);
        initView();

    }
    private void initView(){
        shopName=findViewById(R.id.shopName);
        reduce=findViewById(R.id.reduce);
        limit=findViewById(R.id.limit);
        endDate=findViewById(R.id.endDate);
    }
    public CouponView setViewDate(VoucherModel model){
        shopName.setText(model.getShopName());
        int reducePrice=model.getReduce();
        reduce.setText(String.format("￥%d.00",reducePrice));
        limit.setText(model.getLimit());
        status=model.getStatus();

        if(reducePrice<50){
            colorIndex=0;
        }else if(reducePrice>=50&&reducePrice<100){
            colorIndex=1;
        }else if(reducePrice>=100&&reducePrice<150){
            colorIndex=2;
        }else {
            colorIndex = 3;
        }
        if(status==1){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usable);
        }else if(status==2){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.overdue);
            colorIndex=4;
        }else if(status==3){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.used);
            colorIndex=4;
        }
        initPaint();
        return this;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain==0){
            //计算不整除的剩余部分
            remain = (int)((h-gap)%(2*radius+gap));
        }
        circleNum = (int) ((h-gap)%(2*radius+gap));
    }
    private void initPaint(){

        topPaint=new Paint();
        topPaint.setColor(topColor[colorIndex]);
        topPaint.setStyle(Paint.Style.FILL);
        topPath=new Path();

        bottomPaint=new Paint();
        bottomPaint.setColor(bottomColor[colorIndex]);
        bottomPaint.setStyle(Paint.Style.FILL);
        bottomPath=new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(radiusColor);
        mPaint.setStyle(Paint.Style.FILL);

        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);

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
            canvas.drawCircle(0,y,radius,mPaint);
        }
         if(bitmap!=null){
            int bw=bitmap.getWidth();
            int bh=bitmap.getHeight();
            mSrcRect = new Rect(0, 0, bw,bh);
            mDestRect = new Rect(width-bw/2, 0, width, height-bh/2);
            canvas.drawBitmap(bitmap, mSrcRect, mDestRect, mBitPaint);
        }

      }

}
