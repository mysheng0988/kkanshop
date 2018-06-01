package com.mysheng.office.kkanshop.view;

/**
 * Created by mysheng on 2017/12/18.
 */

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;

import java.io.IOException;

public class MediaManager {
    private static MediaPlayer mediaPlayer;
    private static boolean isPause;
    public static void playSound(String filePath, OnCompletionListener listener, OnErrorListener errorListener){
        if(mediaPlayer==null){
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setOnErrorListener(new OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mediaPlayer.reset();
                    return false;
                }
            });

        }else{
            mediaPlayer.reset();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(listener);
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnErrorListener(errorListener);
    }
    public static void playMsgSound(Context context,Uri uri, OnCompletionListener listener){
        if(mediaPlayer==null){
            mediaPlayer=new MediaPlayer();
            mediaPlayer.setOnErrorListener(new OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mediaPlayer.reset();
                    return false;
                }
            });

        }else{
            mediaPlayer.reset();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(listener);
        try {
            mediaPlayer.setDataSource(context,uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  static void  pause(){
        if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            isPause=true;
        }
    }
    public  static  void  resume(){
        if(mediaPlayer!=null&&isPause){
            mediaPlayer.start();
            isPause=false;
        }
    }
    public static void release(){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
