package com.mysheng.office.kkanshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener
{
	private LinearLayout mIndex;
	private LinearLayout mClassify;
	private LinearLayout mFlow;
	private LinearLayout mPerson;

	private ImageButton mImgIndex;
	private ImageButton mImgClassify;
	private ImageButton mImgShopping;
	private ImageButton mImgPerson;

	private Fragment mTab01;
	private Fragment mTab02;
	private Fragment mTab03;
	private Fragment mTab04;
	FragmentManager manager;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{


		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();

		setSelect(0);
	}

	private void initEvent()
	{
		mIndex.setOnClickListener(this);
		mClassify.setOnClickListener(this);
		mFlow.setOnClickListener(this);
		mPerson.setOnClickListener(this);
	}

	private void initView()
	{
		mIndex =  findViewById(R.id.id_index);
		mClassify =  findViewById(R.id.id_classify);
		mFlow =  findViewById(R.id.id_shopping);
		mPerson =  findViewById(R.id.id_person);

		mImgIndex =  findViewById(R.id.id_index_img);
		mImgClassify =  findViewById(R.id.id_classify_img);
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
				mTab03 = new ShoppingCartFragment();
				transaction.add(R.id.id_content, mTab03,"mTab03");
			} else
			{
				transaction.show(mTab03);
			}
			mImgShopping.setImageResource(R.drawable.shopping_pressed);
			break;
		case 3:
			if (mTab04 == null)
			{
				mTab04 = new PersonFragment();
				transaction.add(R.id.id_content, mTab04,"mTab04");
			} else
			{
				transaction.show(mTab04);
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
		case R.id.id_shopping:
			setSelect(2);
			break;
		case R.id.id_person:
			setSelect(3);
			break;

		default:
			break;
		}
	}

	private void resetImgs()
	{
		mImgIndex.setImageResource(R.drawable.index_normal);
		mImgClassify.setImageResource(R.drawable.classify_normal);
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
		if(mTab03 == null &&fragment instanceof ShoppingCartFragment){
			mTab03=fragment;
		}
		if(mTab04 == null &&fragment instanceof PersonFragment){
			mTab04=fragment;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
