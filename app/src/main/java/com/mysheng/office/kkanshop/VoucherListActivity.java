package com.mysheng.office.kkanshop;

import android.graphics.Typeface;
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
import java.util.List;

/**
 * Created by myaheng on 2018/10/23.
 */

public class VoucherListActivity extends FragmentActivity implements View.OnClickListener{
    private ImageView comeBack;
    private ViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private List<String> mTitle= Arrays.asList("未使用","已使用","已过期");
    private List<VoucherFragment> listFragment=Arrays.asList(VoucherFragment.newInstance(0),VoucherFragment.newInstance(3),VoucherFragment.newInstance(2));
    private FragmentPagerAdapter adapter;
    private TextView voucherText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_list_layout);
        initView();
        initEvent();
    }

    /**
     * 初始化View
     */
    protected void initView() {
        comeBack=findViewById(R.id.comeBack);
        voucherText=findViewById(R.id.voucherText);
        Typeface typeface= Typeface.createFromAsset(getAssets(),"font/chinese.ttf");
        voucherText.setTypeface(typeface);
        mIndicator=findViewById(R.id.id_indicator);
        mViewPager=findViewById(R.id.viewPager);
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
