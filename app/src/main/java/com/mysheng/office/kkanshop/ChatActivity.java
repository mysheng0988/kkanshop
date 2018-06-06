package com.mysheng.office.kkanshop;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.mysheng.office.kkanshop.adapter.ChatAdapter;
import com.mysheng.office.kkanshop.entity.ChatModel;
import com.mysheng.office.kkanshop.entity.Recorder;
import com.mysheng.office.kkanshop.view.AudioRecorderButton;
import com.mysheng.office.kkanshop.view.MediaManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2017/12/15.
 */

public class ChatActivity extends Activity {
    private RecyclerView recyclerView;
    private TextView titleText;
    private ImageButton backButton;
    private ChatAdapter chatAdapter;
    private List<ChatModel> mDatas = new ArrayList<>();
    private AudioRecorderButton mAudioRecorderButton;
    private View animView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_chat);
        titleText=findViewById(R.id.common_title);
        titleText.setText("聊天页面");
        recyclerView=findViewById(R.id.recyclerView);
        backButton=findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        chatAdapter=new ChatAdapter(this);
        chatAdapter.setItemClickListener(new ChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ChatModel model) {
                if(model.type==6){
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
        mAudioRecorderButton =  findViewById(R.id.id_recorder_button);
        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                ChatModel chatModel=new ChatModel();
                chatModel.type=6;
                chatModel.contentPath=filePath;
                chatModel.time= (int) Math.ceil(seconds);
                Log.d("mys", "onFinish: "+ chatModel.time);
                chatAdapter.addModel(chatModel);
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatAdapter.getItemCount()-1);

            }
        });

        initData();

    }
    private void initData(){
        String str="测试123";
        String connect="";
        for(int i=0;i<10;i++){
            int type=i%2+1;
            connect+=str+i;
            ChatModel chatModel=new ChatModel();
            chatModel.type=type;
            chatModel.content=connect;
            mDatas.add(chatModel);
        }
        for(int i=0;i<10;i++){
            int type=i%2+3;
            connect+=str+i;
            ChatModel chatModel=new ChatModel();
            chatModel.type=type;
           // chatModel.content=connect;
            mDatas.add(chatModel);
        }
        chatAdapter.addList(mDatas);
        chatAdapter.notifyDataSetChanged();
        mDatas.clear();
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
