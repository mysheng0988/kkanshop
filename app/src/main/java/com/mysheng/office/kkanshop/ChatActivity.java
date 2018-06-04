package com.mysheng.office.kkanshop;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.adapter.ChatAdapter;
import com.mysheng.office.kkanshop.adapter.ClassifyAdapter;
import com.mysheng.office.kkanshop.adapter.RecorderAdapter;
import com.mysheng.office.kkanshop.entity.ChatModel;
import com.mysheng.office.kkanshop.entity.Recorder;
import com.mysheng.office.kkanshop.view.AudioRecorderButton;
import com.mysheng.office.kkanshop.view.MediaManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2017/12/15.
 */

public class ChatActivity extends Activity implements ChatAdapter.OnItemClickListener{
    private RecyclerView recyclerView;
    private TextView titleText;
    private ImageButton backButton;
    private ArrayAdapter<Recorder> mAdapter;
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
        //chatAdapter.setItemClickListener(ChatActivity.this);
        recyclerView.setAdapter(chatAdapter);
        mAudioRecorderButton =  findViewById(R.id.id_recorder_button);
        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                mDatas.clear();
                ChatModel chatModel=new ChatModel();
                chatModel.type=6;
                chatModel.contentPath=filePath;
                chatModel.time=seconds;
                mDatas.add(chatModel);
                chatAdapter.notifyItemInserted(-1);
                chatAdapter.addList(mDatas);
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


    @Override
    public void onItemClick(View view, int position) {

    }
}
