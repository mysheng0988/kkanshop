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
import java.util.Calendar;
import java.util.List;

/**
 * Created by myaheng on 2018/9/28.
 */

public class SecKillActivity extends FragmentActivity implements View.OnClickListener {
    private ImageView comeBack;
    private ImageView screen;
    private ViewPager secKillPager;
    private ViewPagerIndicator secKillIndicator;
    private List<String> mTitle=new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private List<Fragment> listFragment=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_viewpager_layout);
        comeBack=findViewById(R.id.comeBack);
        //screen=findViewById(R.id.screen);
        secKillPager=findViewById(R.id.viewpager);
        secKillIndicator=findViewById(R.id.id_indicator);
        initEvent();
        initData();
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        String title="";
        for(int i=0;i<4;i++){
          title=hour>9?hour+":00场":"0"+hour+":00场";
            SecKillItemFragment fragment=SecKillItemFragment.getInstance(i);
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
