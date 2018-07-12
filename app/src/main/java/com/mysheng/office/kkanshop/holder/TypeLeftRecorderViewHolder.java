package com.mysheng.office.kkanshop.holder;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ChatModel;

/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeLeftRecorderViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private View mImage;
    private TextView mTextView;

    public TypeLeftRecorderViewHolder(View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.id_anim);
        mImageView = itemView.findViewById(R.id.id_useIcon);
        mTextView=itemView.findViewById(R.id.id_recorder_time);
    }
    @Override
    public void bindHolder(Object model){
        if(model instanceof ChatModel){
            ChatModel chatModel= (ChatModel) model;
           // mImage.setBackgroundResource(R.drawable.adj_l);
            mTextView.setText(chatModel.mesTime+"\"");
            mImageView.setImageResource(R.drawable.icon);//图片应该加载当前用户的头像地址
        }
    }
}
