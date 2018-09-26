package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.mysheng.office.kkanshop.adapter.NavViewAdapter;
import com.mysheng.office.kkanshop.banner.Banner;
import com.mysheng.office.kkanshop.banner.BannerConfig;
import com.mysheng.office.kkanshop.banner.GlideImageLoader;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.view.CircleIndicator;
import com.mysheng.office.kkanshop.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/9/26.
 */

public class SupermarketActivity extends Activity {
    private Banner banner;
    private ViewPager navViewPage;
    private ViewPager contentViewPage;
    private CircleIndicator circleIndicator;
    private ViewPagerIndicator indicator;
    private NavViewAdapter navViewAdapter;
    private LayoutInflater inflater;
    private List<View> viewList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_layout);
        banner=findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(IndexTools.market);
       // banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.start();
        navViewPage=findViewById(R.id.nva_viewpager);
        contentViewPage =findViewById(R.id.viewpager);
        indicator =findViewById(R.id.id_indicator);
        circleIndicator =findViewById(R.id.navIndicator);
        inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.nav_layout, null);
        View view2 = inflater.inflate(R.layout.nav2_layout, null);
        View view3 = inflater.inflate(R.layout.nav3_layout, null);
        View view4 = inflater.inflate(R.layout.nav4_layout, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        navViewAdapter = new NavViewAdapter(viewList);
        navViewPage.setAdapter(navViewAdapter);
        circleIndicator.setUpWithViewPager(navViewPage);
        circleIndicator.setEnableClickSwitch(true);
        circleIndicator.setOnIndicatorClickListener(new CircleIndicator.OnIndicatorClickListener() {
            @Override
            public void onSelected(int position) {
                navViewPage.setCurrentItem(position);
            }
        });

    }
}
