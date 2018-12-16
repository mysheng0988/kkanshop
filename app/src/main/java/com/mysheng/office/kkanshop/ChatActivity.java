package com.mysheng.office.kkanshop;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import com.mysheng.office.kkanshop.permissions.RxPermissions;
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

import io.reactivex.functions.Consumer;


/**
 * Created by myaheng on 2017/12/15.
 */

public class ChatActivity extends BaseActivity implements View.OnClickListener,UserManager.OnHandleMIMCMsgListener{
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
    private EditText audioText;
    private List<ChatMsg> mDatas = new ArrayList<>();
    private List<ChatGenreBean> genreDatas = new ArrayList<>();
    private AudioRecorderButton mAudioRecorderButton;
    public  static  int SEND_LOCATION=0x110;
    private View animView;
    private long frontMseDate;
    private long endTime;
    private RefreshLayout refreshLayout;

    private int[] imageId={R.drawable.icon_images,R.drawable.icon_camera,R.drawable.icon_video,R.drawable.icon_location,R.drawable.icon_phone,R.drawable.icon_goods,R.drawable.icon_order};
    private String[] genreName={"相册","相机","摄像","定位","语音","商品","订单"};



    private String userId;
    private SharedPreferencesUtils shareData;
    private String sendUserName;
    private String sendUserId;
    private String token;
    private  MIMCUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareData=new SharedPreferencesUtils(this);
        userId= (String) shareData.getParam("phone","");
        user = UserManager.getInstance().newUser(userId);
        user.login();
        mDatas.clear();
        setContentView(R.layout.chat_layout);
        initView();
        initEvent();
        endTime=System.currentTimeMillis();
        getHistoryChatList();


        LinearLayoutManager layoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        //layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if(bottom<oldBottom){
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
                            }
                        },100);
                    }
            }
        });

        chatAdapter=new ChatAdapter(this,mDatas);
        chatAdapter.setItemClickListener(new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view,ChatMsg chatMsg,List<String> list) {
                isKeyboard=true;
                audioText.clearFocus();
                genreView.setVisibility(View.GONE);
                if(chatMsg.getMsg().getMsgType()==Constant.AUDIO_FILE){
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
                    MediaManager.playSound(chatMsg.getMsg().getContent().toString(), new MediaPlayer.OnCompletionListener() {

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
                }else if(chatMsg.getMsg().getMsgType()== Constant.PIC_FILE){
                    switch (view.getId()){
                        case R.id.id_content_img:
                            int pos=list.indexOf(chatMsg.getMsg().getContent().toString());
                            ImageTrans.with(ChatActivity.this)
                                    .setImageList(list)
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

        });
        recyclerView.setAdapter(chatAdapter);

        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);

            }
        });
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,4);
        genreView.setLayoutManager(gridLayoutManager);
        genreViewAdapter=new ChatGenreViewAdapter(this);
        genreView.setAdapter(genreViewAdapter);
        genreViewAdapter.setItemClickListener(new ChatGenreViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ChatGenreBean model) {
                if(model.getPosition()==1){
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
        });

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
        // 设置处理MIMC消息监听器
        UserManager.getInstance().setHandleMIMCMsgListener(this);
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
    private void getHistoryChatList(){
        Log.e("history", "onSuccess: "+mDatas.size() );
        long statTime=endTime-24*60*60*1000;
        token=user.getToken();
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
                            content=Base64Utils.getFromBase64(content);
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
                        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
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
    private void getHistoryChatListMore(){
        long statTime=endTime-24*60*60*1000;
        token=user.getToken();
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
                        recyclerView.scrollToPosition(dataSize+5);
                        refreshLayout.resetNoMoreData();
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

    private int showDateNum(long time,int position){
        if(time-frontMseDate>10*60*1000){
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
        UserManager userManager = UserManager.getInstance();

        if (user != null){
            userManager.sendMsg(sendUserId, strText.getBytes(), Constant.TEXT);
        }
        audioText.setText("");
    }

    /**
     * 发送拍照图片
     */
    private void sendCameraImage(ChatModel chatModel){
//        chatModel.mesType=4;
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
//        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
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
//        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
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
//        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
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
        MediaManager.release();
    }


    @Override
    public void onHandleMessage(final ChatMsg chatMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDateNum(chatMsg.getMsg().getTimestamp(),-1);
                mDatas.add(chatMsg);
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public void onHandleGroupMessage(ChatMsg chatMsg) {

    }

    @Override
    public void onHandleStatusChanged(MIMCConstant.OnlineStatus status) {

    }

    @Override
    public void onHandleServerAck(MIMCServerAck serverAck) {

    }

    @Override
    public void onHandleCreateGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQueryGroupInfo(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQueryGroupsOfAccount(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleJoinGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQuitGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleKickGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleUpdateGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleDismissGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandlePullP2PHistory(String json, boolean isSuccess) {

    }

    @Override
    public void onHandlePullP2THistory(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleSendMessageTimeout(MIMCMessage message) {

    }

    @Override
    public void onHandleSendGroupMessageTimeout(MIMCGroupMessage groupMessage) {

    }

    @Override
    public void onHandleJoinUnlimitedGroup(long topicId, int code, String errMsg) {

    }

    @Override
    public void onHandleQuitUnlimitedGroup(long topicId, int code, String errMsg) {

    }

    @Override
    public void onHandleDismissUnlimitedGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQueryUnlimitedGroupMembers(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQueryUnlimitedGroups(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQueryUnlimitedGroupOnlineUsers(String json, boolean isSuccess) {

    }
}
