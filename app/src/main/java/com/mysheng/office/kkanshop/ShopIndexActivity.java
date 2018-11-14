package com.mysheng.office.kkanshop;

import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.adapter.NavViewAdapter;
import com.mysheng.office.kkanshop.banner.Banner;
import com.mysheng.office.kkanshop.banner.GlideImageLoader;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.listenter.ChangeGoodsNum;
import com.mysheng.office.kkanshop.view.CircleViewIndicator;
import com.mysheng.office.kkanshop.view.ShoppingCartAnimationView;
import com.mysheng.office.kkanshop.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by myaheng on 2018/9/26.
 */

public class ShopIndexActivity extends FragmentActivity  implements View.OnClickListener {

    private Banner banner;
    private ViewPager contentViewPage;
    private ViewPagerIndicator indicator;
    private List<String> mTitle= Arrays.asList("首页","商品","活动","上新","动态");
    private List<Fragment> listFragment=Arrays.asList(ShopIndexFragment.getInstance(0),ReListFragment.getInstance(1),ReListFragment.getInstance(2),ReListFragment.getInstance(3),ReListFragment.getInstance(4));
    private FragmentPagerAdapter adapter;
    private ImageView comeBack;
    private TextView goodsNum;
    private TextView shopPhone;
    private TextView shopName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_index_layout);
        comeBack=findViewById(R.id.comeBack);
        banner=findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(IndexTools.market);
        banner.setDelayTime(3000);
       // banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.start();

        contentViewPage =findViewById(R.id.viewpager);
        indicator =findViewById(R.id.id_indicator);
        String fontPath="font/cartoon.ttf";
        Typeface typeface=Typeface.createFromAsset(getAssets(),fontPath);
        String fontPath2="font/1001.ttf";
        Typeface typeface2=Typeface.createFromAsset(getAssets(),fontPath2);
        shopName =findViewById(R.id.shopName);
        shopPhone =findViewById(R.id.shopPhone);
        shopPhone.setTypeface(typeface2);
        shopName.setTypeface(typeface);

        initData();
        initEvent();
    }

    private void initEvent() {
        comeBack.setOnClickListener(this);
    }

    private void initData() {
//        for (int i=0;i<mTitle.size();i++){
//
//            ReListFragment fragment= ReListFragment.getInstance(i);
//            listFragment.add(fragment);
//        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comeBack:
                finish();
                break;
        }
    }


}
