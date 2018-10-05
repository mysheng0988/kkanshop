package com.mysheng.office.kkanshop;

import android.graphics.Point;
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

public class SupermarketActivity extends FragmentActivity  implements View.OnClickListener,ChangeGoodsNum {

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
    private ImageView comeBack;
    private ImageView shopCart;
    private TextView goodsNum;
    private TextView navTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_layout);
        String mTitle=getIntent().getStringExtra("mTitle");
        comeBack=findViewById(R.id.comeBack);
        navTitle=findViewById(R.id.navTitle);
        navTitle.setText(mTitle);
        shopCart=findViewById(R.id.shopCart);
        goodsNum=findViewById(R.id.goods_num);
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
        initEvent();
    }

    private void initEvent() {
        comeBack.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comeBack:
                finish();
                break;
        }
    }
    /**
     * 添加商品动画
     * @param view
     */
    public void addAction(View view) {
        ShoppingCartAnimationView shoppingCartAnimationView = new ShoppingCartAnimationView(this);
        int position[] = new int[2];
        view.getLocationInWindow(position);
        int width=view.getWidth()/2;
        shoppingCartAnimationView.setStartPosition(new Point(position[0]+width, position[1]));
        ViewGroup rootView = (ViewGroup) getWindow().getDecorView();
        rootView.addView(shoppingCartAnimationView);
        int endPosition[] = new int[2];
        goodsNum.getLocationInWindow(endPosition);
        shoppingCartAnimationView.setEndPosition(new Point(endPosition[0], endPosition[1]));
        shoppingCartAnimationView.startBeizerAnimation(goodsNum);
//


    }
    @Override
    public void changeNun(View view) {
        addAction(view);
    }
}
