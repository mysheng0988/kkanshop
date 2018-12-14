package com.mysheng.office.kkanshop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.mysheng.office.kkanshop.permissions.RxPermissions;
import com.mysheng.office.kkanshop.util.CommonUtil;
import com.mysheng.office.kkanshop.util.SharedPreferencesUtils;
import com.mysheng.office.kkanshop.util.UtilToast;
import com.mysheng.office.kkanshop.util.VolleyJsonInterface;
import com.mysheng.office.kkanshop.util.VolleyRequest;
import com.mysheng.office.kkanshop.zxing.common.Constant;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class MainActivity extends FragmentActivity implements OnClickListener
{
	private LinearLayout mIndex;
	private LinearLayout mClassify;
	private LinearLayout mNearby;
	private LinearLayout mFlow;
	private LinearLayout mPerson;

	public static final String APP_ID ="2882303761517808316";
	public static final String APP_KEY ="5491780810316";
	public static final String TAG = "com.mysheng.office.kkanshop";

	private ImageButton mImgIndex;
	private ImageButton mImgNearby;
	private ImageButton mImgClassify;
	private ImageButton mImgShopping;
	private ImageButton mImgPerson;

	private Fragment mTab01;
	private Fragment mTab02;
	private Fragment mTab03;
	private Fragment mTab04;
	private Fragment mTab05;
	private FragmentManager manager;
	private TextView textView;
	private RxPermissions rxPermissions;
	private SharedPreferencesUtils shareData;
	private String userId;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{


		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		shareData=new SharedPreferencesUtils(this);
		userId=(String) shareData.getParam("phone","");
		rxPermissions=new RxPermissions(this);
		rxPermissions.request(Manifest.permission.READ_PHONE_STATE,
				android.Manifest.permission.ACCESS_FINE_LOCATION)
				.subscribe(new Consumer<Boolean>() {
					@Override
					public void accept(Boolean aBoolean) throws Exception {
						if (aBoolean) {
							if(shouldInit()) {
								MiPushClient.registerPush(getApplicationContext(), APP_ID, APP_KEY);
								String regId= MiPushClient.getRegId(MainActivity.this);
								Log.e("mainActivity", "onCreate: "+regId );
							}
						} else {
							UtilToast.showShort(MainActivity.this,"你已拒绝改权限");
						}
					}
				});

		//打开Log
		LoggerInterface newLogger = new LoggerInterface() {

			@Override
			public void setTag(String tag) {
				// ignore
			}

			@SuppressLint("LongLogTag")
			@Override
			public void log(String content, Throwable t) {
				Log.d(TAG, content, t);
			}
			@SuppressLint("LongLogTag")
			@Override
			public void log(String content) {
				Log.d(TAG, content);
			}
		};
		Logger.setLogger(this, newLogger);
		setSelect(0);
	}
	private boolean shouldInit() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = Process.myPid();
		for (ActivityManager.RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;

	}

	private void initEvent()
	{
		mIndex.setOnClickListener(this);
		mClassify.setOnClickListener(this);
		mNearby.setOnClickListener(this);
		mFlow.setOnClickListener(this);
		mPerson.setOnClickListener(this);

	}

	private void initView()
	{
		mIndex =  findViewById(R.id.id_index);
		mClassify =  findViewById(R.id.id_classify);
		mNearby =  findViewById(R.id.id_nearby);
		mFlow =  findViewById(R.id.id_shopping);
		mPerson =  findViewById(R.id.id_person);

		mImgIndex =  findViewById(R.id.id_index_img);
		mImgClassify =  findViewById(R.id.id_classify_img);
		mImgNearby =  findViewById(R.id.id_nearby_img);
		mImgShopping =  findViewById(R.id.id_shopping_img);
		mImgPerson =  findViewById(R.id.id_person_img);
		//textView=findViewById(R.id.tab_title);
	}

	private void setSelect(int i)
	{
		 manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		hideFragment(transaction);
		switch (i)
		{
		case 0:
			if (mTab01 == null)
			{
				mTab01 = new IndexFragment();
				transaction.add(R.id.id_content, mTab01,"mTab01");
			} else
			{
				transaction.show(mTab01);
			}
			mImgIndex.setImageResource(R.drawable.index_pressed);
			break;
		case 1:

			if (mTab02 == null)
			{
				mTab02 = new ClassifyFragment();
				transaction.add(R.id.id_content, mTab02,"mTab02");
			} else
			{
				transaction.show(mTab02);
				
			}
			mImgClassify.setImageResource(R.drawable.classify_pressed);
			break;
		case 2:
			if (mTab03 == null)
			{
				mTab03 = new NearbyFragment();
				transaction.add(R.id.id_content, mTab03,"mTab03");
			} else
			{
				transaction.show(mTab03);
			}
			mImgNearby.setImageResource(R.drawable.nearby_pressed);
			break;
		case 3:
			if (mTab04 == null)
			{
				mTab04 = new ShoppingCartFragment();
				transaction.add(R.id.id_content, mTab04,"mTab04");
			} else
			{
				transaction.show(mTab04);
			}
			mImgShopping.setImageResource(R.drawable.shopping_pressed);
			break;
		case 4:
			if (mTab05 == null)
			{
				mTab05 = new PersonFragment();
				transaction.add(R.id.id_content, mTab05,"mTab05");
			} else
			{
				transaction.show(mTab05);
			}
			mImgPerson.setImageResource(R.drawable.person_pressed);
				break;
		default:
			break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction)
	{
		if (mTab01 != null)
		{
			transaction.hide(mTab01);
		}
		if (mTab02 != null)
		{
			transaction.hide(mTab02);
		}
		if (mTab03 != null)
		{
			transaction.hide(mTab03);
		}
		if (mTab04 != null)
		{
			transaction.hide(mTab04);
		}
		if (mTab05 != null)
		{
			transaction.hide(mTab05);
		}
	}

	@Override
	public void onClick(View v)
	{
		resetImgs();
		switch (v.getId())
		{
		case R.id.id_index:
			setSelect(0);
			break;
		case R.id.id_classify:
			setSelect(1);
			break;
		case R.id.id_nearby:
			setSelect(2);
			break;
		case R.id.id_shopping:
			setSelect(3);
			break;
		case R.id.id_person:
			setSelect(4);
			break;

		default:
			break;
		}
	}

	private void resetImgs()
	{
		mImgIndex.setImageResource(R.drawable.index_normal);
		mImgClassify.setImageResource(R.drawable.classify_normal);
		mImgNearby.setImageResource(R.drawable.nearby_normal);
		mImgShopping.setImageResource(R.drawable.shopping_normal);
		mImgPerson.setImageResource(R.drawable.person_normal);
	}


	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
		if(mTab01 == null &&fragment instanceof IndexFragment){
			mTab01=fragment;
		}
		if(mTab02 == null &&fragment instanceof ClassifyFragment){
			mTab02=fragment;
		}
		if(mTab03 == null &&fragment instanceof NearbyFragment){
			mTab03=fragment;
		}
		if(mTab04 == null &&fragment instanceof ShoppingCartFragment){
			mTab04=fragment;
		}
		if(mTab05 == null &&fragment instanceof PersonFragment){
			mTab05=fragment;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CommonUtil.SCAN_RESULT && resultCode == RESULT_OK) {
			if (data != null) {
				String content = data.getStringExtra(Constant.CODED_CONTENT);
				Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
			}
		}
	}

}
