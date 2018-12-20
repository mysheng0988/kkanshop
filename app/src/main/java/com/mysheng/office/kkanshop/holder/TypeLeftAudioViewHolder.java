package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ChatModel;


/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeLeftAudioViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private View mImage;
    private TextView mTextView;

    public TypeLeftAudioViewHolder(View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.id_anim);
        mImageView = itemView.findViewById(R.id.id_useIcon);
        mTextView=itemView.findViewById(R.id.id_recorder_time);
    }
    @Override
    public void bindHolder(ChatMsg model){
            mTextView.setText(model.getMsg().getMsgLongTime()+"\"");
            //mImageView.setImageResource(R.drawable.icon);//图片应该加载当前用户的头像地址
    }
}
