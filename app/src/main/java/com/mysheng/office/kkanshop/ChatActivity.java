package com.mysheng.office.kkanshop;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.VolleyError;
import com.mysheng.office.kkanshop.ImageViewer.ImageTrans;
import com.mysheng.office.kkanshop.ImageViewer.imageload.MyImageLoad;
import com.mysheng.office.kkanshop.ImageViewer.imageload.MyImageTransAdapter;
import com.mysheng.office.kkanshop.ImageViewer.imageload.MyProgressBarGet;
import com.mysheng.office.kkanshop.ImageViewer.listener.SourceImageViewGet;
import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.MIMC.bean.Msg;
import com.mysheng.office.kkanshop.MIMC.common.Base64Utils;
import com.mysheng.office.kkanshop.MIMC.common.UserManager;
import com.mysheng.office.kkanshop.MIMC.constant.Constant;
import com.mysheng.office.kkanshop.RxTool.SaveBitmapToImage;
import com.mysheng.office.kkanshop.adapter.ChatAdapter;
import com.mysheng.office.kkanshop.adapter.ChatGenreViewAdapter;
import com.mysheng.office.kkanshop.customCamera.config.PictureConfig;
import com.mysheng.office.kkanshop.customCamera.config.PictureMimeType;
import com.mysheng.office.kkanshop.customCamera.config.PictureSelector;
import com.mysheng.office.kkanshop.customCamera.entity.LocalMedia;
import com.mysheng.office.kkanshop.customCamera.util.LogUtils;
import com.mysheng.office.kkanshop.customCamera.util.StringUtils;
import com.mysheng.office.kkanshop.entity.ChatGenreBean;
import com.mysheng.office.kkanshop.entity.ChatModel;
import com.mysheng.office.kkanshop.entity.ChatTools;
import com.mysheng.office.kkanshop.listenter.MIMCUpdateChatMsg;
import com.mysheng.office.kkanshop.permissions.RxPermissions;
import com.mysheng.office.kkanshop.service.MIMCService;
import com.mysheng.office.kkanshop.util.SharedPreferencesUtils;
import com.mysheng.office.kkanshop.util.UtilToast;
import com.mysheng.office.kkanshop.util.VolleyJsonInterface;
import com.mysheng.office.kkanshop.util.VolleyRequest;
import com.mysheng.office.kkanshop.view.AudioRecorderButton;
import com.mysheng.office.kkanshop.view.MediaManager;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomi.mimc.MIMCGroupMessage;
import com.xiaomi.mimc.MIMCMessage;
import com.xiaomi.mimc.MIMCServerAck;
import com.xiaomi.mimc.MIMCUser;
import com.xiaomi.mimc.common.MIMCConstant;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.reactivex.functions.Consumer;


/**
 * Created by myaheng on 2017/12/15.
 */

public class ChatActivity extends BaseActivity implements ChatGenreViewAdapter.OnItemClickListener,ChatAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private RecyclerView genreView;
    private static boolean isKeyboard=false;//默认显示切换语音
    private RxPermissions rxPermissions;
    private TextView titleText;
    private ImageButton backButton;
    private ImageView keyboard;
    private Button sendOut;
    private ImageView addItem;
    private ChatAdapter chatAdapter;
    private ChatGenreViewAdapter genreViewAdapter;
    private LinearLayoutManager layoutManager;
    private EditText audioText;
    private List<ChatMsg> mDatas = new ArrayList<>();
    private List<String> imagePath = new ArrayList<>();
    private List<ChatGenreBean> genreDatas = new ArrayList<>();
    private AudioRecorderButton mAudioRecorderButton;
    public  static  int SEND_LOCATION=0x110;
    private View animView;
    private long frontMseDate;
    private long endTime;
    private RefreshLayout refreshLayout;

    private int[] imageId={R.drawable.icon_images,R.drawable.icon_camera,R.drawable.icon_video,R.drawable.icon_location,R.drawable.icon_phone,R.drawable.icon_goods,R.drawable.icon_order};
    private String[] genreName={"相册","相机","摄像","定位","语音","商品","订单"};

    private SharedPreferencesUtils shareData;
    private String sendUserName;
    private String sendUserId;
    private MIMCService mimcService;
    private String userId;
    private String token;
    private MIMCUser mimcUser;
    private UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareData=new SharedPreferencesUtils(this);
        userId= (String) shareData.getParam("phone","");
        mDatas.clear();
        startMIMCService();
        userManager=UserManager.getInstance();
        setContentView(R.layout.chat_layout);
        initView();
        initEvent();
        endTime=System.currentTimeMillis();



        layoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        //layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if(bottom<oldBottom){
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                layoutManager.scrollToPositionWithOffset(mDatas.size()-1,0);
                            }
                        },100);
                    }
            }
        });

        chatAdapter=new ChatAdapter(this,mDatas,imagePath);
        chatAdapter.setItemClickListener(this);
        recyclerView.setAdapter(chatAdapter);

        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                chatAdapter.notifyDataSetChanged();
                layoutManager.scrollToPositionWithOffset(mDatas.size()-1,0);

            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,4);
        genreView.setLayoutManager(gridLayoutManager);
        genreViewAdapter=new ChatGenreViewAdapter(this);
        genreView.setAdapter(genreViewAdapter);
        genreViewAdapter.setItemClickListener(this);

        initData();
    }

    private void startLocation() {
        rxPermissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent intent=new Intent(ChatActivity.this,ShareLocationActivity.class);
                            startActivityForResult(intent,SEND_LOCATION);
                        } else {
                            showToast(getString(R.string.picture_all_permission));

                        }
                    }
                });

    }
    @Override
    protected void initView(){
        titleText=findViewById(R.id.common_title);
        keyboard=findViewById(R.id.keyboard);
        Bundle bundle = getIntent().getExtras();
        sendUserName = bundle.getString("sendUserName");
        sendUserId = bundle.getString("sendUserId");
        titleText.setText(sendUserName);
        rxPermissions = new RxPermissions(this);
        recyclerView=findViewById(R.id.recyclerView);
        genreView=findViewById(R.id.chatRecyclerView);
        recyclerView.setOnClickListener(this);
        backButton=findViewById(R.id.btn_back);
        audioText=findViewById(R.id.audio_text);
        sendOut=findViewById(R.id.send_out);
        addItem=findViewById(R.id.add_item);
        mAudioRecorderButton =  findViewById(R.id.id_recorder_button);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(true));
        refreshLayout.setPrimaryColorsId(R.color.light_salmon);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getHistoryChatListMore();
            }
        });

    }
    @Override
    protected void initEvent(){
        keyboard.setOnClickListener(this);
        sendOut.setOnClickListener(this);
        addItem.setOnClickListener(this);
        audioText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(ChatActivity.this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }else{
                    genreView.setVisibility(View.GONE);
                }
            }
        });
        audioText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(audioText.getText().toString().trim().length()>0){
                        addItem.setVisibility(View.GONE);
                        sendOut.setVisibility(View.VISIBLE);
                    }else {
                        sendOut.setVisibility(View.GONE);
                        addItem.setVisibility(View.VISIBLE);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backButton.setOnClickListener(this);
    }
    private void initData(){

        for(int i=0;i<genreName.length;i++){
            ChatGenreBean genreBean=new ChatGenreBean();
            genreBean.setPosition(i);
            genreBean.setGenreImageId(imageId[i]);
            genreBean.setGenreName(genreName[i]);
            genreDatas.add(genreBean);
        }
        genreViewAdapter.addList(genreDatas);
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
            mimcService = ((MIMCService.MIMCBinder) service).getService();
            mimcUser=mimcService.getMimcUser();
            mimcUser.login();
            getHistoryChatList();
            mimcService.setUpdateChatMsg(new MIMCUpdateChatMsg() {
                @Override
                public void noticeNewMsg(ChatMsg chatMsg) {
                    showDateNum(chatMsg.getMsg().getTimestamp(),-1);
                    mDatas.add(chatMsg);
                    chatAdapter.notifyDataSetChanged();
                    layoutManager.scrollToPositionWithOffset(mDatas.size()-1,0);
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
    private void getHistoryChatList(){
        Log.e("history", "onSuccess: "+mDatas.size() );
        long statTime=endTime-24*60*60*1000;
        token=mimcUser.getToken();
        String strURL="https://mimc.chat.xiaomi.net/api/msg/p2p/queryOnCount";
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("toAccount", sendUserId);
        hashMap.put("fromAccount", userId);
        hashMap.put("utcFromTime",statTime+"");
        hashMap.put("utcToTime",endTime+"");
        hashMap.put("count","20");
        JSONObject jsonParams = new JSONObject(hashMap);
        VolleyRequest.JsonRequestPost(strURL,"json",token,jsonParams,new VolleyJsonInterface(this, VolleyJsonInterface.mListener, VolleyJsonInterface.errorListener) {
            @Override
            public void onSuccess(JSONObject result) {
                String code="",message="";

                try {
                    code=result.getString("code");
                    message=result.getString("message");
                    if(code.equals("200")){
                        JSONObject jsonObject=result.getJSONObject("data");
                        endTime=jsonObject.getLong("timestamp");
                        JSONArray jsonArray=jsonObject.getJSONArray("messages");
                        String payload="";
                        for(int i=0;i<jsonArray.length();i++){
                            payload=jsonArray.getJSONObject(i).getString("payload");
                            payload=Base64Utils.getFromBase64(payload);
                            String fromAccount=jsonArray.getJSONObject(i).getString("fromAccount");
                            JSONObject obj=new JSONObject(payload);
                            ChatMsg chatMsg=new ChatMsg();
                            chatMsg.setFromAccount(fromAccount);
                            chatMsg.setSingle(true);
                            Msg msg=new Msg();
                            String content=obj.getString("content");
                            int msgType=obj.getInt("msgType");
                            content=Base64Utils.getFromBase64(content);
                            if(Constant.PIC_FILE==msgType){
                                imagePath.add(content);
                            }
                            msg.setContent(content.getBytes());
                            long timestamp=obj.getLong("timestamp");
                            showDateNum(timestamp,-1);
                            msg.setTimestamp(timestamp);
                            msg.setMsgType(Integer.parseInt(obj.getString("msgType")));
                            chatMsg.setMsg(msg);
                            mDatas.add(chatMsg);
                        }
                        Log.e("history", "onSuccess: "+mDatas.size() );
                        chatAdapter.notifyDataSetChanged();
                        layoutManager.scrollToPositionWithOffset(mDatas.size()-1,0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    /**
     * 获取更多历史记录
     */
    private void getHistoryChatListMore(){
        long statTime=endTime-24*60*60*1000;
        token=mimcUser.getToken();
        String strURL="https://mimc.chat.xiaomi.net/api/msg/p2p/queryOnCount";
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("toAccount", sendUserId);
        hashMap.put("fromAccount", userId);
        hashMap.put("utcFromTime",statTime+"");
        hashMap.put("utcToTime",endTime+"");
        hashMap.put("count","20");
        JSONObject jsonParams = new JSONObject(hashMap);
        VolleyRequest.JsonRequestPost(strURL,"json",token,jsonParams,new VolleyJsonInterface(this, VolleyJsonInterface.mListener, VolleyJsonInterface.errorListener) {
            @Override
            public void onSuccess(JSONObject result) {
                String code="",message="";
                int dataSize=0;
                int num=0;
                try {
                    code=result.getString("code");
                    message=result.getString("message");
                    if(code.equals("200")){
                        JSONObject jsonObject=result.getJSONObject("data");
                        if(jsonObject.isNull("timestamp")){
                            refreshLayout.resetNoMoreData();
                            refreshLayout.finishRefresh(true);
                            return;
                        }
                        endTime=jsonObject.getLong("timestamp");
                        JSONArray jsonArray=jsonObject.getJSONArray("messages");
                        String payload="";
                         dataSize=jsonArray.length();
                        for(int i=0;i<jsonArray.length();i++){
                            payload=jsonArray.getJSONObject(i).getString("payload");
                            payload=Base64Utils.getFromBase64(payload);
                            String fromAccount=jsonArray.getJSONObject(i).getString("fromAccount");
                            JSONObject obj=new JSONObject(payload);
                            ChatMsg chatMsg=new ChatMsg();
                            chatMsg.setFromAccount(fromAccount);
                            chatMsg.setSingle(true);
                            Msg msg=new Msg();
                            String content=obj.getString("content");
                            content=Base64Utils.getFromBase64(content);
                            int msgType=obj.getInt("msgType");
                            if(Constant.PIC_FILE==msgType){
                                imagePath.add(content);
                            }
                            msg.setContent(content.getBytes());
                            long timestamp=obj.getLong("timestamp");
                            msg.setTimestamp(timestamp);
                            msg.setMsgType(Integer.parseInt(obj.getString("msgType")));
                            chatMsg.setMsg(msg);
                             num=num+showDateNum(timestamp,i+num);
                            mDatas.add(i+num,chatMsg);
                        }
                        dataSize=dataSize+num;
                        chatAdapter.notifyDataSetChanged();
                        layoutManager.scrollToPositionWithOffset(dataSize,0);
                        refreshLayout.finishRefresh(true);
                    }else{
                        refreshLayout.finishRefresh(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    /**
     *  显示聊天时间
     * @param time
     * @param position
     * @return
     */
    private int showDateNum(long time,int position){
        if(time-frontMseDate>5*60*1000){
            ChatMsg chatMsg=new ChatMsg();
            Msg msg=new Msg();
            msg.setMsgType(ChatTools.SEND_TIME);
            msg.setTimestamp(time);
            chatMsg.setFromAccount(userId);
            chatMsg.setSingle(true);
            chatMsg.setMsg(msg);
            if(position>-1){
                mDatas.add(position,chatMsg);
            }else {
                mDatas.add(chatMsg);
            }
            frontMseDate=time;
            return 1;
        }
        frontMseDate=time;
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.keyboard:
                switchToTextAndAudio();
                break;
            case R.id.add_item:
                if(genreView.getVisibility()==View.VISIBLE){
                    genreView.setVisibility(View.GONE);
                }else{
                    genreView.setVisibility(View.VISIBLE);
                }
                isKeyboard=true;
                audioText.clearFocus();
                break;
            case R.id.send_out:
                sendOutText();
                break;
            default:
                genreView.setVisibility(View.GONE);
                break;

        }
    }

    public void onTakePhoto(final int chooseMode) {

        //启动相机拍照,先判断手机是否有拍照、录音、写入权限
        rxPermissions.request(android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            PictureSelector.create(ChatActivity.this)
                                    .openCamera(chooseMode)
                                    .compress(true)
                                    .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                        } else {
                            showToast(getString(R.string.picture_all_permission));

                        }
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode==SEND_LOCATION){
                ChatModel model= (ChatModel) data.getSerializableExtra("data");
                sendLocation(model);
               // Toast.makeText(ChatActivity.this, "发送：" + model.getTabTitle() + "  " + model.getAddress() + "  " + "纬度：" +model.getLatitude() + "  " + "经度：" + model.getLongitude(), Toast.LENGTH_SHORT).show();
            }else if(requestCode==PictureConfig.CHOOSE_REQUEST){
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    for (int i = 0; i < selectList.size(); i++) {
                        handleLocalMedia(selectList.get(i));
                    }
            }
//            switch (requestCode) {
//                case SEND_LOCATION:
//                    showToast(getString(R.string.picture_all_permission));
//                    break;
//                case PictureConfig.CHOOSE_REQUEST:
//                    // 图片选择,共用一个数据通道:返回时图片，可能为列表，视频只能有一个
//                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                    for (int i = 0; i < selectList.size(); i++) {
//                        handleLocalMedia(selectList.get(i));
//                    }
//                    break;
//
//            }
        }
    }
    private void handleLocalMedia(LocalMedia media) {
        int pictureType = PictureMimeType.isPictureType(media.getPictureType());
        switch (pictureType) {
            case PictureConfig.TYPE_IMAGE:

                LogUtils.e("TEST===> media path = " + media.getPath()
                        + ",  compressPath = " + media.getCompressPath()
                        + ", height = " + media.getHeight()
                        + ", width = " + media.getWidth());
                //showToast(media.getPath());
//                ChatModel model=new ChatModel();
//                model.setContentPath(media.getCompressPath());
//                sendCameraImage(model);
                break;
            case PictureConfig.TYPE_VIDEO:
                if (TextUtils.isEmpty(media.getPath())) return;
                if (!StringUtils.fileIsExists(media.getPath())) {
                    LogUtils.e("文件可能不存在了~");
                    return;
                }
               LogUtils.e("TEST===> video path = " + media.getPath()
                        + ",  compressPath = " + media.getCompressPath()
                        + ", height = " + media.getHeight()
                        + ", width = " + media.getWidth());
                MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
                metadataRetriever.setDataSource(media.getPath());

                Bitmap bitmap = metadataRetriever.getFrameAtTime();
                String savePath= SaveBitmapToImage.saveBitmap(ChatActivity.this,bitmap);
                sendCameraVideo(media.getPath(),savePath);
                // showToast(media.getPath());
                break;
        }
    }
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 发送文字
     */
    private void sendOutText(){
        String strText=audioText.getText().toString().trim();
        if("".equals(strText)){
           UtilToast.showShort(ChatActivity.this,"发送内容不能为空");
            return;
        }
       userManager = UserManager.getInstance();

        if (mimcUser != null){
            userManager.sendMsg(sendUserId, strText.getBytes(), Constant.TEXT);
        }
        audioText.setText("");
    }

    /**
     * 发送拍照图片
     */
    private void sendCameraImage(){
        Random random=new Random();
        int index=random.nextInt(ChatTools.netImages.length);
        //ChatTools.netImages[index];
        String imagePath="http://wx1.sinaimg.cn/woriginal/daaf97d2gy1fgsxkq8uc3j20dw0ku74x.jpg";
        userManager.sendMsg(sendUserId, imagePath.getBytes(), Constant.PIC_FILE);
    }

    /**
     * 发送相册图片
     */
    private void sendPhotoImage(String path){

    }

    /**
     * 发送短视频
     */
    private void sendCameraVideo(String videoPath,String imagePath){
//        ChatModel chatModel=new ChatModel();
//        chatModel.mesType=11;
//        chatModel.setContentPath(imagePath);
//        chatModel.setVideoPath(videoPath);
//        chatModel.setMesDate(new Date());
//        if(isShowDate(chatModel.getMesDate())){
//            ChatModel chatModel2=new ChatModel();
//            chatModel2.mesType=7;
//            chatModel2.setMesDate(new Date());
//            chatAdapter.addModel(chatModel2);
//        }
//        frontMseDate=new Date();
//        chatAdapter.addModel(chatModel);
//        chatAdapter.notifyDataSetChanged();
//        layoutManager.scrollToPositionWithOffset(mDatas.size()-1,0);
    }

    /**
     * 发送定位
     * @param chatModel
     */
    private void sendLocation( ChatModel chatModel){
//        chatModel.mesType=8;
//        chatModel.setMesDate(new Date());
//        if(isShowDate(chatModel.getMesDate())){
//            ChatModel chatModel2=new ChatModel();
//            chatModel2.mesType=7;
//            chatModel2.setMesDate(new Date());
//            chatAdapter.addModel(chatModel2);
//        }
//        frontMseDate=new Date();
//        chatAdapter.addModel(chatModel);
//        chatAdapter.notifyDataSetChanged();
//        layoutManager.scrollToPositionWithOffset(mDatas.size()-1,0);
    }
    private void switchToTextAndAudio(){
        if(isKeyboard){
            isKeyboard=false;
            keyboard.setImageResource(R.drawable.audio);
            audioText.setVisibility(View.VISIBLE);
            mAudioRecorderButton.setVisibility(View.GONE);
        }else{
            isKeyboard=true;
            audioText.clearFocus();
            keyboard.setImageResource(R.drawable.keyboard);
            audioText.setVisibility(View.GONE);
            mAudioRecorderButton.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        MediaManager.release();
    }


    @Override
    public void onItemClick(View view, ChatGenreBean model) {
        if(model.getPosition()==0){
            sendCameraImage();
        }else if(model.getPosition()==1){
            onTakePhoto(PictureMimeType.ofImage());
        }else if(model.getPosition()==2){
            onTakePhoto(PictureMimeType.ofVideo());
        }else if(model.getPosition()==3){
            startLocation();
        }else if(model.getPosition()==4){
            if (UserManager.getInstance().getStatus() == MIMCConstant.OnlineStatus.ONLINE) {
                VoiceCallActivity.actionStartActivity(ChatActivity.this, sendUserId);
            } else {
                Toast.makeText(ChatActivity.this, getResources().getString(R.string.not_login), Toast.LENGTH_SHORT).show();
            }
        }
        genreView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(final View view, ChatMsg model, List<String> lists) {
        isKeyboard=true;
        audioText.clearFocus();
        genreView.setVisibility(View.GONE);
        if(model.getMsg().getMsgType()==Constant.AUDIO_FILE){
            //播放动画
            if(animView != null) {
                animView.setBackgroundResource(R.drawable.adj);
                animView = null;
            }
            animView = view.findViewById(R.id.id_recorder_anim);
            animView.setBackgroundResource(R.drawable.play_anim);
            AnimationDrawable anim = (AnimationDrawable) animView.getBackground();
            anim.start();
            //播放音频
            MediaManager.playSound(model.getMsg().getContent().toString(), new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    animView.setBackgroundResource(R.drawable.adj);
                }
            }, new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    MediaManager.release();
                    animView.setBackgroundResource(R.drawable.adj);
                    return false;
                }
            });
        }else if(model.getMsg().getMsgType()== Constant.PIC_FILE){
            switch (view.getId()){
                case R.id.id_content_img:
                    String content=new String(model.getMsg().getContent());
                    int pos=lists.indexOf(content);
                    ImageTrans.with(ChatActivity.this)
                            .setImageList(lists)
                            .setSourceImageView(new SourceImageViewGet() {
                                @Override
                                public ImageView getImageView(int pos) {
                                    return (ImageView)view.findViewById(R.id.id_content_img);
                                }
                            })
                            .setImageLoad(new MyImageLoad())
                            .setNowIndex(pos)
                            .setProgressBar(new MyProgressBarGet())
                            .setAdapter(new MyImageTransAdapter())
                            .show();
                    break;
            }

        }
    }
}
