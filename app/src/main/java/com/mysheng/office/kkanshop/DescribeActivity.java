package com.mysheng.office.kkanshop;

import android.app.Dialog;
import android.app.Service;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.mysheng.office.kkanshop.adapter.DescribeViewAdapter;
import com.mysheng.office.kkanshop.entity.DescribeModel;
import com.mysheng.office.kkanshop.util.TakePhotoSetting;
import com.mysheng.office.kkanshop.view.CustomProgressBar;
import com.mysheng.office.kkanshop.view.MarqueeTextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by myaheng on 2018/9/6.
 */

public class DescribeActivity extends TakePhotoActivity implements View.OnClickListener{
    private ImageView comeBack;

    private MarqueeTextView marqueeTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.describe_layout);

        initView();
        initEvent();
    }



    private void initView() {
        comeBack=findViewById(R.id.comeBack);
        marqueeTextView=findViewById(R.id.marquee);
        marqueeTextView.startScroll();


    }
    private void initEvent() {
        comeBack.setOnClickListener(this);
       // addMenu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comeBack:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
      marqueeTextView.startScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        marqueeTextView.stopScroll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
