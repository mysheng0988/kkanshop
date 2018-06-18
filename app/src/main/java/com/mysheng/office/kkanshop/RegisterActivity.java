package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements View.OnClickListener{
    private EditText userName;
    private EditText userPassword;
    private EditText againPassword;
    private EditText code;
    private Button btnCode;
    private Button regBtn;
    private ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        initView();
        initEvent();
    }

    private void initView() {
        userName=findViewById(R.id.user_name);
        userPassword=findViewById(R.id.user_password);
        againPassword=findViewById(R.id.user_pwd);
        code=findViewById(R.id.id_code);
        btnCode=findViewById(R.id.id_code_btn);
        regBtn=findViewById(R.id.reg_Button);
        back=findViewById(R.id.back);
    }
    private void initEvent() {
        btnCode.setOnClickListener(this);
        regBtn.setOnClickListener(this);
        btnCode.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_code_btn:
                getPhoneCode();
                break;
            case R.id.reg_Button:
                userRegAccount();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
    private void userRegAccount(){
        String name=userName.getText().toString().trim();
        String password=userPassword.getText().toString().trim();
        String confirmPwd=againPassword.getText().toString().trim();
        String codeValue=code.getText().toString().trim();
        if ("".equals(name)){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
        }else if("".equals(password)){
            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
        }else if(password.equals(confirmPwd)){
            Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
        }else if("".equals(codeValue)){
            Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
        }
        //todo 注册用户
    }
    private void getPhoneCode(){
        //todo 获取手机验证码的方法
    }
}
