package com.mysheng.office.kkanshop.holder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ChatModel;


/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeRightAudioViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
   // private ImageView mContentImage;
    private TextView mTextView;
    public TypeRightAudioViewHolder(View itemView) {
        super(itemView);
       // mContentImage = itemView.findViewById(R.id.id_content_img);
        mImageView = itemView.findViewById(R.id.id_useIcon);
        mTextView=itemView.findViewById(R.id.id_recorder_time);
    }
    @Override
    public void bindHolder(ChatMsg model){

            //mContentImage.setImageResource(R.drawable.ynn);
            Log.d("mys", "bindHolder: "+ model.getMsg().getMsgLongTime());
            mTextView.setText( model.getMsg().getMsgLongTime()+"\"");
            mImageView.setImageResource(R.drawable.ynn);//图片应该加载当前用户的头像地址
    }
}
