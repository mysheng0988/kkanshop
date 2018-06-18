package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingActivity extends Activity implements View.OnClickListener{
    private ImageButton backBtn;
    private TextView  title;
    private LinearLayout message;
    private LinearLayout updatePwd;
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
        message=findViewById(R.id.message);
        updatePwd=findViewById(R.id.update_password);
        title.setText("账户设置");
    }
    private void initEvent() {
        backBtn.setOnClickListener(this);
        message.setOnClickListener(this);
        updatePwd.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.message:
                Intent intent=new Intent(this,SimpleActivity.class);
                startActivity(intent);
            break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.update_password:
                Intent intent2=new Intent(this,UploadImageActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
