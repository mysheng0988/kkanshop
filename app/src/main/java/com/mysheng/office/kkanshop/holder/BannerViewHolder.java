package com.mysheng.office.kkanshop.holder;

import android.view.View;


import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.banner.Banner;

import com.mysheng.office.kkanshop.banner.BannerConfig;
import com.mysheng.office.kkanshop.banner.GlideImageLoader;
import com.mysheng.office.kkanshop.entity.BannerModel;


/**
 * Created by myaheng on 2018/9/10.
 */

public class BannerViewHolder extends IndexAbstractViewHolder<BannerModel>{
    public Banner banner;
    public BannerViewHolder(View itemView) {
        super(itemView);
        banner=itemView.findViewById(R.id.id_banner);

    }

    @Override
    public void bindHolder(BannerModel bannerModel) {
        banner.setImageLoader(new GlideImageLoader());
        banner.setDelayTime(4000);
        banner.setImages(bannerModel.getImgPaths());
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.setBannerTitles(bannerModel.getTitles());
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }
}
