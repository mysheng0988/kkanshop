package com.mysheng.office.kkanshop;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.util.AppBarStateChangeListener;
import com.mysheng.office.kkanshop.util.SharedPreferencesUtils;
import com.mysheng.office.kkanshop.util.UtilToast;
import com.mysheng.office.kkanshop.view.ViewPagerIndicator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonFragment extends Fragment implements View.OnClickListener {
	private ViewPagerIndicator mIndicator;
	private SharedPreferencesUtils shareData;
	private ViewPager mViewPager;
	private List<String> mTitle= Arrays.asList("精选","副食","餐饮","超市","全部1","全部2","全部3","全部4");
	private List<ResembleFragment> listFragment=new ArrayList<>();
	private FragmentPagerAdapter adapter;
	private Toolbar toolbar;
	private AppBarLayout appBarLayout;
	/**
	 * 是否已登录
	 */
	private boolean isLogined=false;
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
	/**
	 * 登录信息
	 */
	private LinearLayout login;
	private LinearLayout textContent;
	private TextView userName;
	private TextView phone;
	private ImageView setting;
	private ImageView msg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.tab05, container, false);
		shareData=new SharedPreferencesUtils(getActivity());
		initView(view);
		initEvent();
		initData();
		return view;
	}

	private void initView(View view){

		login=view.findViewById(R.id.login);
		textContent=view.findViewById(R.id.textContent);
		userName=view.findViewById(R.id.userName);
		phone=view.findViewById(R.id.phone);

		setting=view.findViewById(R.id.setting);
		msg=view.findViewById(R.id.msg);


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

		login.setOnClickListener(this);

		setting.setOnClickListener(this);
		msg.setOnClickListener(this);

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
			case R.id.setting:
				intent=new Intent(getActivity(),SettingActivity.class);
				startActivity(intent);
				break;
			case R.id.msg:
				intent=new Intent(getActivity(),ChatListViewActivity.class);
				startActivity(intent);
				break;
			case R.id.login:
				intent=new Intent(getActivity(),LoginActivity.class);
				startActivity(intent);
				break;
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

	@Override
	public void onResume() {
		super.onResume();
		String userNameStr= (String) shareData.getParam("userName","");
		String phoneStr= (String) shareData.getParam("phone","");
		if(TextUtils.isEmpty(userNameStr)&&TextUtils.isEmpty(phoneStr)){
			textContent.setVisibility(View.GONE);
			login.setVisibility(View.VISIBLE);
		}else {
			login.setVisibility(View.GONE);
			textContent.setVisibility(View.VISIBLE);
			userName.setText(userNameStr);
			phone.setText(phoneStr);
		}
	}


}
