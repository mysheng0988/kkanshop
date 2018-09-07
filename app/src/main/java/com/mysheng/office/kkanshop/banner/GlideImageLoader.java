package com.mysheng.office.kkanshop.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.banner.loader.ImageLoader;


/**
 * Created by myaheng on 2018/5/16.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String imagePath= (String) path;
        Glide.with(context).load(imagePath).into(imageView);

    }
}
