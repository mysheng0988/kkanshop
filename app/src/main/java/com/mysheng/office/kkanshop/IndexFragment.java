package com.mysheng.office.kkanshop;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mysheng.office.kkanshop.util.BitmapCache;
import com.mysheng.office.kkanshop.util.CommonUtil;
import com.mysheng.office.kkanshop.util.GlideImageLoader;
import com.mysheng.office.kkanshop.util.VolleyImage;
import com.mysheng.office.kkanshop.view.ObservableScrollView;
import com.mysheng.office.kkanshop.zxing.android.CaptureActivity;
import com.mysheng.office.kkanshop.zxing.bean.ZxingConfig;
import com.mysheng.office.kkanshop.zxing.common.Constant;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import java.util.ArrayList;
import static android.app.Activity.RESULT_OK;

public class IndexFragment extends Fragment implements View.OnClickListener{
	private static boolean isFirstDragging = true;
	private ImageView imageView;
	private ImageView scanCode;
	private ImageView chatMsg;
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
		scanCode=view.findViewById(R.id.scan_code);
		chatMsg=view.findViewById(R.id.chat_msg);
		networkImageView=view.findViewById(R.id.netImage);
		banner=view.findViewById(R.id.id_banner);
		line=view.findViewById(R.id.line);
		line.bringToFront();
		chatMsg.setOnClickListener(this);
		scanCode.setOnClickListener(this);
		scrollView= view.findViewById(R.id.scrollView);
		CommonUtil.fullScreen(getActivity());
		line.setBackgroundColor(Color.argb((int) 0, 72, 183, 245));
		scrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
			@Override
			public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
				if (y <= 0) {
					line.setBackgroundColor(Color.argb( 0, 72, 183, 245));//AGB由相关工具获得，或者美工提供
				} else if (y > 0 && y <= imageHeight) {
					Log.d("mys", "onScrollChanged: "+y);
					float scale = (float) y / imageHeight;
					float alpha = (255 * scale);
					// 只是layout背景透明
					line.setBackgroundColor(Color.argb((int) alpha, 72, 183, 245));
				} else {
					line.setBackgroundColor(Color.argb(255, 72, 183, 245));
				}
			}
		});
		initData();
		RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
		refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){

			@Override
			public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
				super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
				if(isFirstDragging&&isDragging){
					isFirstDragging=false;
					line.setVisibility(View.GONE);
				}
			}


		});
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(RefreshLayout refreshlayout) {
				refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
				isFirstDragging=true;

				line.setVisibility(View.VISIBLE);

			}
		});
		refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore(RefreshLayout refreshlayout) {
				refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败

			}
		});

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

	@Override
	public void onClick(View v) {
		PackageManager pm = getActivity().getPackageManager();
		switch (v.getId()){
			case R.id.scan_code:

				if(! (pm.checkPermission("android.permission.CAMERA", "com.mysheng.office.kkanshop")==PackageManager.PERMISSION_GRANTED ) ) {
					IndexFragment.this.requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
							CommonUtil.SCAN_CODE);
				}else {
					startScanCode();
				}
				break;
			case R.id.chat_msg:
				Intent intent = new Intent(getActivity(), ChatActivity.class);
				startActivity(intent);
			    break;
		}

	}
	private void startScanCode(){
		Intent intent = new Intent(getActivity(), CaptureActivity.class);
		/*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
		 * 也可以不传这个参数
		 * 不传的话  默认都为默认不震动  其他都为true
		 * */
		ZxingConfig config = new ZxingConfig();
		config.setPlayBeep(true);
		config.setShake(true);
		intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
		startActivityForResult(intent, CommonUtil.SCAN_RESULT);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// 扫描二维码/条码回传
		if (requestCode == CommonUtil.SCAN_RESULT && resultCode == RESULT_OK) {
			if (data != null) {
				String content = data.getStringExtra(Constant.CODED_CONTENT);
				Toast.makeText(getActivity(),content,Toast.LENGTH_SHORT).show();
			}
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if(requestCode==CommonUtil.SCAN_CODE){
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED)
			{
				startScanCode();
			} else {
				// Permission Denied
				Toast.makeText(getActivity(), "您已拒绝，请打开手机应用权限设置", Toast.LENGTH_SHORT).show();
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
}
