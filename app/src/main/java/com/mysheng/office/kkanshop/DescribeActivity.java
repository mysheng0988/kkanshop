package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import com.jph.takephoto.app.TakePhotoActivity;
import com.mysheng.office.kkanshop.view.MarqueeTextView;



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
