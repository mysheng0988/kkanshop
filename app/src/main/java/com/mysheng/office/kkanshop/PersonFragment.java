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
import android.widget.RelativeLayout;

import com.mysheng.office.kkanshop.util.AppBarStateChangeListener;
import com.mysheng.office.kkanshop.util.UtilToast;
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
	/**
	 * 八个功能按钮
	 */
	private RelativeLayout waitPayment;
	private RelativeLayout waitReceived;
	private RelativeLayout waitEvaluate;
	private RelativeLayout myOrder;
	private RelativeLayout voucher;
	private RelativeLayout giftCart;
	private RelativeLayout myIntegral;
	private RelativeLayout myWallet;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.tab05, container, false);
		initView(view);
		initEvent();
		initData();
		return view;
	}

	private void initView(View view){

		mIndicator=view.findViewById(R.id.id_indicator);
		mViewPager=view.findViewById(R.id.viewpager);
		appBarLayout=view.findViewById(R.id.appBarLayout);
		toolbar=view.findViewById(R.id.toolbar);
		waitPayment=view.findViewById(R.id.waitPayment);
		waitReceived=view.findViewById(R.id.waitReceived);
		waitEvaluate=view.findViewById(R.id.waitEvaluate);
		myOrder=view.findViewById(R.id.myOrder);
		voucher=view.findViewById(R.id.voucher);
		giftCart=view.findViewById(R.id.giftCart);
		myIntegral=view.findViewById(R.id.myIntegral);
		myWallet=view.findViewById(R.id.myWallet);
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

		waitPayment.setOnClickListener(this);
		waitReceived.setOnClickListener(this);
		waitEvaluate.setOnClickListener(this);
		myOrder.setOnClickListener(this);
		voucher.setOnClickListener(this);
		giftCart.setOnClickListener(this);
		myIntegral.setOnClickListener(this);
		myWallet.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent=null;
		switch (v.getId()){
			case R.id.waitPayment:
				intent=new Intent(getActivity(),OrderActivity.class);
				intent.putExtra("indexNum",1);
				startActivity(intent);
				break;
			case R.id.waitReceived:
				intent=new Intent(getActivity(),OrderActivity.class);
				intent.putExtra("indexNum",2);
				startActivity(intent);
				break;
			case R.id.waitEvaluate:
//				intent=new Intent(getActivity(),OrderActivity.class);
//				intent.putExtra("indexNum",0);
//				startActivity(intent);
				UtilToast.showShort(getActivity(),"待评价");
				break;
			case R.id.myOrder:
				intent=new Intent(getActivity(),OrderActivity.class);
				intent.putExtra("indexNum",0);
				startActivity(intent);
				break;
			case R.id.giftCart:
				UtilToast.showShort(getActivity(),"礼品卡");
				break;
			case R.id.voucher:
				intent=new Intent(getActivity(),VoucherListActivity.class);
				startActivity(intent);
				break;
			case R.id.myIntegral:
				UtilToast.showShort(getActivity(),"我的积分");
				break;
			case R.id.myWallet:
				UtilToast.showShort(getActivity(),"我的钱包");
				break;
		}
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
