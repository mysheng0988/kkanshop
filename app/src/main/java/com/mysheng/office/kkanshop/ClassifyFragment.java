package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mysheng.office.kkanshop.adapter.ShopAdapter;

public class ClassifyFragment extends Fragment  implements
		AdapterView.OnItemClickListener {
	private String[] strs = { "常用分类", "服饰内衣", "鞋靴", "手机", "家用电器", "数码", "电脑办公",
			"个护化妆", "图书","男鞋","女鞋" ,"男装","女装"};
	private ListView listView;
	private ShopAdapter adapter;
	private ShopFragment myFragment;
	public static int mPosition;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.tab02, container, false);
		initView(view);
		return view;
	}
	/**
	 * 初始化view
	 */
	private void initView(View view) {
		// TODO Auto-generated method stub
		listView = view.findViewById(R.id.listview);

		adapter = new ShopAdapter(getActivity(), strs);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(this);

		//创建MyFragment对象
		myFragment = new ShopFragment();
		FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager()
				.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_container, myFragment);
		//通过bundle传值给MyFragment
		Bundle bundle = new Bundle();
		bundle.putString(ShopFragment.TAG, strs[mPosition]);
		myFragment.setArguments(bundle);
		fragmentTransaction.commit();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		// TODO Auto-generated method stub
		//拿到当前位置
		mPosition = position;
		//即使刷新adapter
		adapter.notifyDataSetChanged();
		for (int i = 0; i < strs.length; i++) {
			myFragment = new ShopFragment();
			FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.replace(R.id.fragment_container, myFragment);
			Bundle bundle = new Bundle();
			bundle.putString(ShopFragment.TAG, strs[position]);
			myFragment.setArguments(bundle);
			fragmentTransaction.commit();
		}
	}
}
