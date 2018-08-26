package com.mysheng.office.kkanshop.holder;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.RxTool.RxImageTool;
import com.mysheng.office.kkanshop.RxTool.RxTool;
import com.mysheng.office.kkanshop.entity.ChatModel;
import com.mysheng.office.kkanshop.util.ChatTool;


/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeRightVideoViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private ImageView mContentVideo;
    ViewGroup.LayoutParams para;
    public TypeRightVideoViewHolder(View itemView) {
        super(itemView);
        mContentVideo=itemView.findViewById(R.id.id_content_video);
        mImageView=itemView.findViewById(R.id.id_useIcon);
    }
    @Override
    public void bindHolder(Object model,boolean isScrolling){
        if(model instanceof ChatModel){
            ChatModel chatModel= (ChatModel) model;
            RxTool.init(mContentVideo.getContext());
            para = mContentVideo.getLayoutParams();
            Glide.with(mContentVideo.getContext())
                    .load("file://"+chatModel.getContentPath())
                    .asBitmap()//强制Glide返回一个Bitmap对象
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            if (width<height){
                                para.height = RxImageTool.dp2px(ChatTool.VIEW_HEIGHT);
                                para.width = RxImageTool.dp2px(ChatTool.VIEW_WIDTH);
                                mContentVideo.setLayoutParams(para);

                            }else{
                                para.height = RxImageTool.dp2px(ChatTool.VIEW_WIDTH);
                                para.width = RxImageTool.dp2px(ChatTool.VIEW_HEIGHT);
                                mContentVideo.setLayoutParams(para);
                            }
                            mContentVideo.setImageBitmap(bitmap);
                        }
                    });

            mImageView.setImageResource(R.drawable.ynn);//图片应该加载当前用户的头像地址
        }
    }
}
