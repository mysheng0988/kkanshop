package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.adapter.UserAdapter;
import com.mysheng.office.kkanshop.adapter.ViewLineDivider;
import com.mysheng.office.kkanshop.customVideoPlayer.NiceVideoPlayer;
import com.mysheng.office.kkanshop.customVideoPlayer.NiceVideoPlayerManager;
import com.mysheng.office.kkanshop.customVideoPlayer.TxVideoPlayerController;
import com.mysheng.office.kkanshop.entity.UserModel;
import com.mysheng.office.kkanshop.permissions.RxPermissions;
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

public class DescribeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView comeBack;

    private MarqueeTextView marqueeTextView;
     private RecyclerView mRecyclerView;

    private List<UserModel> users=new ArrayList<>();

    private UserAdapter adapter;
    public RxPermissions rxPermissions;
    private NiceVideoPlayer mNiceVideoPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //方式一：这句代码必须写在setContentView()方法的后面
        getSupportActionBar().hide();

        //方式二：这句代码必须写在setContentView()方法的前面
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        rxPermissions = new RxPermissions(this);
        setContentView(R.layout.describe_layout);
        Utils.init(DescribeActivity.this);
        initView();
        initEvent();

        init();
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
    private void init() {
        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.niceVideoPlayer);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        String videoUrl = "http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super";
//        videoUrl = Environment.getExternalStorageDirectory().getPath().concat("/办公室小野.mp4");
        mNiceVideoPlayer.setUp(videoUrl, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("办公室小野开番外了，居然在办公室开澡堂！老板还点赞？");
        controller.setLenght(98000);
        Glide.with(this)
                .load("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg")
                .placeholder(R.drawable.img_default)
                .crossFade()
                .into(controller.imageView());
        mNiceVideoPlayer.setController(controller);
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
    public void enterTinyWindow(View view) {
        if (mNiceVideoPlayer.isIdle()) {
            Toast.makeText(this, "要点击播放后才能进入小窗口", Toast.LENGTH_SHORT).show();
        } else {
            mNiceVideoPlayer.enterTinyWindow();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(marqueeTextView!=null){
            getSupportActionBar().hide();
            marqueeTextView.startScroll();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(marqueeTextView!=null){
            marqueeTextView.stopScroll();
        }

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
