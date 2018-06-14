package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.mysheng.office.kkanshop.util.VolleyJsonInterface;
import com.mysheng.office.kkanshop.util.VolleyRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements View.OnClickListener{
    private View inflate;
    private EditText userId;
    private EditText userPwd;
    private Button  login;
    private Button  reg;
    private ImageView back;
    private ImageView setting;
    private Dialog dialog;
    private TextView choosePhoto;
    private TextView takePhoto;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        initView();
        initEvent();
    }

    private void initView() {
        userId=findViewById(R.id.user_id);
        userPwd=findViewById(R.id.user_password);
        login=findViewById(R.id.loginButton);
        reg=findViewById(R.id.regButton);
        back=findViewById(R.id.back);
        setting=findViewById(R.id.setting);

    }
    private void initEvent() {
        login.setOnClickListener(this);
        reg.setOnClickListener(this);
        back.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                signIn();
                break;
            case R.id.regButton:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.setting:
                show();
                break;
        }
    }
    public void show(){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_button_layout, null);
        //初始化控件
        choosePhoto =  inflate.findViewById(R.id.choosePhoto);
        takePhoto = inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }
    private void signIn(){
        String id=userId.getText().toString().trim();
        String pwd=userPwd.getText().toString().trim();
        if("".equals(id)&&"".equals(pwd)){
            Toast.makeText(this,"用户名和密码不能为空",Toast.LENGTH_SHORT).show();
        }else {
            //TODO
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("userId", "15701570988");
            hashMap.put("userPwd", "123456");
            JSONObject jsonParams = new JSONObject(hashMap);
            String url = "http://192.168.1.22:9090/office/goods/addGoods";
            VolleyRequest.JsonRequestPost(url,"json",jsonParams,new VolleyJsonInterface(LoginActivity.this, VolleyJsonInterface.mListener, VolleyJsonInterface.errorListener) {
                @Override
                public void onSuccess(JSONObject result) {
                    Toast.makeText(LoginActivity.this,result.toString(),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(VolleyError error) {
                    Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
