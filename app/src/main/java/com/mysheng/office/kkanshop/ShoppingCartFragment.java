package com.mysheng.office.kkanshop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mysheng.office.kkanshop.view.ObservableScrollView;

public class ShoppingCartFragment extends Fragment {
	private LinearLayout line;
	private ObservableScrollView scrollView;
	private int imageHeight=250; //设置渐变高度，一般为导航图片高度，自己控制
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view=inflater.inflate(R.layout.tab03, container, false);
		line=view.findViewById(R.id.line);
		line.bringToFront();
		scrollView= view.findViewById(R.id.scrollView);
		scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
			@Override
			public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
				if (y <= 0) {
					line.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));//AGB由相关工具获得，或者美工提供
				} else if (y > 0 && y <= imageHeight) {
					float scale = (float) y / imageHeight;
					float alpha = (72 * scale);
					// 只是layout背景透明
					line.setBackgroundColor(Color.argb((int) alpha, 227, 29, 26));
				} else {
					line.setBackgroundColor(Color.argb((int) 72, 227, 29, 26));
				}
			}
		});
		return view ;
	}
}
