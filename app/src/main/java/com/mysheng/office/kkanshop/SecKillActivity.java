package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.mysheng.office.kkanshop.adapter.ChatAdapter;
import com.mysheng.office.kkanshop.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by myaheng on 2018/9/28.
 */

public class SecKillActivity extends FragmentActivity implements View.OnClickListener {
    private ImageView comeBack;
    private ViewPager secKillPager;
    private ViewPagerIndicator secKillIndicator;
    private List<String> mTitle=new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private List<Fragment> listFragment=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seckill_layout);
        comeBack=findViewById(R.id.comeBack);
        secKillPager=findViewById(R.id.viewpager);
        secKillIndicator=findViewById(R.id.id_indicator);
        initEvent();
        initData();
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        String title="";
        for(int i=0;i<5;i++){
          title=hour>9?hour+":00场":"0"+hour+":00场";
            NavItemFragment fragment=NavItemFragment.getInstance(i);
            listFragment.add(fragment);
          hour=hour+2;
          mTitle.add(title);
        }
        secKillIndicator.setTabItemTitle(mTitle);
        secKillIndicator.setViewPager(secKillPager,0);
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
        secKillPager.setAdapter(adapter);
    }


    private void initEvent() {
        comeBack.setOnClickListener(this);
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
