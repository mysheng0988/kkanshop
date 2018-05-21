package com.mysheng.office.kkanshop.util;

import android.content.Context;

import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by myaheng on 2018/5/16.
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load((String) path).into(imageView);

    }
}
