package com.mysheng.office.kkanshop.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.mysheng.office.kkanshop.R;


/**
 * @author mysheng
 */
@SuppressLint("AppCompatCustomView")
public class GridItemView extends ImageView {
    private OnDelButtonClickL mDelClickL;//监听删除
    private Paint mPaint;//画笔
    private Bitmap mDelBitmap;//删除图片
    private Rect mDelBound;//删除矩形框
    private Rect rect= new Rect();//画边框
    private int mDelBoundPadding=5;//默认为5
    private boolean isShowDel=true;
    public GridItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GridItemView(Context context) {
        super(context);
        init();
    }

    private void init(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0x88000000);
        mDelBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.del_img);
        mDelBound=new Rect();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowDel){
            mDelBound.set(getWidth()-getPaddingRight()-mDelBitmap.getWidth()-mDelBoundPadding*2,getPaddingTop(),getWidth()-getPaddingRight(),getPaddingTop()+mDelBitmap.getHeight()+mDelBoundPadding*2);
            canvas.drawRect(mDelBound,mPaint);//先画一个删除框
            canvas.drawBitmap(mDelBitmap,mDelBound.left+mDelBoundPadding,mDelBound.top+mDelBoundPadding,null);//然后画一个删除按钮
        }
        mPaint.setColor(0x88cccccc);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5.0f);	//设置画笔宽度
        mPaint.setStyle(Paint.Style.STROKE);	//设置画笔为线条模式

        canvas.getClipBounds(rect);	//获取到ImageView的外轮廓矩形

        canvas.drawRect(rect, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean touchable = event.getX() > mDelBound.left
                    && event.getX() < mDelBound.right&&event.getY()>mDelBound.top&&event.getY()<mDelBound.bottom;

            if (touchable&&mDelClickL!=null) {//点击删除键
                mDelClickL.onDelClickL();
                return true;
            }
        }

        return super.onTouchEvent(event);
    }

    public void setDelBoundPadding(int boundPadding){
        this.mDelBoundPadding=boundPadding;
    }

    public void setShowDel(boolean showDel) {
        isShowDel = showDel;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }

    public void setOnDelClickL(OnDelButtonClickL l){
        this.mDelClickL=l;
    }
    public interface OnDelButtonClickL{
        void onDelClickL();
    }
}