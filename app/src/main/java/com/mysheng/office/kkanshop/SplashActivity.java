package com.mysheng.office.kkanshop;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;



/**
 * Created by 拉丁吴 on 2017/2/12.
 */

public class SplashActivity extends Activity {

    private ImageView img;
    private ImageView ImgMark;
    private CountDownTimer countDownTimer;
    private TextView num;


    ViewPropertyAnimation.Animator animator=new ViewPropertyAnimation.Animator(){

        @Override
        public void animate(View view) {
            view.setAlpha(0f);
            ObjectAnimator objAnimator=ObjectAnimator.ofFloat(view,"alpha",0f,1f);
            objAnimator.setDuration(3000);
            objAnimator.start();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        initStatus();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);
        img= (ImageView) findViewById(R.id.img_id);
        ImgMark=  findViewById(R.id.icon_mark);
        num=  findViewById(R.id.num);
        ImgMark.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(SplashActivity.this).load(R.drawable.benbenla)
                        .animate(animator).into(img);
                startAnimat();
            }
        });
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    private void initStatus(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decoderView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decoderView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            //or ?
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    private void startAnimat(){

        int imgHeight=ImgMark.getHeight()/5;
        int height=getWindowManager().getDefaultDisplay().getHeight();
        int curY=height/2 + imgHeight/2;
        int dy=(height-imgHeight)/2;
        AnimatorSet set=new AnimatorSet();
        ObjectAnimator animatorTranslate=ObjectAnimator.ofFloat(ImgMark,"translationY",0,dy);
        ObjectAnimator animatorScaleX=ObjectAnimator.ofFloat(ImgMark,"ScaleX",1f,0.2f);
        ObjectAnimator animatorScaleY=ObjectAnimator.ofFloat(ImgMark,"ScaleY",1f,0.2f);
        ObjectAnimator animatorAlpha=ObjectAnimator.ofFloat(ImgMark,"alpha",1f,0.5f);
        set.play(animatorTranslate)
                .with(animatorScaleX).with(animatorScaleY).with(animatorAlpha);
        set.setDuration(1200);
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                countDownTimer=new CountDownTimer(3*1000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        num.setText((millisUntilFinished/1000)+"秒跳过");
                    }

                    @Override
                    public void onFinish() {
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        overridePendingTransition(R.anim.splash_fade_out, R.anim.splash_fade_in);// 淡出淡入动画效果
                        SplashActivity.this.finish();
                    }
                };
                countDownTimer.start();
//                ImgMark.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
//                        SplashActivity.this.finish();
//                    }
//                },3000);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }
}
