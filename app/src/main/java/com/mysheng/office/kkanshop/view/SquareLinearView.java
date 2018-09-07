package com.mysheng.office.kkanshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class SquareLinearView extends LinearLayout{
    public SquareLinearView(Context context) {
        super(context); }
    public SquareLinearView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SquareLinearView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();

        widthMeasureSpec = MeasureSpec.makeMeasureSpec( childWidthSize, MeasureSpec.EXACTLY);
        // 高度和宽度一样
        heightMeasureSpec = widthMeasureSpec;
        //设定高是宽的比例
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
