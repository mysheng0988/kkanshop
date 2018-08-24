package com.mysheng.office.kkanshop;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mysheng.office.kkanshop.adapter.GoodShopAdapter;
import com.mysheng.office.kkanshop.adapter.KillAdapter;
import com.mysheng.office.kkanshop.adapter.ShopAdapter;
import com.mysheng.office.kkanshop.entity.KillModel;
import com.mysheng.office.kkanshop.entity.ShopModel;
import com.mysheng.office.kkanshop.util.BitmapCache;
import com.mysheng.office.kkanshop.util.CommonUtil;
import com.mysheng.office.kkanshop.util.CustomToast;
import com.mysheng.office.kkanshop.util.GlideImageLoader;
import com.mysheng.office.kkanshop.util.VolleyImage;
import com.mysheng.office.kkanshop.view.NoticeView;
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
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class IndexFragment extends Fragment implements View.OnClickListener{
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
	private RecyclerView recyclerView;
	private RecyclerView recyclerViewShop;
	private KillAdapter mKillAdapter;
	private GoodShopAdapter mGoodShopAdapter;
	private LinearLayoutManager mLinearManager;
	private LinearLayoutManager mLinearManager2;
	private List<KillModel> mList=new ArrayList<>();
	private List<String> listPath=new ArrayList<>();//储存秒杀地址
	private List<ShopModel> shopList=new ArrayList<>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
	{
		View view= inflater.inflate(R.layout.tab01, container, false);
		imageView=view.findViewById(R.id.image);
		recyclerView=view.findViewById(R.id.recyclerView);
		recyclerViewShop=view.findViewById(R.id.recyclerViewShop);

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

		refreshLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(RefreshLayout refreshlayout) {
				refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败

			}
		});
		refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore(RefreshLayout refreshlayout) {
				refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败

			}
		});
		initNotice(view);
		return view;
	}

	private void initData() {
		banner.setImageLoader(new GlideImageLoader());
		list_path.add("http://i1.mifile.cn/f/i/2018/mix2s/summary/infor-1.jpg");
		list_path.add("http://i1.mifile.cn/a4/xmad_152940243093_EgRIT.jpg");
		list_path.add("http://i1.mifile.cn/a4/xmad_1529377715473_xVjDr.jpg");
		list_path.add("http://i1.mifile.cn/a4/xmad_15293127351522_gPtTj.jpg");
		list_path.add("http://i1.mifile.cn/a4/xmad_15248221330196_tvCXl.jpg");
		list_path.add("http://i1.mifile.cn/a4/xmad_15281678020877_ZAHgw.jpg");
		list_path.add("https://res1.vmallres.com/shopdc/pic/3737747d-ca26-4070-99eb-c64eb469e101.png");
		banner.setImages(list_path);
		list_title.add("小米mix2s 3299起");
		list_title.add("小米8-小米八周年纪念版");
		list_title.add("小米6x");
		list_title.add("小米游戏本");
		list_title.add("小米九号平衡车");
		list_title.add("红米5最高立减200元");
		list_title.add("华为P20pro");
		banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
		banner.setOnBannerListener(new OnBannerListener() {
			@Override
			public void OnBannerClick(int position) {
				Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();

			}
		});
		banner.setBannerTitles(list_title);

		//banner设置方法全部调用完毕时最后调用
		banner.start();
		mLinearManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
		mLinearManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
		// 从底部开始显示
		//mLinearManager.setStackFromEnd(true);
		recyclerView.setLayoutManager(mLinearManager);
		recyclerViewShop.setLayoutManager(mLinearManager2);
		mKillAdapter=new KillAdapter(getActivity());
		listPath.add("https://i1.mifile.cn/a1/pms_1528719476.67789934!220x220.jpg");
		listPath.add("https://i1.mifile.cn/a1/pms_1527144859.25489991!220x220.jpg");
		listPath.add("https://i1.mifile.cn/a4/xmad_14972549116226_tZpod.png");
		listPath.add("https://i1.mifile.cn/a1/pms_1492999959.43955760!220x220.jpg");
		listPath.add("https://i1.mifile.cn/a1/pms_1519609640.9267740!140x140.jpg");
		listPath.add("https://i1.mifile.cn/a1/pms_1528092587.49664451!220x220.jpg");
		for (int i=0;i<listPath.size();i++){
			KillModel model=new KillModel();
			model.setImagePath(listPath.get(i));
			Random random=new Random();
			int num=random.nextInt(1000);
			int num2=random.nextInt(1000);

			model.setOldPrice("￥"+Math.max(num,num2)+".00");
			model.setPrice("￥"+Math.min(num,num2)+".00");
			mList.add(model);
		}
		mKillAdapter.setData(mList);
		mKillAdapter.setOnItemClickCallback(new KillAdapter.OnItemClickCallback() {
			@Override
			public void onItemClick(int position, LinearLayout view) {
				Toast.makeText(getActivity(),mList.get(position).getPrice(),Toast.LENGTH_SHORT).show();
				//CustomToast.show(getContext(),mList.get(position).getPrice());
			}
		});
		recyclerView.setAdapter(mKillAdapter);
		mGoodShopAdapter=new GoodShopAdapter(getActivity());
		ShopModel shopModel=new ShopModel();
		shopModel.setImagePath1("https://i1.mifile.cn/a1/pms_1509723483.31416776!220x220.jpg");
		shopModel.setImagePath2("https://i1.mifile.cn/a1/pms_1527684990.93616987!220x220.jpg");
		shopModel.setImagePath3("https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/86566f01e26104c8c36e1201223385b7.jpg");
		shopModel.setShopName("小米官方旗舰店");
		shopModel.setShopNum("1111");
		shopList.add(shopModel);
		ShopModel shopModel2=new ShopModel();
		shopModel2.setImagePath1("https://openfile.meizu.com/group1/M00/02/F9/Cgbj0VpcI-6AHsPAAACF-hNGTkg171_180x180.jpg");
		shopModel2.setImagePath2("https://openfile.meizu.com/group1/M00/04/0E/Cgbj0FrcbsCANuv_AAzufmGf2yU449.png@240x240.jpg");
		shopModel2.setImagePath3("https://openfile.meizu.com/group1/M00/02/31/Cgbj0VnCIPSAZlXMAA4noi5FGFE697.png@240x240.png");
		shopModel2.setShopName("魅族官方旗舰店");
		shopModel2.setShopNum("22222");
		shopList.add(shopModel2);
		ShopModel shopModel3=new ShopModel();
		shopModel3.setImagePath1("https://consumer-img.huawei.com/content/dam/huawei-cbg-site/greate-china/cn/mkt/pdp/phones/p20-pro-update1/img/kv-mobile-v2-original.jpg");
		shopModel3.setImagePath2("https://consumer-img.huawei.com/content/dam/huawei-cbg-site/greate-china/cn/mkt/list-image/phones/p20pro-listimage-pink-original.png");
		shopModel3.setImagePath3("https://consumer-img.huawei.com/content/dam/huawei-cbg-site/greate-china/cn/mkt/list-image/phones/maters-list-image-original.png");
		shopModel3.setShopName("华为官方旗舰店");
		shopModel3.setShopNum("33333");
		shopList.add(shopModel3);
		mGoodShopAdapter.setData(shopList);
		recyclerViewShop.setAdapter(mGoodShopAdapter);



		String path="http://b399.photo.store.qq.com/psb?/V1435sy10opqoy/zwBEegnRC.5C0UjiyMpKXjYsFsO5YJDkwd5YSTVoYW4!/b/dD452u2qJwAA&bo=gAJVAwAAAAABB*Q!&rf=viewer_4&t=5";
		VolleyImage.loadImageByURL(path,imageView);
		String url="http://b395.photo.store.qq.com/psb?/V1435sy10opqoy/qjZQCDLy.Mm0fZii7pxrOPqMod6kok2FDurfkCTVyQ4!/b/dPyhf.ugBQAA&bo=gAJVAwAAAAABB*Q!&rf=viewer_4&t=5";
		VolleyImage.NetworkImageViewByURL(url,networkImageView);
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
		banner.startAutoPlay();
	}

	@Override
	public void onPause() {
		super.onPause();
		banner.stopAutoPlay();
	}


}
