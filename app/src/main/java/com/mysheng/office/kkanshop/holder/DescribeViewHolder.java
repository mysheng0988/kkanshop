package com.mysheng.office.kkanshop.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.KkanApplication;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.DescribeModel;
import com.mysheng.office.kkanshop.util.Tools;
import com.mysheng.office.kkanshop.view.ResizableImageView;


/**
 * Created by myaheng on 2018/7/17.
 */

public class DescribeViewHolder extends  RecyclerView.ViewHolder{

    public ResizableImageView imagePath;



    public DescribeViewHolder(View itemView) {
        super(itemView);
        imagePath = itemView.findViewById(R.id.describe_img);


    }
    public void bindHolder(DescribeModel model){

            String imageUrl=model.getImagePath();
            if(!Tools.isNetUri(imageUrl)){
                imageUrl="file://"+imagePath;
            }
            Glide.with(KkanApplication.mContext).load(imageUrl).into(imagePath);


    }


}
