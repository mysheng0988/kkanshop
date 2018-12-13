package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mysheng.office.kkanshop.adapter.UserAdapter;
import com.mysheng.office.kkanshop.adapter.ViewLineDivider;
import com.mysheng.office.kkanshop.entity.UserModel;
import com.mysheng.office.kkanshop.util.UtilToast;
import com.mysheng.office.kkanshop.utils.PhoneUtils;
import com.mysheng.office.kkanshop.utils.Utils;
import com.mysheng.office.kkanshop.view.MarqueeTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * Created by myaheng on 2018/9/6.
 */

public class DescribeActivity extends BaseActivity{
    private ImageView comeBack;

    private MarqueeTextView marqueeTextView;
     private RecyclerView mRecyclerView;

    private List<UserModel> users=new ArrayList<>();

    private UserAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.describe_layout);
        Utils.init(DescribeActivity.this);
        initView();
        initEvent();


    }


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==100){
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DescribeActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.addItemDecoration(new ViewLineDivider(LinearLayoutManager.VERTICAL, 2, 0xFFCCCCCC));
                adapter=new UserAdapter(DescribeActivity.this,users);
                mRecyclerView.setAdapter(adapter);
            }
        }
    };

    protected void initView() {
        comeBack=findViewById(R.id.comeBack);
        marqueeTextView=findViewById(R.id.marquee);
        marqueeTextView.startScroll();
        marqueeTextView.startScroll();
        mRecyclerView=findViewById(R.id.commonRecycler);
        rxPermissions.request(android.Manifest.permission.READ_CONTACTS)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {

                         new ReadPhoneThread().start();
                        } else {

                            UtilToast.showShort(DescribeActivity.this,"读取联系人权限被拒绝！");
                        }
                    }
                });



    }
    protected void initEvent() {
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
        if(marqueeTextView!=null)
      marqueeTextView.startScroll();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(marqueeTextView!=null)
        marqueeTextView.stopScroll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public class ReadPhoneThread extends Thread {

        public void run(){
            List<HashMap<String,String>> list= PhoneUtils.getAllContactInfo();
            for(int i=0;i<list.size();i++){
                String name=list.get(i).get("name");
                String phone=list.get(i).get("phone");
                if(phone!=null){
                    UserModel user=new UserModel();
                    user.setUserName(name);
                    user.setPhone(phone);
                    users.add(user);
                }
            }
            Message message=new Message();
            message.what=100;
            handler.sendMessage(message);
        }
    }
}
