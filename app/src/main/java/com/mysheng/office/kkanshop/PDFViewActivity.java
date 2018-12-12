package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.mysheng.office.kkanshop.util.UtilToast;




public class PDFViewActivity extends BaseActivity{
    private PDFView pdfView;
    private ImageView comeback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf_view_layout);
        initView();
        initEvent();
    }

    @Override
    protected void initView() {
        pdfView=findViewById(R.id.pdfView);
        comeback=findViewById(R.id.comeBack);
        pdfView.fromAsset("app.pdf").onPageChange(new OnPageChangeListener() {
            @Override
            public void onPageChanged(int page, int pageCount) {
                UtilToast.showShort(PDFViewActivity.this,page+"/"+pageCount);
            }
        }).load();
    }

    @Override
    protected void initEvent() {
        comeback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comeBack:
                finish();
                break;
        }
    }

}
