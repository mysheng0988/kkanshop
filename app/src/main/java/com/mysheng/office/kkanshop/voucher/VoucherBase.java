package com.mysheng.office.kkanshop.voucher;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.util.DisplayUtils;

/**
 * Created by myaheng on 2018/11/26.
 */

public abstract class  VoucherBase extends LinearLayout{
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
    protected int contentResId;
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
    public VoucherBase(Context context) {
        this(context,null);
    }

    public VoucherBase(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public VoucherBase(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VoucherView);
        radiusColor = typedArray.getColor(R.styleable.VoucherView_radiusColor, Color.WHITE);
        gap = typedArray.getDimensionPixelSize(R.styleable.VoucherView_gap, 4);
        radius = typedArray.getDimensionPixelSize(R.styleable.VoucherView_circleRadius,5);
        gap= DisplayUtils.dpToPx(gap);
        radius=DisplayUtils.dpToPx(radius);
        typedArray.recycle();
        inflate(context,contentResId,this);
    }

    protected abstract void initView();

    protected abstract void initPaint();

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
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usable);
        }else if(status==2){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.overdue);
            colorIndex=4;
        }else if(status==3){
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.used);
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
        circleNum = (int) ((h-gap)%(2*radius+gap));
    }
}
