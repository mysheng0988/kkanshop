package com.mysheng.office.kkanshop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by myaheng on 2018/9/26.
 */

public class CircleIndicator extends View {
    //默认圆半径
    private int radius=15;
    //当前指示圆半径
    private float selectedRadius;
    private int width;
    private int count;
    private Paint paint;
    private Paint selectedPaint;
    private int height;
    private float current;
    public CircleIndicator(Context context) {
        this(context,null);
    }
    public CircleIndicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public CircleIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //默认圆画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        //当前指示圆画笔
        selectedPaint = new Paint();
        selectedPaint.setAntiAlias(true);
        selectedPaint.setColor(Color.RED);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = getMeasuredHeight();
        width = getMeasuredWidth();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //这个距离如下图所示
        int distance=width/(count+1);
        //灰色圆
        for(int i=0;i<count;i++){
            canvas.drawCircle(distance*(i+1),height/2,radius,paint);
        }
        //红色圆
        canvas.drawCircle(current,height/2,selectedRadius,selectedPaint);
    }
    //和viewpager联动
    public void setUpWithViewPager(ViewPager viewPager){
        if(viewPager==null){
            return;
        }
        //确定要画几个圆
        count=viewPager.getAdapter().getCount();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
              @Override
              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                  setCircleSize(position,positionOffset,positionOffsetPixels);
              }
              @Override
              public void onPageSelected(int position) {}
              @Override
              public void onPageScrollStateChanged(int state) {

              }
        });
    }
    //设置红色圆移动的距离
    private void setCircleSize(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("xxxxx",position+"/"+positionOffset+"/"+positionOffsetPixels);
        int distance=width/(count+1);
        current = (position+1)*distance+distance*positionOffset;
        //设置红色圆的大小，
        selectedRadius=radius+3*(1+0);
        invalidate();
    }

}
