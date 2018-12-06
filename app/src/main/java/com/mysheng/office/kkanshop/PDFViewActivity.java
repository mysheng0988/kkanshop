package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.joanzapata.pdfview.PDFView;


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
        pdfView.fromAsset("app.pdf").load();
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
