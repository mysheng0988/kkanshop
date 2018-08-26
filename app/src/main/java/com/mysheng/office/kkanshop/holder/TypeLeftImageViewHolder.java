package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.RxTool.RxImageTool;
import com.mysheng.office.kkanshop.RxTool.RxTool;
import com.mysheng.office.kkanshop.entity.ChatModel;
import com.mysheng.office.kkanshop.util.ChatTool;


/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeLeftImageViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private ImageView mContentImage;
    ViewGroup.LayoutParams para;
    public TypeLeftImageViewHolder(View itemView) {
        super(itemView);
        mContentImage=itemView.findViewById(R.id.id_content_img);
        mImageView=itemView.findViewById(R.id.id_useIcon);
        para = mContentImage.getLayoutParams();
    }
    @Override
    public void bindHolder(Object model,boolean isScrolling){
        if(model instanceof ChatModel){
            ChatModel chatModel= (ChatModel) model;
            RxTool.init(mContentImage.getContext());
            para.height = RxImageTool.dp2px(ChatTool.VIEW_WIDTH);
            para.width = RxImageTool.dp2px(ChatTool.VIEW_HEIGHT);
            mContentImage.setLayoutParams(para);
            mContentImage.setImageResource(R.drawable.timg);
            mImageView.setImageResource(R.drawable.icon);//图片应该加载当前用户的头像地址
        }
    }
}
