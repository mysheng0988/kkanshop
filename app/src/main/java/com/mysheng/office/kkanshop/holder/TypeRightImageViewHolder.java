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

public class TypeRightImageViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private ImageView mContentImage;
    ViewGroup.LayoutParams para;
    public TypeRightImageViewHolder(View itemView) {
        super(itemView);
        mContentImage=itemView.findViewById(R.id.id_content_img);
        mImageView=itemView.findViewById(R.id.id_useIcon);
    }
    @Override
    public void bindHolder(Object model,boolean isScrolling){
        if(model instanceof ChatModel){
            ChatModel chatModel= (ChatModel) model;
//            Log.e("mys", "H: "+chatModel.getHeight());
//            Log.e("mys", "W: "+chatModel.getWidth());
            RxTool.init(mContentImage.getContext());
            para = mContentImage.getLayoutParams();
            if(chatModel.getContentPath()==null){
                para.height = RxImageTool.dp2px(ChatTool.VIEW_HEIGHT);
                para.width = RxImageTool.dp2px(ChatTool.VIEW_WIDTH);
                mContentImage.setLayoutParams(para);
                mContentImage.setImageResource(R.drawable.bg);
            }else {
//                if ( !isScrolling) {
                    // 这里可以用Glide等网络图片加载库
                    String imagePath=chatModel.getContentPath();
                    if(!ChatTool.isNetUri(imagePath)){
                        imagePath="file://"+imagePath;
                    }
                    Glide.with(mContentImage.getContext())
                            .load(imagePath)
                            .asBitmap()//强制Glide返回一个Bitmap对象
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                    int width = bitmap.getWidth();
                                    int height = bitmap.getHeight();
                                    if (width<height){
                                        para.height = RxImageTool.dp2px(ChatTool.VIEW_HEIGHT);
                                        para.width = RxImageTool.dp2px(ChatTool.VIEW_WIDTH);
                                        mContentImage.setLayoutParams(para);

                                    }else{
                                        para.height = RxImageTool.dp2px(ChatTool.VIEW_WIDTH);
                                        para.width = RxImageTool.dp2px(ChatTool.VIEW_HEIGHT);
                                        mContentImage.setLayoutParams(para);
                                    }
                                    mContentImage.setImageBitmap(bitmap);
                                }
                            });
                //}

               // Glide.with(mContentImage.getContext()).load().into(mContentImage);


            }

            mImageView.setImageResource(R.drawable.ynn);//图片应该加载当前用户的头像地址
        }
    }
}
