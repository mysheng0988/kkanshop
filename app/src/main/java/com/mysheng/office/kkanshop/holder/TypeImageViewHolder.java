package com.mysheng.office.kkanshop.holder;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.RxTool.RxImageTool;
import com.mysheng.office.kkanshop.RxTool.RxTool;
import com.mysheng.office.kkanshop.entity.ChatTools;


/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeImageViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private ImageView mContentImage;
    ViewGroup.LayoutParams para;
    public TypeImageViewHolder(View itemView) {
        super(itemView);
        mContentImage=itemView.findViewById(R.id.id_content_img);
        mImageView=itemView.findViewById(R.id.id_useIcon);
        para = mContentImage.getLayoutParams();
    }
    @Override
    public void bindHolder(ChatMsg model){
        RxTool.init(mContentImage.getContext());
        para = mContentImage.getLayoutParams();
        // 这里可以用Glide等网络图片加载库
        String content=new String(model.getMsg().getContent());
        String imagePath=content;
        if(!ChatTools.isNetUri(imagePath)){
            imagePath="file://"+imagePath;
        }
        Glide.with(mContentImage.getContext())
                .load(imagePath)
                .thumbnail(0.1f)
                .into(mContentImage);
    }

        //mImageView.setImageResource(R.drawable.ynn);//图片应该加载当前用户的头像地址

}
