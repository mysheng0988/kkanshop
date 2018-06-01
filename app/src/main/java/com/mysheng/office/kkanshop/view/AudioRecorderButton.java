package com.mysheng.office.kkanshop.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.mysheng.office.kkanshop.view.AudioManager.AudioStateListener;

import com.mysheng.office.kkanshop.R;

/**
 * Created by myaheng on 2017/12/15.
 */

@SuppressLint("AppCompatCustomView")
public class AudioRecorderButton extends Button implements AudioStateListener {
    public static int IMAGE=0x001;
    public static int CAMERA=0x002;
    public static int AUDIO=0x003;
    private static final int DISTANCE_Y_CANCEL=50;
    private static final int STATE_NORMAL=1;
    private static final int STATE_RECORDING=2;
    private static final int STATE_WANT_TO_CANCEL=3;
    private int mCurState=STATE_NORMAL;
    private boolean isRecording=false;
    private DialogManager mDialogManager;
    private AudioManager mAudioManager;
    private  float mTime;
    private boolean mReady;
    private Context context;

    public AudioRecorderButton(Context context) {

        this(context,null);
    }

    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        mDialogManager=new DialogManager(getContext());
        String dir= Environment.getExternalStorageDirectory()+"/mysheng_recorder_audio";
        mAudioManager=AudioManager.getInstance(dir);
        mAudioManager.setOnAudioStateListener(this);
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mReady=true;
                mAudioManager.prepareAudio();

                return false;
            }
        });
    }
    public interface AudioFinishRecorderListener{
        void onFinish(float seconds, String filePath);
    }
    private AudioFinishRecorderListener audioListener;
    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener){
        audioListener=listener;
    }

    @Override
    public void wellPrepared() {
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }
    private Runnable mGetVoiceLevelRunnable=new Runnable() {
        @Override
        public void run() {
            while (isRecording){
                try {
                    Thread.sleep(100);
                    mTime+=0.1f;
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private static final int MSG_AUDIO_PREPARED=0x110;
    private static final int MSG_VOICE_CHANGED=0x111;
    private static final int MSG_DIALOG_DIMISS=0x112;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_AUDIO_PREPARED:
                    mDialogManager.showRecordingDialog();
                    isRecording=true;
                    new Thread(mGetVoiceLevelRunnable).start();
                    break;

                case MSG_VOICE_CHANGED:
                    mDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
                    break;
                case MSG_DIALOG_DIMISS:
                    mDialogManager.dimissDialog();
                    break;
            }
        }
    };
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        int x= (int) event.getX();
        int y= (int) event.getY();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //PackageManager pm = context.getPackageManager();
//                if(!(pm.checkPermission("android.permission.RECORD_AUDIO", "com.mysheng.office.kkanshop")== PackageManager.PERMISSION_GRANTED ) ) {
//                    ActivityCompat.requestPermissions((Activity) context,new String[]{android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, AUDIO);
//                }else{
//                    changeState(STATE_RECORDING);
//                }
                changeState(STATE_RECORDING);
            break;
            case MotionEvent.ACTION_MOVE:
                if(isRecording){
                    if(wantCancel(x,y)){
                        changeState(STATE_WANT_TO_CANCEL);
                    }else{
                        changeState(STATE_RECORDING);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if(!mReady){
                    reset();
                   return super.onTouchEvent(event);
                }
                if(!isRecording||mTime<0.6f){
                    mDialogManager.tooShort();
                    mAudioManager.cancel();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS,1300);
                }else if(mCurState==STATE_RECORDING){
                    mDialogManager.dimissDialog();
                    if(audioListener!=null){
                        audioListener.onFinish(mTime,mAudioManager.getCurrentFilePath());
                    }
                    mAudioManager.release();


                }else if(mCurState==STATE_WANT_TO_CANCEL){
                    mDialogManager.dimissDialog();
                    mAudioManager.cancel();
                }
                reset();
                break;
        }
        return super.onTouchEvent(event);
    }
    private void reset(){
        mReady=false;
        isRecording=false;
        changeState(STATE_NORMAL);
        mTime=0;
    }
    private void changeState(int state) {
        if(mCurState!=state){
            mCurState=state;
            switch (state){
                case  STATE_NORMAL:
                    setBackgroundResource(R.drawable.recorder_button_normal);
                    setText(R.string.str_recorder_normal);
                    break;
                case  STATE_RECORDING:
                    setBackgroundResource(R.drawable.recorder_button_recording);
                    setText(R.string.str_recorder_recording);
                    if(isRecording){
                        mDialogManager.recording();
                    }
                    break;
                case  STATE_WANT_TO_CANCEL:
                    setBackgroundResource(R.drawable.recorder_button_recording);
                    setText(R.string.str_recorder_cancel);
                    if(isRecording){
                        mDialogManager.wantToCancel();
                    }
                    break;
            }
        }
    }
    private boolean wantCancel(int x,int y){
        if(x<0||x>getWidth()){
            return true;
        }
        if(y<-DISTANCE_Y_CANCEL||y>getHeight()+DISTANCE_Y_CANCEL){
            return  true;
        }
        return false;
    }


}
