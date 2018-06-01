package com.mysheng.office.kkanshop;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.adapter.RecorderAdapter;
import com.mysheng.office.kkanshop.entity.Recorder;
import com.mysheng.office.kkanshop.view.AudioRecorderButton;
import com.mysheng.office.kkanshop.view.MediaManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2017/12/15.
 */

public class ChatActivity extends Activity {
    private ListView mListView;
    private TextView titleText;
    private ImageButton backButton;
    private ArrayAdapter<Recorder> mAdapter;
    private List<Recorder> mDatas = new ArrayList<Recorder>();
    private AudioRecorderButton mAudioRecorderButton;
    private View animView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_chat);
        titleText=findViewById(R.id.common_title);
        titleText.setText("聊天页面");
        backButton=findViewById(R.id.btn_back);
        PackageManager pm = ChatActivity.this.getPackageManager();
        if(! (pm.checkPermission("android.permission.RECORD_AUDIO", "com.example.hwxnemr.hwxnemr_app")==PackageManager.PERMISSION_GRANTED ) ) {
            ActivityCompat.requestPermissions( ChatActivity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView = findViewById(R.id.id_listView);

        mAudioRecorderButton =  findViewById(R.id.id_recorder_button);
        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                Recorder recorder = new Recorder(seconds,filePath);
                mDatas.add(recorder);
                mAdapter.notifyDataSetChanged();//通知更新的内容
                mListView.setSelection(mDatas.size() - 1);//将lisview设置为最后一个
            }
        });
        mAdapter = new RecorderAdapter(this,mDatas);
        mListView.setAdapter(mAdapter);

        //listView的item点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(animView != null) {
                    animView.setBackgroundResource(R.drawable.adj);
                    animView = null;
                }
                //播放动画
                animView = view.findViewById(R.id.id_recorder_anim);
                animView.setBackgroundResource(R.drawable.play_anim);
                AnimationDrawable anim = (AnimationDrawable) animView.getBackground();
                anim.start();
                //播放音频
                MediaManager.playSound(mDatas.get(position).filePath, new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        animView.setBackgroundResource(R.drawable.adj);
                    }
                }, new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        MediaManager.release();
                        return false;
                    }
                });
            }
        });
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
