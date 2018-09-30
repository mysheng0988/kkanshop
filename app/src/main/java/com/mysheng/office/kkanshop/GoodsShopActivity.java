package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by myaheng on 2018/9/30.
 */

public class GoodsShopActivity extends FragmentActivity implements View.OnClickListener {
    private ImageView comeBack;
    private ImageView screen;
    private TextView commonTitle;
    private ViewPager goodShopPager;
    private ViewPagerIndicator goodShopIndicator;
    private List<String> mTitle= Arrays.asList("精选","休闲食品","手机通讯","大家电","男装","女装","电脑整机","床上用品");
    private FragmentPagerAdapter adapter;
    private List<Fragment> listFragment=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_viewpager_layout);
        comeBack=findViewById(R.id.comeBack);
        //screen=findViewById(R.id.screen);
        commonTitle=findViewById(R.id.commonTitle);
        commonTitle.setText("好店推荐");
        goodShopPager=findViewById(R.id.viewpager);
        goodShopPager.setOffscreenPageLimit(3);
        goodShopIndicator=findViewById(R.id.id_indicator);
        initEvent();
        initData();
    }

    private void initData() {
        for(int i=0;i<mTitle.size();i++){

            GoodShopItemFragment fragment=GoodShopItemFragment.getInstance(i);
            listFragment.add(fragment);
        }
        goodShopIndicator.setTabItemTitle(mTitle);
        goodShopIndicator.setViewPager(goodShopPager,0);
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
        goodShopPager.setAdapter(adapter);
    }


    private void initEvent() {

        comeBack.setOnClickListener(this);
        //  screen.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comeBack:
                finish();
                break;
            case R.id.screen:
                screenItem();
                break;
        }

    }

    private void screenItem() {
    }
}
