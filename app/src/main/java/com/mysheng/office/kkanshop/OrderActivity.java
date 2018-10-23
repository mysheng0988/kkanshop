package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.mysheng.office.kkanshop.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by myaheng on 2018/10/23.
 */

public class OrderActivity  extends FragmentActivity implements View.OnClickListener{
    private ImageView comeBack;
    private ViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private List<String> mTitle= Arrays.asList("全部","待支付","待收货","已完成","已取消");
    private List<VpNearbyFragment> listFragment=new ArrayList<>();
    private FragmentPagerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        initView();
        initEvent();
    }

    /**
     * 初始化View
     */
    protected void initView() {
        comeBack=findViewById(R.id.comeBack);
        mIndicator=findViewById(R.id.id_indicator);
        mViewPager=findViewById(R.id.viewPager);
        for (String title:mTitle){

            VpNearbyFragment fragment=new VpNearbyFragment();
            listFragment.add(fragment);
        }
        mIndicator.setTabItemTitle(mTitle);
        mIndicator.setViewPager(mViewPager,0);
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
        mViewPager.setAdapter(adapter);
    }

    /**
     * 初始化event
     */
    protected void initEvent() {
        comeBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.comeBack:
                finish();
                break;
        }
    }
}
