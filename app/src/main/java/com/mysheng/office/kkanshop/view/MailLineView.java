package com.mysheng.office.kkanshop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by myaheng on 2018/10/9.
 */

public class MailLineView extends View {
    //颜色块的宽度，可定义成XML属性，颜色值也可以定义为XML属性，请自行处理
    private int colorWidth = 4;
    //空白块的宽度
    private int emptyWidth = 1;

    public MailLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MailLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MailLineView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取View宽度
        int viewHeight = getHeight();
        //绘制完成的长度
        int drawLength = 0;

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        int count = 0;

        //使用canvas循环绘制颜色块
        while (drawLength < getWidth()) {
            count++;
            //切换颜色
            if (count % 2 == 1) {
                paint.setColor(Color.rgb(255, 134, 134));
            } else {
                paint.setColor(Color.rgb(134, 194, 255));
            }
            //使用路径绘制一个菱形
            Path path = new Path();
            path.moveTo(drawLength, viewHeight);// 此点为多边形的起点
            path.lineTo(drawLength + colorWidth * viewHeight - viewHeight, viewHeight);
            path.lineTo(drawLength + colorWidth * viewHeight, 0);
            path.lineTo(drawLength + viewHeight, 0);
            path.close(); // 使这些点构成封闭的多边形
            canvas.drawPath(path, paint);
            drawLength += emptyWidth * viewHeight;
            drawLength += colorWidth * viewHeight;
        }
    }


}
