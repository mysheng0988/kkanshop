package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingActivity extends Activity implements View.OnClickListener{
    private ImageButton backBtn;
    private TextView  title;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_layout);
        initView();
        initEvent();
    }
    private void initView() {
        backBtn=findViewById(R.id.btn_back);
        title=findViewById(R.id.common_title);
        title.setText("账户设置");
    }
    private void initEvent() {
        backBtn.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
        }
    }
}
