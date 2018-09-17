package com.mysheng.office.kkanshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mysheng.office.kkanshop.R;

public class Star extends View{

    private float starMark = 0.0F;
    //星星个数
    private int starNum = 5;
    //星星高度
    private int starHeight ;
    //星星宽度
    private int starWidth ;
    //星星间距
    private int starDistance ;
    //星星背景
    private Drawable starBackgroundBitmap;
    //动态星星
    private Bitmap starDrawDrawable;
    //星星变化监听
    private OnStarChangeListener changeListener;
    //是否可以点击
    private boolean isClick = false;
    //画笔
    private Paint mPaint;
    public Star(Context context) {
        this(context,null);
    }

    public Star(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.star);
        this.starNum = typedArray.getInteger(R.styleable.star_starsNum, 5);
        this.starHeight = (int) typedArray.getDimension(R.styleable.star_starHeight, 0);
        this.starWidth = (int) typedArray.getDimension(R.styleable.star_starWidth, 0);
        this.starDistance = (int) typedArray.getDimension(R.styleable.star_starDistance, 0);
        this.isClick = typedArray.getBoolean(R.styleable.star_starClickable,true);
        this.starBackgroundBitmap = typedArray.getDrawable(R.styleable.star_starBackground);
        this.starDrawDrawable = drawableToBitmap(typedArray.getDrawable(R.styleable.star_starDrawBackground));
        typedArray.recycle();
        setClickable(isClick);
        //初始化画笔
        mPaint = new Paint();
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setShader(new BitmapShader(starDrawDrawable,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP));
        Log.d("mys", "Star: "+this.starDistance);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(starWidth*starNum+starDistance*(starNum-1),starHeight);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null==starDrawDrawable||null==starBackgroundBitmap){
            return;
        }
        for (int i = 0;i<starNum;i++) {
            starBackgroundBitmap.setBounds(starDistance * i + starWidth * i, 0, starWidth * (i + 1) + starDistance * i, starHeight);
            starBackgroundBitmap.draw(canvas);
        }
        if (starMark > 1) {
            canvas.drawRect(0, 0, starWidth, starHeight, mPaint);
            if(starMark-(int)(starMark) == 0) {
                for (int i = 1; i < starMark; i++) {
                    canvas.translate(starDistance + starWidth, 0);
                    canvas.drawRect(0, 0, starWidth, starHeight, mPaint);
                }
            }else {
                for (int i = 1; i < starMark - 1; i++) {
                    canvas.translate(starDistance + starWidth, 0);
                    canvas.drawRect(0, 0, starWidth, starHeight, mPaint);
                }
                canvas.translate(starDistance + starWidth, 0);
                canvas.drawRect(0, 0, starWidth * (Math.round((starMark - (int) (starMark))*10)*1.0f/10), starHeight, mPaint);
            }
        }else {
            canvas.drawRect(0, 0, starWidth * starMark, starHeight, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isClick){
            return true;
        }
        int x = (int) event.getX();
        if (x<0)
            x=0;
        if (x>getMeasuredWidth())
            x = getMeasuredWidth();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setMark(x*1.0f / (getMeasuredWidth()*1.0f/starNum));
                break;
            case MotionEvent.ACTION_MOVE:
                setMark(x*1.0f / (getMeasuredWidth()*1.0f/starNum));
                break;
            case MotionEvent.ACTION_UP:
                setMark(x*1.0f / (getMeasuredWidth()*1.0f/starNum));
                break;
        }
        return true;
    }
    /**
     * 设置分数
     */
    public void setMark(Float mark){
        starMark = Math.round(mark * 10) * 1.0f / 10;
        if (null!=changeListener){
            changeListener.onStarChange(starMark);
        }
        invalidate();
    }


    /**
     * 设置是否可以点击
     * @param click
     */
    public void setClick(boolean click) {
        isClick = click;
    }

    /**
     * 设置监听
     */
    public void setStarChangeLister(OnStarChangeListener starChangeLister){
        changeListener = starChangeLister;
    }

    /**
     * 获取分数
     */
    public float getMark(){
        return starMark;
    }

    /**
     * 星星数量变化监听接口
     */
    public interface OnStarChangeListener{
        void onStarChange(Float mark);
    }

    /**
     * drawable转bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable)
    {
        if (drawable == null)return null;
        Bitmap bitmap = Bitmap.createBitmap(starWidth, starHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, starWidth, starHeight);
        drawable.draw(canvas);
        return bitmap;
    }
}
