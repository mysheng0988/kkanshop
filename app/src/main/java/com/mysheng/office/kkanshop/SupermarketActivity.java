package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.mysheng.office.kkanshop.adapter.NavViewAdapter;
import com.mysheng.office.kkanshop.banner.Banner;
import com.mysheng.office.kkanshop.banner.GlideImageLoader;
import com.mysheng.office.kkanshop.entity.IndexTools;

import com.mysheng.office.kkanshop.view.CircleViewIndicator;
import com.mysheng.office.kkanshop.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by myaheng on 2018/9/26.
 */

public class SupermarketActivity extends FragmentActivity {
    private Banner banner;
    private ViewPager navViewPage;
    private ViewPager contentViewPage;
    private CircleViewIndicator circleIndicator;
    private ViewPagerIndicator indicator;
    private NavViewAdapter navViewAdapter;
    private LayoutInflater inflater;
    private List<View> viewList = new ArrayList<>();
    private List<String> mTitle= Arrays.asList("精选","休闲零食","生鲜水果","全部1","全部2","全部3","全部4");
    private List<Fragment> listFragment=new ArrayList<>();
    private FragmentPagerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_layout);
        banner=findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(IndexTools.market);
        banner.setDelayTime(3000);
       // banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.start();
        navViewPage=findViewById(R.id.nva_viewpager);
        contentViewPage =findViewById(R.id.viewpager);
        indicator =findViewById(R.id.id_indicator);
        circleIndicator =findViewById(R.id.navIndicator);
        inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.market_nav1_layout, null);
        View view2 = inflater.inflate(R.layout.market_nav2_layout, null);
        viewList.add(view1);
        viewList.add(view2);
        navViewAdapter = new NavViewAdapter(viewList);
        navViewPage.setAdapter(navViewAdapter);
        circleIndicator.setUpWithViewPager(navViewPage);
        circleIndicator.setEnableClickSwitch(true);
        circleIndicator.setOnIndicatorClickListener(new CircleViewIndicator.OnIndicatorClickListener() {
            @Override
            public void onSelected(int position) {
                navViewPage.setCurrentItem(position);
            }
        });
        initData();
    }

    private void initData() {
        for (int i=0;i<mTitle.size();i++){

            NavItemFragment fragment=NavItemFragment.getInstance(i);
            listFragment.add(fragment);
        }
        //mIndicator.setTabVisibleCount(3);
        indicator.setTabItemTitle(mTitle);
        indicator.setViewPager(contentViewPage,0);
        adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return listFragment.get(position);
            }

            @Override
            public int getCount() {
                return listFragment.size();
            }
        };
        contentViewPage.setAdapter(adapter);

    }

}
