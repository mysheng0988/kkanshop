package com.mysheng.office.kkanshop;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.mysheng.office.kkanshop.util.CommonUtil;
import com.mysheng.office.kkanshop.view.ViewPagerIndicator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NearbyFragment extends Fragment implements View.OnClickListener{
	private ViewPagerIndicator mIndicator;
	private ViewPager mViewPager;
	private List<String> mTitle= Arrays.asList("副食","餐饮","超市","全部1","全部2","全部3","全部4");
	private List<VpNearbyFragment> listFragment=new ArrayList<>();
	private FragmentPagerAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view= inflater.inflate(R.layout.tab03, container, false);

		initView(view);
		initData();

		return view;
	}

	private void initView(View view) {
		mIndicator=view.findViewById(R.id.id_indicator);
		mViewPager=view.findViewById(R.id.viewPager);

	}
	private void initData() {
		for (String title:mTitle){
			VpNearbyFragment fragment=VpNearbyFragment.getInstance(title);
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
	@Override
	public void onClick(View v) {

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if(requestCode==CommonUtil.SCAN_CODE){
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED)
			{
				//startScanCode();
			} else {
				// Permission Denied
				Toast.makeText(getActivity(), "您已拒绝，请打开手机应用权限设置", Toast.LENGTH_SHORT).show();
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}



}
