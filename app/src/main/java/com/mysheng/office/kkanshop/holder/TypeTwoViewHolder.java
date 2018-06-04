package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.DataModel;

/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeTwoViewHolder extends TypeAbstractViewHolder{
    public ImageView mImageView;
    public TextView mTextView;
    public TextView mConnent;
    public TypeTwoViewHolder(View itemView) {

        super(itemView);
        mImageView=itemView.findViewById(R.id.avatar);
        mTextView=itemView.findViewById(R.id.name);
        mConnent=itemView.findViewById(R.id.content);
    }
    @Override
    public void bindHolder(Object model){
        if(model instanceof DataModel){
            DataModel dataModel= (DataModel) model;
            mImageView.setBackgroundResource(dataModel.avatarColor);
            mTextView.setText(dataModel.name);
            mConnent.setText(dataModel.content);
        }

    }

}
