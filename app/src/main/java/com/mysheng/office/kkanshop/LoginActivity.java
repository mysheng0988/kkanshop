package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.listenter.MIMCUpdateChatMsg;
import com.mysheng.office.kkanshop.service.MIMCService;
import com.mysheng.office.kkanshop.util.CodeUtils;
import com.mysheng.office.kkanshop.util.SharedPreferencesUtils;
import com.mysheng.office.kkanshop.util.UtilToast;
import com.mysheng.office.kkanshop.utils.FileUtils;
import com.xiaomi.mimc.MIMCTokenFetcher;
import com.xiaomi.mimc.MIMCUser;


/**
 * Created by myaheng on 2018/6/7.
 */

public class LoginActivity extends BaseActivity {
    private SharedPreferencesUtils shareData;
    private EditText userId;
    private EditText password;
    private Button login;
    private Button regUser;
    private Button restPwd;
    private EditText codeNum;
    private CodeUtils codeUtils;
    private ImageView codeImage;
    private ImageView comeBack;
    private MIMCService mimcService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        shareData=new SharedPreferencesUtils(this);
        initView();
        initEvent();
    }
    @Override
    protected void initView() {
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
    @Override
    protected void initEvent() {
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
            UtilToast.showShort(this,"用户名和密码不能为空");
            return;
        }
        if(!codeNum.getText().toString().toUpperCase().equals(codeUtils.getCode())){
            UtilToast.showShort(this,"验证码不正确");
            return;
        }
        shareData.setParam("userName","雾里看花");
        shareData.setParam("phone",id);
        finish();

    }
    /**
     * 开启MIMC聊天
     */
    private void startMIMCService() {
        Intent intent = new Intent(this, MIMCService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }
    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("mys", "onServiceConnected: "+111);
            mimcService = ((MIMCService.MIMCBinder) service).getService();
            mimcService.setUpdateChatMsg(new MIMCUpdateChatMsg() {
                @Override
                public void noticeNewMsg(ChatMsg chatMsg) {

                }
            });
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
