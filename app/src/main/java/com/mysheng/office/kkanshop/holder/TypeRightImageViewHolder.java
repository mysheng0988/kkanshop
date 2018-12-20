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
            .asBitmap()//强制Glide返回一个Bitmap对象
            .into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    if (width<height){
                        para.height = RxImageTool.dp2px(ChatTools.VIEW_HEIGHT);
                        para.width = RxImageTool.dp2px(ChatTools.VIEW_WIDTH);
                        mContentImage.setLayoutParams(para);

                    }else{
                        para.height = RxImageTool.dp2px(ChatTools.VIEW_WIDTH);
                        para.width = RxImageTool.dp2px(ChatTools.VIEW_HEIGHT);
                        mContentImage.setLayoutParams(para);
                    }
                    mContentImage.setImageBitmap(bitmap);
                }
            });

        //mImageView.setImageResource(R.drawable.ynn);//图片应该加载当前用户的头像地址
    }

}
