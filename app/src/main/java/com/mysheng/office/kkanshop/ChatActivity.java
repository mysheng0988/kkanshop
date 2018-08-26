package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
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

import com.mysheng.office.kkanshop.ImageViewer.ImageTrans;
import com.mysheng.office.kkanshop.ImageViewer.imageload.MyImageLoad;
import com.mysheng.office.kkanshop.ImageViewer.imageload.MyImageTransAdapter;
import com.mysheng.office.kkanshop.ImageViewer.imageload.MyProgressBarGet;
import com.mysheng.office.kkanshop.ImageViewer.listener.SourceImageViewGet;
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
import com.mysheng.office.kkanshop.permissions.RxPermissions;
import com.mysheng.office.kkanshop.view.AudioRecorderButton;
import com.mysheng.office.kkanshop.view.MediaManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * Created by myaheng on 2017/12/15.
 */

public class ChatActivity extends Activity implements View.OnClickListener{
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
    private List<ChatModel> mDatas = new ArrayList<>();
    private List<String> listImage = new ArrayList<>();
    private List<ChatGenreBean> genreDatas = new ArrayList<>();
    private AudioRecorderButton mAudioRecorderButton;
    public  static  int SEND_LOCATION=0x110;
    private View animView;
    private Date frontMseDate;

    private int[] imageId={R.drawable.icon_images,R.drawable.icon_camera,R.drawable.icon_video,R.drawable.icon_location,R.drawable.icon_goods,R.drawable.icon_order};
    private String[] genreName={"相册","相机","摄像","定位","商品","订单"};

    public static String[] netImages = {
            "http://wx1.sinaimg.cn/woriginal/daaf97d2gy1fgsxkq8uc3j20dw0ku74x.jpg",
            "http://wx1.sinaimg.cn/woriginal/daaf97d2gy1fgsxkqm7b0j20dw0kut9h.jpg",
            "http://wx4.sinaimg.cn/woriginal/daaf97d2gy1fgsxks2l4ij20dw0kldhb.jpg",
            "http://wx2.sinaimg.cn/woriginal/daaf97d2gy1fgsxksskbkj20dw0kut9b.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2966021298,3341101515&fm=23&gp=0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496402134202&di=6c7f4a6afa5bdf02000c788f7a51e9c0&imgtype=0&src=http%3A%2F%2Fcdnq.duitang.com%2Fuploads%2Fitem%2F201506%2F23%2F20150623183946_iZtFs.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496996892&di=ea1e213c8ddd4427c55f073db9bf91b7&imgtype=jpg&er=1&src=http%3A%2F%2Fpic27.nipic.com%2F20130323%2F9483785_182530048000_2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1496996959&di=13c094ba73675a24df2ad1d2c730c02c&imgtype=jpg&er=1&src=http%3A%2F%2Fdasouji.com%2Fwp-content%2Fuploads%2F2015%2F07%2F%25E9%2595%25BF%25E8%258A%25B1%25E5%259B%25BE-6.jpg"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        initView();
        initEvent();

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

        chatAdapter=new ChatAdapter(this);
        chatAdapter.setItemClickListener(new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final View view,ChatModel chatModel,List<String> list) {
                isKeyboard=true;
                audioText.clearFocus();
                genreView.setVisibility(View.GONE);
                if(chatModel.mesType==6){
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
                    MediaManager.playSound(chatModel.contentPath, new MediaPlayer.OnCompletionListener() {

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
                }else if(chatModel.getMesType()==4){
                    switch (view.getId()){
                        case R.id.id_content_img:
                            int pos=list.indexOf(chatModel.getContentPath());
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
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                // 查看源码可知State有三种状态：SCROLL_STATE_IDLE（静止）、SCROLL_STATE_DRAGGING（上升）、SCROLL_STATE_SETTLING（下落）
//                if (newState == SCROLL_STATE_IDLE) { // 滚动静止时才加载图片资源，极大提升流畅度
//                    chatAdapter.setScrolling(false);
//                    chatAdapter.notifyDataSetChanged(); // notify调用后onBindViewHolder会响应调用
//                } else{
//                    chatAdapter.setScrolling(true);
//                }
//
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
        recyclerView.setAdapter(chatAdapter);

        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                ChatModel chatModel=new ChatModel();
                chatModel.mesType=6;
                chatModel.contentPath=filePath;
                chatModel.mesTime= (int) Math.ceil(seconds);
                Log.d("mys", "onFinish: "+ chatModel.mesType);
                chatAdapter.addModel(chatModel);
                mDatas.add(chatModel);
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
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Intent intent=new Intent(ChatActivity.this,ShareLocationActivity.class);
                            startActivityForResult(intent,SEND_LOCATION);
                        } else {
                            showToast(getString(R.string.picture_all_permission));

                        }
                    }
                });

    }

    private void initView(){
        titleText=findViewById(R.id.common_title);
        keyboard=findViewById(R.id.keyboard);
        Bundle bundle = this.getIntent().getExtras();
        String sendUserName = bundle.getString("sendUserName");
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
    }
    private void initEvent(){
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
//        int num=0;
//        String str="测试123";
//        String connect="";
        long timeDate= new Date().getTime()-12*60*60*1000;
//        for(int i=0;i<10;i++){
//            int type=i%2+1;
//            connect+=str+i;
//            ChatModel chatModel=new ChatModel();
//            chatModel.mesType=type;
//            chatModel.content=connect;
//            timeDate+=Math.random()*1000000f;
//            chatModel.setMesDate(new Date(timeDate));
//            if(isShowDate(chatModel.getMesDate())){
//                ChatModel chatModel2=new ChatModel();
//                chatModel2.mesType=7;
//                chatModel2.setMesDate(new Date(timeDate));
//                Log.d("mysheng", "initData: "+num);
//                num++;
//                mDatas.add(chatModel2);
//            }
//            frontMseDate=new Date(timeDate);
//            mDatas.add(chatModel);
//        }
//        for(int i=0;i<10;i++){
//            int type=i%2+3;
//            connect+=str+i;
//            ChatModel chatModel=new ChatModel();
//            chatModel.mesType=type;
//            timeDate+=Math.random()*1000000f;
//            chatModel.setMesDate(new Date(timeDate));
//
//            if(isShowDate(chatModel.getMesDate())){
//                ChatModel chatModel2=new ChatModel();
//                chatModel2.mesType=7;
//                chatModel2.setMesDate(new Date(timeDate));
//                Log.d("mysheng", "initData: "+num);
//                num++;
//                mDatas.add(chatModel2);
//            }
//            frontMseDate=new Date(timeDate);
//            mDatas.add(chatModel);
//        }
        for (int i=0;i<netImages.length;i++){
            ChatModel chatModel=new ChatModel();
            chatModel.mesType=4;
            timeDate+=Math.random()*100000f;
            chatModel.setMesDate(new Date(timeDate));
            chatModel.setContentPath(netImages[i]);
            listImage.add(netImages[i]);
            if(isShowDate(chatModel.getMesDate())){
                ChatModel chatModel2=new ChatModel();
                chatModel2.mesType=7;
                chatModel2.setMesDate(new Date(timeDate));
                mDatas.add(chatModel2);
            }
            frontMseDate=new Date(timeDate);
            mDatas.add(chatModel);
        }
        chatAdapter.addImages(listImage);
        chatAdapter.addList(mDatas);

        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
        for(int i=0;i<genreName.length;i++){
            ChatGenreBean genreBean=new ChatGenreBean();
            genreBean.setPosition(i);
            genreBean.setGenreImageId(imageId[i]);
            genreBean.setGenreName(genreName[i]);
            genreDatas.add(genreBean);
        }
        genreViewAdapter.addList(genreDatas);
    }
    private boolean isShowDate(Date strDate){
        long nowDate=strDate.getTime();//System.currentTimeMillis();
        if(frontMseDate==null){
            return true;
        }
        long frontDate=frontMseDate.getTime();
        if(nowDate-frontDate>10*60*1000){
            return true;
        }
        return false;
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
                ChatModel model=new ChatModel();
                model.setContentPath(media.getCompressPath());
                sendCameraImage(model);
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
            Toast.makeText(ChatActivity.this,"发送内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        ChatModel chatModel=new ChatModel();
        chatModel.mesType=2;
        chatModel.content=audioText.getText().toString().trim();
        chatModel.setMesDate(new Date());
        if(isShowDate(chatModel.getMesDate())){
            ChatModel chatModel2=new ChatModel();
            chatModel2.mesType=7;
            chatModel2.setMesDate(new Date());
            chatAdapter.addModel(chatModel2);
        }
        frontMseDate=new Date();
        mDatas.add(chatModel);
        chatAdapter.addModel(chatModel);
        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
        audioText.setText("");
    }

    /**
     * 发送拍照图片
     */
    private void sendCameraImage(ChatModel chatModel){
        chatModel.mesType=4;
        chatModel.setMesDate(new Date());
        if(isShowDate(chatModel.getMesDate())){
            ChatModel chatModel2=new ChatModel();
            chatModel2.mesType=7;
            chatModel2.setMesDate(new Date());
            chatAdapter.addModel(chatModel2);
        }
        frontMseDate=new Date();
        chatAdapter.addModel(chatModel);
        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
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
        ChatModel chatModel=new ChatModel();
        chatModel.mesType=11;
        chatModel.setContentPath(imagePath);
        chatModel.setVideoPath(videoPath);
        chatModel.setMesDate(new Date());
        if(isShowDate(chatModel.getMesDate())){
            ChatModel chatModel2=new ChatModel();
            chatModel2.mesType=7;
            chatModel2.setMesDate(new Date());
            chatAdapter.addModel(chatModel2);
        }
        frontMseDate=new Date();
        chatAdapter.addModel(chatModel);
        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
    }

    /**
     * 发送定位
     * @param chatModel
     */
    private void sendLocation( ChatModel chatModel){
        chatModel.mesType=8;
        chatModel.setMesDate(new Date());
        if(isShowDate(chatModel.getMesDate())){
            ChatModel chatModel2=new ChatModel();
            chatModel2.mesType=7;
            chatModel2.setMesDate(new Date());
            chatAdapter.addModel(chatModel2);
        }
        frontMseDate=new Date();
        chatAdapter.addModel(chatModel);
        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
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


}
