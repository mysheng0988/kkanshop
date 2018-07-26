package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mysheng.office.kkanshop.util.CodeUtils;


/**
 * Created by myaheng on 2018/6/7.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText userId;
    private EditText password;
    private Button login;
    private Button regUser;
    private Button restPwd;
    private EditText codeNum;
    private CodeUtils codeUtils;
    private ImageView codeImage;
    private ImageView comeBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();
        initEvent();
    }
    private void initView() {
        codeImage=findViewById(R.id.codeImage);
        userId=findViewById(R.id.user_id);
        codeNum=findViewById(R.id.code_num);
        codeUtils = CodeUtils.getInstance();
        codeImage.setImageBitmap(codeUtils.createBitmap());
        password=findViewById(R.id.user_password);
        login=findViewById(R.id.loginButton);
        restPwd=findViewById(R.id.restPwd);
        regUser=findViewById(R.id.regButton);
        comeBack=findViewById(R.id.comeBack);
    }
    private void initEvent() {
        login.setOnClickListener(this);
        regUser.setOnClickListener(this);
        restPwd.setOnClickListener(this);
        codeImage.setOnClickListener(this);
        comeBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                loginUser();
                break;
            case R.id.regButton:
                startRegActivity();
                break;
            case R.id.restPwd:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "3");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.codeImage:
                codeImage.setImageBitmap(codeUtils.createBitmap());
                break;
            case R.id.comeBack:
                finish();
                break;

        }
    }
    private void startRegActivity(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", "1");
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void loginUser(){
        String id=userId.getText().toString().trim();
        String pwd=password.getText().toString().trim();
        if("".equals(id)||"".equals(pwd)){
            Toast.makeText(this,"用户名和密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!codeNum.getText().toString().toUpperCase().equals(codeUtils.getCode())){
            Toast.makeText(this,"验证码不正确",Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
