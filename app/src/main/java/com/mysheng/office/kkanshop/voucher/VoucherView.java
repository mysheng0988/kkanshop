package com.mysheng.office.kkanshop.voucher;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
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
public class VoucherView extends LinearLayout {

    /**
     * 圆间距
     */
    protected int gap;
    /**
     * 圆圈颜色
     */
    protected int radiusColor;
    /**
     * 半径
     */
    protected int radius;
    /**
     * 圆数量
     */
    protected int circleNum;
    /**
     * 获取内容布局
     */
    protected int colorIndex=0;
    protected int[] topColor={0xffE89F38,0xff89AA3A,0xff67ADD0,0xffC34B62,0xffc5c5c5};
    protected  int[] bottomColor={0xffEBAD45,0xff9BB752,0xff7DB9D7,0xffCB657B,0xffcdcdcd};
    protected Bitmap bitmap;


    protected float remain;
    protected Paint mPaint;
    protected Paint dottedLine;
    protected Path dottedPath;
    protected Paint mBitPaint;
    protected Rect mSrcRect, mDestRect;

    protected Paint topPaint;
    protected Path topPath;
    protected Paint bottomPaint;
    protected Path bottomPath;

    private TextView shopName;
    private TextView reduce;
    private TextView limit;
    private TextView startDate;
    private TextView endDate;
    private Context mContext;

    public VoucherView(Context context) {
        this(context,null);
    }

    public VoucherView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VoucherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VoucherView);
        radiusColor = typedArray.getColor(R.styleable.VoucherView_radiusColor, Color.WHITE);
        gap = typedArray.getDimensionPixelSize(R.styleable.VoucherView_gap, 4);
        radius = typedArray.getDimensionPixelSize(R.styleable.VoucherView_circleRadius,5);
        gap= DisplayUtils.dpToPx(gap);
        radius=DisplayUtils.dpToPx(radius);
        typedArray.recycle();
        inflate(context,R.layout.voucher_layout,this);
        initView();

    }


    protected void initView(){
        shopName=findViewById(R.id.shopName);
        reduce=findViewById(R.id.reduce);
        limit=findViewById(R.id.limit);
        startDate=findViewById(R.id.startDate);
        endDate=findViewById(R.id.endDate);
    }

    protected void initPaint() {
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

        dottedLine=new Paint();
        dottedLine.setColor(Color.WHITE);
        dottedLine.setStyle(Paint.Style.STROKE);
        dottedLine.setStrokeWidth(1);
        dottedPath =new Path();
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
    }

    public VoucherView setViewDate(VoucherModel model){
        shopName.setText(model.getShopName());
        int reducePrice=model.getReduce();
        reduce.setText("￥"+model.getReduce()+".00");
        limit.setText(model.getLimit());
       // subhead.setText(model.getSubhead());
        startDate.setText(model.getStartDate());
        endDate.setText(model.getEndDate());
        int status=model.getStatus();
        initViewParam(reducePrice,status);
        initPaint();
        return this;
    }
    /**
     * 当前优惠券的状态设置
     * @param reducePrice 优惠价格
     * @param status 状态
     */
    protected void initViewParam(int reducePrice,int status){
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
            bitmap = BitmapUtils.getBitmapFromDrawable(mContext,R.drawable.icon_usable);
        }else if(status==2){
            bitmap = BitmapUtils.getBitmapFromDrawable(mContext,R.drawable.icon_overdue);
            colorIndex=4;
        }else if(status==3){
            bitmap = BitmapUtils.getBitmapFromDrawable(mContext,R.drawable.icon_used);
            colorIndex=4;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (remain==0){
            //计算不整除的剩余部分
            remain = (int)((h-gap)%(2*radius+gap));
        }
        circleNum = (int) ((h-gap)/(2*radius+gap));
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
            float y =remain/2+gap+radius+((gap+radius*2)*i);
            canvas.drawCircle(0,y,radius,mPaint);
        }
        canvas.drawCircle(width*3/5,0,radius,mPaint);
        canvas.drawCircle(width*3/5,height,radius,mPaint);
        dottedPath.reset();
        dottedPath.moveTo(width*3/5,0);
        dottedPath.lineTo(width*3/5,height);
        dottedLine.setPathEffect(new DashPathEffect(new float[]{20f,10f}, 0));
        canvas.drawPath(dottedPath, dottedLine);
        if(bitmap!=null){
            int bw=bitmap.getWidth();
            int bh=bitmap.getHeight();
            mSrcRect = new Rect(0, 0, bw,bh);
            mDestRect = new Rect(width-bw, 0, width, height-bh);
            canvas.drawBitmap(bitmap, mSrcRect, mDestRect, mBitPaint);
        }
      }

}
