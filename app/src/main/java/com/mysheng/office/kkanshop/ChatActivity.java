package com.mysheng.office.kkanshop;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mysheng.office.kkanshop.adapter.ChatAdapter;
import com.mysheng.office.kkanshop.entity.ChatModel;
import com.mysheng.office.kkanshop.view.AudioRecorderButton;
import com.mysheng.office.kkanshop.view.MediaManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by myaheng on 2017/12/15.
 */

public class ChatActivity extends Activity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private static boolean isKeyboard=false;//默认显示切换语音
    private TextView titleText;
    private ImageButton backButton;
    private ImageView keyboard;
    private ImageView sendOut;
    private ChatAdapter chatAdapter;
    private EditText audioText;
    private List<ChatModel> mDatas = new ArrayList<>();
    private AudioRecorderButton mAudioRecorderButton;
    private View animView;
    private Date frontMseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);
        initView();
        initEvent();

        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
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
            public void onItemClick(View view, ChatModel model) {
                if(model.mesType==6){
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
                    MediaManager.playSound(model.contentPath, new MediaPlayer.OnCompletionListener() {

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
                }
            }

        });
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
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);

            }
        });

        initData();

    }
    private void initView(){
        titleText=findViewById(R.id.common_title);
        keyboard=findViewById(R.id.keyboard);
        titleText.setText("聊天页面");
        recyclerView=findViewById(R.id.recyclerView);
        backButton=findViewById(R.id.btn_back);
        audioText=findViewById(R.id.audio_text);
        sendOut=findViewById(R.id.send_out);
        mAudioRecorderButton =  findViewById(R.id.id_recorder_button);
    }
    private void initEvent(){
        keyboard.setOnClickListener(this);
        sendOut.setOnClickListener(this);
        audioText.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(ChatActivity.this.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
            }
        });
        backButton.setOnClickListener(this);
    }
    private void initData(){
        int num=0;
        String str="测试123";
        String connect="";
        long timeDate= new Date().getTime()-12*60*60*1000;
        for(int i=0;i<10;i++){
            int type=i%2+1;
            connect+=str+i;
            ChatModel chatModel=new ChatModel();
            chatModel.mesType=type;
            chatModel.content=connect;
            timeDate+=Math.random()*1000000f;
            chatModel.setMesDate(new Date(timeDate));
            if(isShowDate(chatModel.getMesDate())){
                ChatModel chatModel2=new ChatModel();
                chatModel2.mesType=7;
                chatModel2.setMesDate(new Date(timeDate));
                Log.d("mysheng", "initData: "+num);
                num++;
                mDatas.add(chatModel2);
            }
            frontMseDate=new Date(timeDate);
            mDatas.add(chatModel);
        }
//        Calendar mesDate = Calendar.getInstance();
//        mesDate.add(Calendar.M,-5);

        for(int i=0;i<10;i++){
            int type=i%2+3;
            connect+=str+i;
            ChatModel chatModel=new ChatModel();
            chatModel.mesType=type;
            timeDate+=Math.random()*1000000f;
            chatModel.setMesDate(new Date(timeDate));

            if(isShowDate(chatModel.getMesDate())){
                ChatModel chatModel2=new ChatModel();
                chatModel2.mesType=7;
                chatModel2.setMesDate(new Date(timeDate));
                Log.d("mysheng", "initData: "+num);
                num++;
                mDatas.add(chatModel2);
            }
            frontMseDate=new Date(timeDate);
            mDatas.add(chatModel);
        }

        chatAdapter.addList(mDatas);
        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
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
            case R.id.send_out:
                sendOutText();
                break;

        }
    }
    private void sendOutText(){
        String strText=audioText.getText().toString().trim();
        if("".equals(strText)){
            Toast.makeText(ChatActivity.this,"发送内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        ChatModel chatModel=new ChatModel();
        chatModel.mesType=2;
        chatModel.content=audioText.getText().toString().trim();
        chatAdapter.addModel(chatModel);
        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
        audioText.setText("");
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
