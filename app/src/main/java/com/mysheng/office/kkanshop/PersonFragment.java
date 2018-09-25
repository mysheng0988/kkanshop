package com.mysheng.office.kkanshop;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.util.AppBarStateChangeListener;
import com.mysheng.office.kkanshop.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonFragment extends Fragment implements View.OnClickListener {
	private ViewPagerIndicator mIndicator;
	private ViewPager mViewPager;
	private List<String> mTitle= Arrays.asList("副食","餐饮","超市","全部1","全部2","全部3","全部4");
	private List<ResembleFragment> listFragment=new ArrayList<>();
	private FragmentPagerAdapter adapter;
	private Toolbar toolbar;
	private AppBarLayout appBarLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.tab05, container, false);
		initView(view);
		initData();
		return view;
	}

	private void initView(View view){

		mIndicator=view.findViewById(R.id.id_indicator);
		mViewPager=view.findViewById(R.id.viewpager);
		appBarLayout=view.findViewById(R.id.appBarLayout);
		toolbar=view.findViewById(R.id.toolbar);
		appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
			@Override
			public void onStateChanged(AppBarLayout appBarLayout, State state) {
				Log.d("STATE", state.name());
					if (state == State.EXPANDED) {
						toolbar.setVisibility(View.GONE);
						//展开状态

					} else if (state == State.COLLAPSED) {
						toolbar.setVisibility(View.VISIBLE);
						//折叠状态

					} else {
						toolbar.setVisibility(View.GONE);
						//中间状态

					}
				}
		});
	}
	private void initEvent(){

	}
	@Override
	public void onClick(View v) {

	}
	private void initData() {
		for (String title:mTitle){
			ResembleFragment fragment=new ResembleFragment();
			listFragment.add(fragment);
		}
		//mIndicator.setTabVisibleCount(3);
		mIndicator.setTabItemTitle(mTitle);
		mIndicator.setViewPager(mViewPager,0);
		adapter=new FragmentPagerAdapter(getFragmentManager()) {
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
	private void startSettingActivity() {
		Intent intent = new Intent(getActivity(), SettingActivity.class);
		startActivity(intent);
	}



}
