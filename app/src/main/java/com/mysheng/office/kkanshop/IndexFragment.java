package com.mysheng.office.kkanshop;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mysheng.office.kkanshop.util.BitmapCache;
import com.mysheng.office.kkanshop.util.GlideImageLoader;
import com.mysheng.office.kkanshop.util.VolleyImage;
import com.mysheng.office.kkanshop.view.ObservableScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;


public class IndexFragment extends Fragment {
	private ImageView imageView;
	private NetworkImageView networkImageView;
	private Banner banner;
	private ArrayList<String> list_path=new ArrayList<>();
	private ArrayList<String> list_title=new ArrayList<>();
	private LinearLayout line;
	private ObservableScrollView scrollView;
	private int imageHeight=300;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
		View view= inflater.inflate(R.layout.tab01, container, false);
		imageView=view.findViewById(R.id.image);
		networkImageView=view.findViewById(R.id.netImage);
		banner=view.findViewById(R.id.id_banner);
		line=view.findViewById(R.id.line);
		line.bringToFront();
		scrollView= view.findViewById(R.id.scrollView);
		scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
			@Override
			public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
				if (y <= 0) {
					line.setBackgroundColor(Color.argb((int) 0, 72, 183, 245));//AGB由相关工具获得，或者美工提供
				} else if (y > 0 && y <= imageHeight) {
					Log.d("mys", "onScrollChanged: "+y);
					float scale = (float) y / imageHeight;
					float alpha = (255 * scale);
					// 只是layout背景透明
					line.setBackgroundColor(Color.argb((int) alpha, 72, 183, 245));
				} else {
					line.setBackgroundColor(Color.argb((int) 255, 72, 183, 245));
				}
			}
		});
		initData();

		return view;
	}

	private void initData() {
		banner.setImageLoader(new GlideImageLoader());
		list_path.add("http://b399.photo.store.qq.com/psb?/V1435sy10opqoy/zwBEegnRC.5C0UjiyMpKXjYsFsO5YJDkwd5YSTVoYW4!/b/dD452u2qJwAA&bo=gAJVAwAAAAABB*Q!&rf=viewer_4&t=5");
		list_path.add("http://b395.photo.store.qq.com/psb?/V1435sy10opqoy/qjZQCDLy.Mm0fZii7pxrOPqMod6kok2FDurfkCTVyQ4!/b/dPyhf.ugBQAA&bo=gAJVAwAAAAABB*Q!&rf=viewer_4&t=5");
		list_path.add("http://b399.photo.store.qq.com/psb?/V1435sy10opqoy/zwBEegnRC.5C0UjiyMpKXjYsFsO5YJDkwd5YSTVoYW4!/b/dD452u2qJwAA&bo=gAJVAwAAAAABB*Q!&rf=viewer_4&t=5");
		list_path.add("http://b395.photo.store.qq.com/psb?/V1435sy10opqoy/qjZQCDLy.Mm0fZii7pxrOPqMod6kok2FDurfkCTVyQ4!/b/dPyhf.ugBQAA&bo=gAJVAwAAAAABB*Q!&rf=viewer_4&t=5");
		banner.setImages(list_path);
		list_title.add("小笨猪");
		list_title.add("小懒猪");
		list_title.add("555");
		list_title.add("1111");
		banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
		banner.setBannerTitles(list_title);

		//banner设置方法全部调用完毕时最后调用
		banner.start();
		String path="http://b399.photo.store.qq.com/psb?/V1435sy10opqoy/zwBEegnRC.5C0UjiyMpKXjYsFsO5YJDkwd5YSTVoYW4!/b/dD452u2qJwAA&bo=gAJVAwAAAAABB*Q!&rf=viewer_4&t=5";
		VolleyImage.loadImageByURL(path,imageView);
		String url="http://b395.photo.store.qq.com/psb?/V1435sy10opqoy/qjZQCDLy.Mm0fZii7pxrOPqMod6kok2FDurfkCTVyQ4!/b/dPyhf.ugBQAA&bo=gAJVAwAAAAABB*Q!&rf=viewer_4&t=5";
		VolleyImage.NetworkImageViewByURL(url,networkImageView);
	}

	private void getImageByURL(){
		String url = "http://g.hiphotos.baidu.com/image/pic/item/0ff41bd5ad6eddc487907ddd3cdbb6fd526633a5.jpg";
		ImageLoader loader = new ImageLoader(KkanApplication.getHttpQueues(), new BitmapCache());
		imageView.setImageResource(R.drawable.default_header);
		ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.default_header , R.drawable.default_header );
		loader.get(url, listener);
	}
}
