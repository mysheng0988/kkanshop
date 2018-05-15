package com.mysheng.office.kkanshop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.DataModel;

/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeTwoViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private TextView mTextView;
    private TextView mConnent;
    public TypeTwoViewHolder(View itemView) {
        super(itemView);
        mImageView=itemView.findViewById(R.id.avatar);
        mTextView=itemView.findViewById(R.id.name);
        mConnent=itemView.findViewById(R.id.content);
    }
    @Override
    public void bindHolder(DataModel model){

        mImageView.setBackgroundResource(model.avatarColor);
        mTextView.setText(model.name);
        mConnent.setText(model.content);
    }
}
