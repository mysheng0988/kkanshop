package com.mysheng.office.kkanshop.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;

import java.util.Random;

/**
 * Created by myaheng on 2018/11/16.
 */

@SuppressLint("AppCompatCustomView")
public class ChangeableTextView extends TextView{
    private LinearGradient mLinearGradient;
    private String fontPath[]={"font/formal.ttf","font/ana.ttf", "font/bubbling.ttf",
            "font/annabelle.ttf","font/brush.ttf",
            "font/cartoon.ttf","font/chinese.ttf",
            "font/fzlxt.ttf","font/fzpyt.ttf",
            "font/hk_snt.ttf","font/ldy.ttf",
            "font/normal.ttf","font/qlin.ttf",
            "font/run.ttf","font/running.ttf","font/Spray.ttf", "font/wzybxs.ttf","font/led.ttf"
    };
  //  private int[] color=  new int[] {  0xFFFFCC00, 0xFFFF9900 ,0xFFFF6600,0xFFFF3300,0xFFFF6600, 0xFFFF9900,0xFFFFCC00};
    private Paint mPaint;
    private int mViewWidth = 0;
    private Rect mTextBound = new Rect();
    private int textStyle=0;
    public ChangeableTextView(Context context) {
        this(context,null);
    }

    public ChangeableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray= context.obtainStyledAttributes(attrs, R.styleable.ChangeableTextView);
        textStyle=typedArray.getInt(R.styleable.ChangeableTextView_style_mode,0);

        Random random=new Random();
        Typeface typeface;
        String strPath;
        if(textStyle==-1){
             strPath=fontPath[random.nextInt(18)];
            Log.e("mys", "ChangeableTextView: "+ strPath);
            typeface=Typeface.createFromAsset(context.getAssets(),strPath);
        }else{
            strPath=fontPath[textStyle];
            Log.e("mys", "ChangeableTextView: "+ strPath);
            typeface=Typeface.createFromAsset(context.getAssets(),strPath);
        }
        setTypeface(typeface);
        typedArray.recycle();
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        mViewWidth = getMeasuredWidth();
//        mPaint = getPaint();
//        String mTipText = getText().toString();
//        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
//        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, color, null, Shader.TileMode.REPEAT);
//        mPaint.setShader(mLinearGradient);
//        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 +   mTextBound.height()/2, mPaint);
//    }
//
//    public void setColor(int[] color) {
//        this.color = color;
//        invalidate();
//    }
}
