package com.mysheng.office.kkanshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.util.SharedPreferencesUtils;
import com.mysheng.office.kkanshop.util.UtilToast;
import com.mysheng.office.kkanshop.util.Utils;

public class SettingActivity extends BaseActivity {
    private ImageButton backBtn;
    private TextView  title;
    private LinearLayout message;
    private LinearLayout updatePwd;
    private LinearLayout loginExit;
    private SharedPreferencesUtils shareData;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_layout);
        shareData=new SharedPreferencesUtils(this);
        initView();
        initEvent();
    }
    @Override
    protected void initView() {
        backBtn=findViewById(R.id.btn_back);
        title=findViewById(R.id.common_title);
        message=findViewById(R.id.message);
        updatePwd=findViewById(R.id.update_password);
        loginExit=findViewById(R.id.login_exit);
        title.setText("账户设置");
    }
    @Override
    protected void initEvent() {
        backBtn.setOnClickListener(this);
        message.setOnClickListener(this);
        updatePwd.setOnClickListener(this);
        loginExit.setOnClickListener(this);
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
            case R.id.login_exit:
                shareData.clear();
                UtilToast.showShort(this,"已退出登录");
                finish();
                break;
        }
    }
}
