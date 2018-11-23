package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import com.jph.takephoto.app.TakePhotoActivity;
import com.mysheng.office.kkanshop.entity.VoucherModel;
import com.mysheng.office.kkanshop.view.MarqueeTextView;
import com.mysheng.office.kkanshop.view.VoucherView;


/**
 * Created by myaheng on 2018/9/6.
 */

public class DescribeActivity extends TakePhotoActivity implements View.OnClickListener{
    private ImageView comeBack;
    private VoucherView voucherView;

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
        voucherView=findViewById(R.id.voucherView);
        VoucherModel model=new VoucherModel();
        model.setShopName("新月神电动车");
        model.setLimit("满3000减50");
        model.setStartDate("2018-12-01");
        model.setEndDate("2018-12-30");
        model.setReduce("￥50.00");
        voucherView.setViewDate(model);
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
