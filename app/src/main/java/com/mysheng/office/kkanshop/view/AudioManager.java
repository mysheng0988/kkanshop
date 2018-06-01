package com.mysheng.office.kkanshop.view;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by mysheng on 2017/12/15.
 */

public class AudioManager {
    private MediaRecorder mediaRecorder;
    public String mDir;
    private String mCurrentFilePath;
    private boolean isPrepared;
    private static AudioManager mIntantce;
    public AudioManager(String dir){
        mDir=dir;
    };
    public interface AudioStateListener{
        void wellPrepared();
    }
    public AudioStateListener mListener;
    public void  setOnAudioStateListener(AudioStateListener listener){
        mListener=listener;
    }
    public static AudioManager getInstance(String dir){
        if(mIntantce==null){
            synchronized (AudioManager.class){
                if(mIntantce==null){
                    mIntantce=new AudioManager(dir);
                }
            }
        }
         return mIntantce;
    }
    public void prepareAudio(){
        try {
            File dir=new File(mDir);
            isPrepared=false;
            if(!dir.exists()){
                dir.mkdir();
            }
        String fileName=generateFileName();
        File file=new File(dir,fileName);
          mCurrentFilePath=file.getAbsolutePath();
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setOutputFile(file.getAbsolutePath());
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//音频源为麦克风
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mediaRecorder.prepare();
            mediaRecorder.start();
            isPrepared=true;
            if(mListener!=null){
                mListener.wellPrepared();
            }

        } catch (IllegalStateException|IOException e) {
            e.printStackTrace();
        }
    }

    private String generateFileName() {

        return UUID.randomUUID().toString()+".amr";
    }

    public int getVoiceLevel(int maxLevel){
        if(isPrepared){
            try {
                return maxLevel*mediaRecorder.getMaxAmplitude()/32768+1;//(1~32767)
            }catch (Exception e){

            }

        }

        return 1;
    }
    public void release(){
        if(mediaRecorder!=null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder=null;
        }

    }
    public void cancel(){
        release();
        if(mCurrentFilePath!=null){
            File file=new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath=null;
        }
    }

    public String getCurrentFilePath(){
        return mCurrentFilePath;
    }
}
