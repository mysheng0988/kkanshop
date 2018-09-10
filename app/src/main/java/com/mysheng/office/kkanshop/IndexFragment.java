package com.mysheng.office.kkanshop;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mysheng.office.kkanshop.adapter.ClassifyAdapter;
import com.mysheng.office.kkanshop.adapter.GoodShopAdapter;
import com.mysheng.office.kkanshop.adapter.IndexAdapter;
import com.mysheng.office.kkanshop.adapter.KillAdapter;
import com.mysheng.office.kkanshop.adapter.NavAdapter;

import com.mysheng.office.kkanshop.banner.Banner;
import com.mysheng.office.kkanshop.banner.BannerConfig;
import com.mysheng.office.kkanshop.banner.GlideImageLoader;
import com.mysheng.office.kkanshop.banner.listener.OnBannerListener;
import com.mysheng.office.kkanshop.decoration.DividerGridItemDecoration;
import com.mysheng.office.kkanshop.entity.BannerModel;
import com.mysheng.office.kkanshop.entity.DataModel;
import com.mysheng.office.kkanshop.entity.IndexToos;
import com.mysheng.office.kkanshop.entity.KillModel;
import com.mysheng.office.kkanshop.entity.NavModel;
import com.mysheng.office.kkanshop.entity.ShopModel;

import com.mysheng.office.kkanshop.manager.FullyGridLayoutManager;
import com.mysheng.office.kkanshop.manager.FullyLinearLayoutManager;
import com.mysheng.office.kkanshop.util.CommonUtil;

import com.mysheng.office.kkanshop.view.NoticeView;
import com.mysheng.office.kkanshop.view.ObservableScrollView;
import com.mysheng.office.kkanshop.zxing.android.CaptureActivity;
import com.mysheng.office.kkanshop.zxing.bean.ZxingConfig;
import com.mysheng.office.kkanshop.zxing.common.Constant;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class IndexFragment extends Fragment implements View.OnClickListener{
	private ImageView scanCode;
	private ImageView chatMsg;
	private RefreshLayout refreshLayout;
	private LinearLayout line;
	private ObservableScrollView scrollView;
	private int imageHeight=200;

	private IndexAdapter mIndexAdapter;
	private RecyclerView mRecyclerView;
	private ArrayList<String> list_path=new ArrayList<>();
	private ArrayList<String> list_title=new ArrayList<>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
		View view= inflater.inflate(R.layout.tab01, container, false);
		initAttrInit(view);
		initView();
		initEvent();
		return view;
	}
	private void initAttrInit(View view){
		scanCode=view.findViewById(R.id.scan_code);
		chatMsg=view.findViewById(R.id.chat_msg);
		mRecyclerView=view.findViewById(R.id.index_recyclerView);
		line=view.findViewById(R.id.line);
		line.bringToFront();
		refreshLayout = view.findViewById(R.id.refreshLayout);
		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(RefreshLayout refreshlayout) {
				refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

			}
		});
		refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore( RefreshLayout refreshlayout) {
				refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败

			}
		});
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
	}
	private void initView(){
		list_path.add("http://i1.mifile.cn/f/i/2018/mix2s/summary/infor-1.jpg");
		list_path.add("http://i1.mifile.cn/a4/xmad_152940243093_EgRIT.jpg");
		list_path.add("http://i1.mifile.cn/a4/xmad_1529377715473_xVjDr.jpg");
		list_path.add("http://i1.mifile.cn/a4/xmad_15293127351522_gPtTj.jpg");
		list_path.add("http://i1.mifile.cn/a4/xmad_15248221330196_tvCXl.jpg");
		list_path.add("http://i1.mifile.cn/a4/xmad_15281678020877_ZAHgw.jpg");
		list_path.add("https://res1.vmallres.com/shopdc/pic/3737747d-ca26-4070-99eb-c64eb469e101.png");
		list_title.add("小米mix2s 3299起");
		list_title.add("小米8-小米八周年纪念版");
		list_title.add("小米6x");
		list_title.add("小米游戏本");
		list_title.add("小米九号平衡车");
		list_title.add("红米5最高立减200元");
		list_title.add("华为P20pro");
		BannerModel bannerModel=new BannerModel();
		bannerModel.setImgPaths(list_path);
		bannerModel.setTitles(list_title);
		GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),6);
		gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				int viewType=mRecyclerView.getAdapter().getItemViewType(position);
				switch (viewType){
					case IndexToos.BANNER:
					case IndexToos.NAV:
						return 6;
					default:
						return 3;
				}
			}
		});
		mRecyclerView.setLayoutManager(gridLayoutManager);
		if(mIndexAdapter==null){
			mIndexAdapter=new IndexAdapter(getActivity());
		}else{
			mIndexAdapter.notifyDataSetChanged();
		}
		List<BannerModel> list=new ArrayList<>();
		list.add(bannerModel);
		List<NavModel> navModels=new ArrayList<>();
		NavModel navModel=new NavModel();
		navModels.add(navModel);
		mIndexAdapter.addBanner(list);
		mIndexAdapter.addNav(navModels);
		mRecyclerView.setAdapter(mIndexAdapter);

	}
	private void initEvent(){
		chatMsg.setOnClickListener(this);
		scanCode.setOnClickListener(this);
	}

	private void initNotice(View view) {
		NoticeView noticeView = view.findViewById(R.id.notice_view);
		List<String> notices = new ArrayList<>();
		notices.add("大促销下单拆福袋，亿万新年红包随便拿");
		notices.add("家电五折团，抢十亿无门槛现金红包");
		notices.add("星球大战剃须刀首发送200元代金券");
		noticeView.addNotice(notices);
		noticeView.startFlipping();
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
				Intent intent = new Intent(getActivity(), ChatListViewActivity.class);
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

	@Override
	public void onResume() {
		super.onResume();
		//banner.startAutoPlay();
	}

	@Override
	public void onPause() {
		super.onPause();
		//banner.stopAutoPlay();
	}


}
