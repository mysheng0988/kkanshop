package com.mysheng.office.kkanshop.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;

/**
 * Created by myaheng on 2017/12/15.
 */

public class DialogManager {
    private Dialog dialog;
    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLabel;
    private Context mContext;

    public DialogManager(Context context) {
        mContext=context;
    }
    public void showRecordingDialog(){
        dialog=new Dialog(mContext, R.style.Theme_AudioDialog);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.dialog_recorder,null);
        dialog.setContentView(view);
        mIcon=dialog.findViewById(R.id.id_recorder_dialog_icon);
        mVoice=dialog.findViewById(R.id.id_recorder_dialog_voice);
        mLabel=dialog.findViewById(R.id.id_recorder_dialog_label);
        dialog.show();

    }
    public void recording(){
        if(dialog!=null&&dialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.recorder);
            mLabel.setText("松开上划，取消发送");
        }
    }
    public void wantToCancel(){
        if(dialog!=null&&dialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.cancel);
            mLabel.setText("松开手指，取消发送");
        }
    }
    public void tooShort(){
        if(dialog!=null&&dialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);
            mIcon.setImageResource(R.drawable.voice_to_short);
            mLabel.setText("录音时间过短");
        }
    }
    public void dismissDialog(){
        if(dialog!=null&&dialog.isShowing()){
          dialog.dismiss();
          dialog=null;
        }
    }
    public void updateVoiceLevel(int level){
        if(dialog!=null&&dialog.isShowing()){
//            mIcon.setVisibility(View.VISIBLE);
//            mVoice.setVisibility(View.VISIBLE);
//            mLable.setVisibility(View.VISIBLE);
            int resId=mContext.getResources().getIdentifier("v"+level,"drawable",mContext.getPackageName());
            mVoice.setImageResource(resId);
        }
    }

}
