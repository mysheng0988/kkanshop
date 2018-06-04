package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.DataModel;

/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeThreeViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private ImageView mImgContent;
    private TextView mTextView;
    private TextView mConnent;
    public TypeThreeViewHolder(View itemView) {
        super(itemView);
        mImageView=itemView.findViewById(R.id.avatar);
        mTextView=itemView.findViewById(R.id.name);
        mConnent=itemView.findViewById(R.id.content);
        mImgContent=itemView.findViewById(R.id.contentImage);

    }
    @Override
    public void bindHolder(DataModel model){

        mImageView.setBackgroundResource(model.avatarColor);
        mImgContent.setBackgroundResource(model.contentColor);
        mTextView.setText(model.name);
        mConnent.setText(model.content);
    }
}
