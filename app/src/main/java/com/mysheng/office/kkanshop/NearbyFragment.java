package com.mysheng.office.kkanshop;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.mysheng.office.kkanshop.location.MapUtils;
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
	private TextView mAddress;
	private double latitude;
	private double longitude;
	private String cityCode;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view= inflater.inflate(R.layout.tab03, container, false);

		initView(view);
		initData();
		MapUtils mapUtils = new MapUtils();
		mapUtils.getLonLat(getActivity(), new MapUtils.LonLatListener() {
			@Override
			public void getLonLat(AMapLocation aMapLocation) {
				if (aMapLocation != null) {
					if (aMapLocation.getErrorCode() == 0) {
						//定位成功回调信息，设置相关消息
						aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
						latitude = aMapLocation.getLatitude();//获取纬度
						longitude = aMapLocation.getLongitude();//获取经度
						cityCode = aMapLocation.getCityCode();
//						.setText("当前位置：" + amapLocation.getAddress());
//						tv_city.setText("当前城市：" + amapLocation.getProvince() + "-" + amapLocation.getCity() + "-" + amapLocation.getDistrict() + "-" + amapLocation.getStreet() + "-" + amapLocation.getStreetNum());
//						tv_poi.setText("当前位置："+amapLocation.getAoiName());
						mAddress.setText(aMapLocation.getAddress());
						aMapLocation.getAccuracy();//获取精度信息
					} else {
						Log.e("AmapError", "location Error, ErrCode:"
								+ aMapLocation.getErrorCode() + ", errInfo:"
								+ aMapLocation.getErrorInfo());

						Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		return view;
	}

	private void initView(View view) {
		mIndicator=view.findViewById(R.id.id_indicator);
		mViewPager=view.findViewById(R.id.viewPager);
		mAddress=view.findViewById(R.id.address);

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
