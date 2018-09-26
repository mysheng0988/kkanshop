package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.mysheng.office.kkanshop.banner.Banner;
import com.mysheng.office.kkanshop.banner.BannerConfig;
import com.mysheng.office.kkanshop.banner.GlideImageLoader;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.view.CircleIndicator;
import com.mysheng.office.kkanshop.view.ViewPagerIndicator;

/**
 * Created by myaheng on 2018/9/26.
 */

public class SupermarketActivity extends Activity {
    private Banner banner;
    private ViewPager navViewPage;
    private ViewPager contentViewPage;
    private CircleIndicator circleIndicator;
    private ViewPagerIndicator indicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_layout);
        banner=findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(IndexTools.nearby);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.start();
        navViewPage=findViewById(R.id.nva_viewpager);
        contentViewPage =findViewById(R.id.viewpager);
        indicator =findViewById(R.id.id_indicator);
        circleIndicator =findViewById(R.id.circleIndicator);
    }
}
