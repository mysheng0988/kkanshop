package com.mysheng.office.kkanshop.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mysheng.office.kkanshop.R;
import java.util.List;
public class ViewPagerIndicator extends LinearLayout{
    private Paint mPaint;
    private Path mPath;
    private int triangleWidth;
    private int triangleHeight;
    private int tabVisibleCount;//可见tab得数量
    public  PageOnChangeListener mListener;
    private static final int DEFAULT_TAB_COUNT=4;
    private static final float RADIO_TRIANGLE_WIDTH=1/5F;
    private  int COLOR_TEXT_NORMAL=0x77FFFFFF;
    private  int COLOR_TEXT_HIGHLIGHT=0xFFFFFFFF;
    private  final int TRIANGLE_MAX_WIDTH= (int) (getScreenWidth()/3*RADIO_TRIANGLE_WIDTH);
    private  int mInitTranslationX;
    private  int mMoveTranslationX;
    private int textSize;
    private ViewPager mViewPager;
    private List<String> mTitle;
    private boolean lineStyle=false;
    private int indicatorColor=Color.WHITE;
    private int dividerColor=Color.WHITE;
    public ViewPagerIndicator(Context context) {
        this(context ,null);
    }
    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取可见tab的数量
       TypedArray ta= context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        tabVisibleCount=ta.getInt(R.styleable.ViewPagerIndicator_visible_tab_count,DEFAULT_TAB_COUNT);
        lineStyle=ta.getBoolean(R.styleable.ViewPagerIndicator_line_style,lineStyle);
        indicatorColor=ta.getInt(R.styleable.ViewPagerIndicator_color,indicatorColor);
        COLOR_TEXT_NORMAL=ta.getInt(R.styleable.ViewPagerIndicator_textColor,COLOR_TEXT_NORMAL);
        COLOR_TEXT_HIGHLIGHT=ta.getInt(R.styleable.ViewPagerIndicator_textCheckedColor,COLOR_TEXT_HIGHLIGHT);
        dividerColor=ta.getInt(R.styleable.ViewPagerIndicator_dividerColor,dividerColor);
        textSize=ta.getDimensionPixelSize(R.styleable.ViewPagerIndicator_textSize,-1);
        if(tabVisibleCount<0){
            tabVisibleCount=DEFAULT_TAB_COUNT;
        }
       ta.recycle();
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(indicatorColor);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    @Override
    protected void onFinishInflate() {
        int mCount=getChildCount();
        if(mCount==0)return;
        for(int i=0;i<mCount;i++){
            View view=getChildAt(i);
            LinearLayout.LayoutParams lp= (LayoutParams) view.getLayoutParams();
            lp.weight=0;
            lp.width=getScreenWidth()/tabVisibleCount;
        }
        setTabItemOnclickEvent();
        super.onFinishInflate();
    }
    //获取屏幕的宽度
    private int getScreenWidth() {
        WindowManager windowManager= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        return metrics.widthPixels;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX+mMoveTranslationX,getHeight());
        canvas.drawPath(mPath,mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        triangleWidth= (int) (w/tabVisibleCount*RADIO_TRIANGLE_WIDTH);
        triangleWidth=Math.min(TRIANGLE_MAX_WIDTH,triangleWidth);
        if(lineStyle){
            mInitTranslationX=0;
            initLine();
        }else{
            initTriangle();
            mInitTranslationX=w/tabVisibleCount/2-triangleWidth/2;
        }


    }

    private void initTriangle() {
        triangleHeight=triangleWidth/3;
        mPath=new Path();
        mPath.moveTo(0,0);
        mPath.lineTo(triangleWidth,0);
        mPath.lineTo(triangleWidth/2,-triangleHeight);
        mPath.close();

    }
    private void initLine() {
        triangleHeight=triangleWidth/3;
        mPath=new Path();
        mPath.moveTo(0,0);
        mPath.lineTo(getScreenWidth()/tabVisibleCount,0);
        mPath.lineTo(getScreenWidth()/tabVisibleCount,-6);
        mPath.lineTo(0,-5);
        mPath.close();

    }

    /**
     * 指示器跟随手指滚动
     * @param position
     * @param offset
     */
    public void scroll(int position,float offset){
        int tabWidth=getWidth()/tabVisibleCount;
        mMoveTranslationX= (int) (tabWidth*(offset+position));
        if(position>=(tabVisibleCount-1)&&offset>0&&getChildCount()>tabVisibleCount){
            this.scrollTo((position-(tabVisibleCount-1))*tabWidth+(int)(tabWidth*offset),0);
        }
        invalidate();
    }
    public void setTabItemTitle(List<String> titles){
        if(titles!=null&&titles.size()>0){
            this.removeAllViews();
            mTitle=titles;
//            for(String title:mTitle){
//                addView(getTabItemTitle(title));
//            }
            for (int i=0;i<mTitle.size()-1;i++){
                addView(getTabItemTitle(mTitle.get(i)));
            }
            addView(getTabItemTextView(mTitle.get(mTitle.size()-1)));
            setTabItemOnclickEvent();
        }

    }

    /**
     * 设置tab显示的个数
     * @param count
     */
    public void setTabVisibleCount(int count){
        tabVisibleCount=count;
    }
    /**
     * 根据title创建tab
     * @param title
     * @return
     */
    private View getTabItemTitle(String title) {
        BorderTextView tv=new BorderTextView(getContext(),dividerColor);
        LinearLayout.LayoutParams lp=new  LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
       lp.width=getScreenWidth()/tabVisibleCount;
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setText(title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(lp);
        return tv;
    }
    private View getTabItemTextView(String title) {
        TextView tv=new TextView(getContext());
        LinearLayout.LayoutParams lp=new  LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        lp.width=getScreenWidth()/tabVisibleCount;
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setText(title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(lp);
        return tv;
    }

    /**
     * 自定义view实用了滚动监听，自己创建滚动监听供调用者使用
     */
    public interface PageOnChangeListener{
       public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
       public void onPageSelected(int position);
       public void onPageScrollStateChanged(int state);
    }
    public void setPageOnChangeListener(PageOnChangeListener listener){
        this.mListener=listener;
    }
    /**
     * 设置关联viewPager
     * @param viewPager
     * @param position
     */
    public void setViewPager(ViewPager viewPager,int position){
        mViewPager=viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position,positionOffset);
                if(mListener!=null){
                    mListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if(mListener!=null){
                    mListener.onPageSelected(position);
                }
                setColorTextHighlight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(mListener!=null){
                    mListener.onPageScrollStateChanged(state);
                }
            }
        });
        mViewPager.setCurrentItem(position);
        setColorTextHighlight(position);
    }
    private void resetTextViewColor(){
        for(int i=0;i<getChildCount();i++){
            View view=getChildAt(i);
            if(view instanceof TextView){
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }
    /**
     * 设置某个tab的颜色为高亮显示
     * @param position
     */
    private void setColorTextHighlight(int position){
            resetTextViewColor();
            View view=getChildAt(position);
            if(view instanceof TextView){
                ((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHT);
            }
    }
    private void setTabItemOnclickEvent(){
        int count=getChildCount();
        for(int i=0;i<count;i++){
            View view=getChildAt(i);
            final int pos=i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(pos);
                }
            });
        }
    }
}
