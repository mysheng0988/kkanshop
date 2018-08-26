package com.mysheng.office.kkanshop.holder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ChatModel;


/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeRightLocationViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mAddress;
    private ImageView locationMap;

    public TypeRightLocationViewHolder(View itemView) {
        super(itemView);
        mTitle=itemView.findViewById(R.id.id_location_title);
        mImageView=itemView.findViewById(R.id.id_useIcon);
        mAddress=itemView.findViewById(R.id.id_location_address);
        locationMap=itemView.findViewById(R.id.locationMap);
    }

    @Override
    public void bindHolder(Object model,boolean isScrolling){
        if(model instanceof ChatModel) {
            ChatModel chatModel = (ChatModel) model;
            mTitle.setText(chatModel.getTabTitle());
            mImageView.setImageResource(R.drawable.ynn);
            mAddress.setText(chatModel.getAddress());
            Glide.with(mAddress.getContext()).load("file://"+((ChatModel) model).getLocationPath()).into(locationMap);
           // locationMap.setImageBitmap(chatModel.getLocationBitmap());
        }
    }

}
