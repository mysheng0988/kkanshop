package com.mysheng.office.kkanshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.CommentFragment;
import com.mysheng.office.kkanshop.ContentDetailFragment;
import com.mysheng.office.kkanshop.GoodsDetailFragment;
import com.mysheng.office.kkanshop.ResembleFragment;

/**
 * Created by myaheng on 2018/9/13.
 */

public class MinePagerAdapter extends FragmentPagerAdapter {
    //Fragment[] fragments = new Fragment[]{GoodsDetailFragment.newInstance(), ContentDetailFragment.newInstance(), CommentFragment.newInstance(), ResembleFragment.newInstance()};
    Fragment[] fragments = new Fragment[]{new GoodsDetailFragment(), new ContentDetailFragment(),new  CommentFragment(), new ResembleFragment()};
    String[] titles = new String[]{"商品", "详情", "评价","推荐"};

    public MinePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }


}
