package com.mysheng.office.kkanshop;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mysheng.office.kkanshop.adapter.IndexAdapter;
import com.mysheng.office.kkanshop.decoration.DividerGridItemDecoration;
import com.mysheng.office.kkanshop.entity.BannerImage;
import com.mysheng.office.kkanshop.entity.BannerModel;
import com.mysheng.office.kkanshop.entity.GoTitleModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.KillModel;
import com.mysheng.office.kkanshop.entity.LoveModel;
import com.mysheng.office.kkanshop.entity.NavModel;
import com.mysheng.office.kkanshop.entity.NoticeModel;
import com.mysheng.office.kkanshop.entity.RecommendModel;
import com.mysheng.office.kkanshop.entity.ShopModel;
import com.mysheng.office.kkanshop.entity.TitleModel;
import com.mysheng.office.kkanshop.entity.TypeMode;
import com.mysheng.office.kkanshop.listenter.OnItemClickListener;
import com.mysheng.office.kkanshop.util.CommonUtil;
import com.mysheng.office.kkanshop.util.UtilToast;
import com.mysheng.office.kkanshop.util.Utils;
import com.mysheng.office.kkanshop.util.UtilsDate;
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

public class IndexFragment extends Fragment implements View.OnClickListener,IndexAdapter.OnBannerClickListener,OnItemClickListener<TypeMode>{
	private ImageView scanCode;
	private ImageView chatMsg;
	private ImageView backTop;
	private RefreshLayout refreshLayout;
	private LinearLayout line;
	private IndexAdapter mIndexAdapter;
	private RecyclerView mRecyclerView;
	private List<String> list_path=new ArrayList<>();
	private List<String> list_title=new ArrayList<>();
	private List<String> listNews=new ArrayList<>();
	private List<TypeMode> mList=new ArrayList<>();

	private String[] killPath={
			"https://i1.mifile.cn/a1/pms_1528719476.67789934!220x220.jpg",
			"https://i1.mifile.cn/a1/pms_1527144859.25489991!220x220.jpg",
			"https://i1.mifile.cn/a4/xmad_14972549116226_tZpod.png",
			"https://i1.mifile.cn/a1/pms_1492999959.43955760!220x220.jpg"
//			"https://i1.mifile.cn/a1/pms_1519609640.9267740!140x140.jpg",
//			"https://i1.mifile.cn/a1/pms_1528092587.49664451!220x220.jpg",
	};

	private String[] loveTitle={"玩3c", "购家电", "逛超市", "爱家", "爱宝宝","爱美丽","爱吃","爱逛"};
	private String[] discountTitle={"抢iPhone7红", "800元现金券", "乳品二免一", "居家物199-100", "满199减50","YSL黑管唇釉","领券99减50","满399减100"};
	private String[] labelTitle={"抢iPhone7红", "", "", "家居小商品节", "","","",""};
	private String[] lovePath={
			"https://m.360buyimg.com/mobilecms/jfs/t4390/306/810593954/43649/db7da1a3/58d4f31dN7f95f121.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t3277/14/9826379985/16115/3bb30dc7/58d8ce10Nc69a898e.jpg!q70.jpg",
			 "https://m.360buyimg.com/mobilecms/jfs/t4663/320/926911638/15321/7cdb9777/58d66630N3aa7e836.jpg!q70.jpg",
		     "https://m.360buyimg.com/mobilecms/jfs/t4516/339/787269831/33006/b2d29efe/58d5008dN359de233.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4675/199/1080554759/28689/4d53c00e/58d88d51N32dfc9e7.png!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4273/172/3010816439/22923/3096939d/58d8b505N70640765.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4426/51/1099039770/16697/134acadd/58d8d2e1N3b849fd9.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4543/232/1163237356/8249/e2e2069c/58d9c2ecN771ca3f0.jpg!q70.jpg",
	};
	private String[] shopTitle={"大家电馆", "母婴馆", "时尚馆", "手机数码", "美食城","电脑办公","鞋靴箱包","小家电馆", "生鲜馆","家装城","图书音像","中外名酒"};
	private String[] shopDiscountTitle={"白条6期免息", "满199减100", "京东丝袜节", "联想爆品五折秒", "5折起","399减120","每满399减20","每满199减20", "满2件8折","跨万店4免1","每满150减50","啤酒99减20"};
	private String[] shopLabelTitle={"", "", "", "", "","","",""};
	private String[] shopPath={
			"https://m.360buyimg.com/n1/jfs/t3622/276/660901756/68531/e5d7241b/5813f6fbN03214d2d.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4666/290/1329628173/34033/a8e29b76/58dcdf92N5e3dff44.png!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4246/177/3319375981/3828920/76ed6c38/58df9758Ndb750df0.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4378/235/3560807378/24770/8f222ffe/58e442e5Ndddccdae.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4285/117/3109257512/19944/1be3b538/58db634bNcdb3ec1b.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t5068/345/197333997/10951/544031c8/58dca172N265a12e6.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4228/306/3195162999/8876/88ab9216/58db69e3N0a944318.jpg!q70.jpg",
			"https://m.360buyimg.com/n1/jfs/t3088/300/7513997890/259901/98178f5/58b7c78cNbd0b5e05.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4333/136/3260917771/19068/b517fd1/58df8c62Na031087d.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4570/19/1318587865/17550/8a7e8ca/58dcb5ddN186af61e.png!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4774/324/569821243/4473/eed964ec/58e44fb4Nd8e78b24.jpg!q70.jpg",
			"https://m.360buyimg.com/mobilecms/jfs/t4600/15/1356162856/36024/b9a45109/58dcdb49N5d95b788.png!q70.jpg",
	};
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
		backTop=view.findViewById(R.id.backTop);
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
				List<TypeMode> recommendModels=new ArrayList<>();
				for(int i=0;i<IndexTools.list.length;i++){
					RecommendModel reModel=new RecommendModel();
					reModel.setGoodsPath(IndexTools.list[i]);
					reModel.setTypeParam(IndexTools.Recommend);
					reModel.setGoodsTitle(IndexTools.title);
					Random random=new Random();
					int num=random.nextInt(1000);
					reModel.setPrice("￥:"+num+".00");
					recommendModels.add(reModel);
				}
				int start=mIndexAdapter.getItemCount();
				mIndexAdapter.setModelList(recommendModels);
				int end=mIndexAdapter.getItemCount();
				mIndexAdapter.notifyItemRangeChanged(start,end);
				refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败

			}
		});
		//scrollView= view.findViewById(R.id.scrollView);
		CommonUtil.fullScreen(getActivity());
		line.setBackgroundColor(Color.argb((int) 0, 255, 153, 0));
		mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if (dy <= 0) {
					line.setBackgroundColor(Color.argb( 0, 255, 153, 0));//AGB由相关工具获得，或者美工提供
					backTop.setVisibility(View.VISIBLE);
				}
//				else if (dy > 0 && y <= imageHeight) {
//					Log.d("mys", "onScrollChanged: "+y);
//					float scale = (float) y / imageHeight;
//					float alpha = (255 * scale);
//					// 只是layout背景透明
//					line.setBackgroundColor(Color.argb((int) alpha, 72, 183, 245));
//				}
				else {
					line.setBackgroundColor(Color.argb(255, 255, 153, 0));
					backTop.setVisibility(View.GONE);
				}
			}
		});
	}
	private void initView(){
		GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),8);
		mRecyclerView.addItemDecoration(new DividerGridItemDecoration());
		gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
			@Override
			public int getSpanSize(int position) {
				int viewType=mRecyclerView.getAdapter().getItemViewType(position);
				switch (viewType){
					case IndexTools.BANNER:
					case IndexTools.KILLTITLE:
					case IndexTools.SHOPTITLE:
					case IndexTools.NOTICE:
					case IndexTools.GOTitle:
					case IndexTools.GOShopTitle:
					case IndexTools.GOReTitle:
						return 8;
					case IndexTools.LOVE_TWO:
					case IndexTools.Recommend:
					case IndexTools.GOSHOPTWO:
						return 4;
					case IndexTools.KILL:
					case IndexTools.NAV:
					case IndexTools.LOVE_FOUR:
					case IndexTools.GOSHOPFOUR:
						return 2;
					default:
						return 4;
				}
			}
		});
		mRecyclerView.setLayoutManager(gridLayoutManager);
		if(mIndexAdapter==null){
			mIndexAdapter=new IndexAdapter(getActivity());
		}else{
			mIndexAdapter.notifyDataSetChanged();
		}
		BannerModel bannerModel=new BannerModel();
		bannerModel.setTypeParam(IndexTools.BANNER);
		bannerModel.setListShopId(IndexTools.listShopId);
		bannerModel.setListPath(IndexTools.BANNER_IMAGE);
		bannerModel.setListTitle(IndexTools.BANNER_TITLE);
		bannerModel.setListGoodsId(IndexTools.listGoodsId);
		mList.add(bannerModel);
		for (int i=0;i<IndexTools.navTitle.length;i++){
			NavModel navModel=new NavModel();
			navModel.setNavIcon(IndexTools.navIcon[i]);
			navModel.setNavTitle(IndexTools.navTitle[i]);
			navModel.setTypeParam(IndexTools.NAV);
			mList.add(navModel);
		}

		NoticeModel noticeModel=new NoticeModel();
		listNews.add("大促销下单拆福袋，亿万新年红包随便拿");
		listNews.add("家电五折团，抢十亿无门槛现金红包");
		listNews.add("星球大战剃须刀首发送200元代金券");
		noticeModel.setTypeParam(IndexTools.NOTICE);
		noticeModel.setTextList(listNews);
		mList.add(noticeModel);
		TitleModel titleModel=new TitleModel();
		titleModel.setLeftTitle("整点秒杀");
		titleModel.setTypeParam(IndexTools.KILLTITLE);
		String hour= UtilsDate.getTodayDate("HH");
		titleModel.setCenterTitle(hour);
		mList.add(titleModel);

		for (int i=0;i<killPath.length;i++){
			KillModel model=new KillModel();
			model.setImagePath(killPath[i]);
			model.setTypeParam(IndexTools.KILL);
			Random random=new Random();
			int num=random.nextInt(1000);
			int num2=random.nextInt(1000);
			model.setOldPrice("￥"+Math.max(num,num2)+".00");
			model.setPrice("￥"+Math.min(num,num2)+".00");
			mList.add(model);
		}
		TitleModel titleModel2=new TitleModel();
		titleModel2.setTypeParam(IndexTools.SHOPTITLE);
		titleModel2.setLeftTitle("发现好店");
		mList.add(titleModel2);
		ShopModel shopModel=new ShopModel();
		shopModel.setTypeParam(IndexTools.GOODSSHOP);
		shopModel.setImagePath1("https://i1.mifile.cn/a1/pms_1509723483.31416776!220x220.jpg");
		shopModel.setImagePath2("https://i1.mifile.cn/a1/pms_1527684990.93616987!220x220.jpg");
		shopModel.setImagePath3("https://cdn.cnbj0.fds.api.mi-img.com/b2c-mimall-media/86566f01e26104c8c36e1201223385b7.jpg");
		shopModel.setShopName("小米官方旗舰店");
		shopModel.setShopNum("1111");
		mList.add(shopModel);
		ShopModel shopModel2=new ShopModel();
		shopModel2.setImagePath1("https://openfile.meizu.com/group1/M00/02/F9/Cgbj0VpcI-6AHsPAAACF-hNGTkg171_180x180.jpg");
		shopModel2.setImagePath2("https://openfile.meizu.com/group1/M00/04/0E/Cgbj0FrcbsCANuv_AAzufmGf2yU449.png@240x240.jpg");
		shopModel2.setImagePath3("https://openfile.meizu.com/group1/M00/02/31/Cgbj0VnCIPSAZlXMAA4noi5FGFE697.png@240x240.png");
		shopModel2.setShopName("魅族官方旗舰店");
		shopModel2.setShopNum("22222");
		shopModel2.setTypeParam(IndexTools.GOODSSHOP);
		mList.add(shopModel2);
		GoTitleModel goTitleModel=new GoTitleModel();
		goTitleModel.setGoTitle("--爱生活--");
		goTitleModel.setTypeParam(IndexTools.GOTitle);
		mList.add(goTitleModel);

		for(int i=0;i<loveTitle.length;i++){
			if(i<4){
				LoveModel loveModel1=new LoveModel();
				loveModel1.setLoveTitle(loveTitle[i]);
				loveModel1.setDiscountTitle(discountTitle[i]);
				loveModel1.setLabelTitle(labelTitle[i]);
				loveModel1.setLovePath(lovePath[i]);
				loveModel1.setTypeParam(IndexTools.LOVE_TWO);
				mList.add(loveModel1);
			}else {
				LoveModel loveModel1=new LoveModel();
				loveModel1.setLoveTitle(loveTitle[i]);
				loveModel1.setDiscountTitle(discountTitle[i]);
				loveModel1.setTypeParam(IndexTools.LOVE_FOUR);
				loveModel1.setLovePath(lovePath[i]);
				mList.add(loveModel1);
			}
		}
		GoTitleModel goTitleModel2=new GoTitleModel();
		goTitleModel2.setGoTitle("--逛商场--");
		goTitleModel2.setTypeParam(IndexTools.GOShopTitle);
		mList.add(goTitleModel2);
		for(int i=0;i<shopTitle.length;i++){
			if(i<4){
				LoveModel loveModel1=new LoveModel();
				loveModel1.setLoveTitle(shopTitle[i]);
				loveModel1.setDiscountTitle(shopDiscountTitle[i]);
				loveModel1.setTypeParam(IndexTools.GOSHOPTWO);
				loveModel1.setLabelTitle(shopLabelTitle[i]);
				loveModel1.setLovePath(shopPath[i]);
				mList.add(loveModel1);
			}else {
				LoveModel loveModel1=new LoveModel();
				loveModel1.setLoveTitle(shopTitle[i]);
				loveModel1.setDiscountTitle(shopDiscountTitle[i]);
				loveModel1.setTypeParam(IndexTools.GOSHOPFOUR);
				loveModel1.setLovePath(shopPath[i]);
				mList.add(loveModel1);
			}
		}
		GoTitleModel goTitleModel3=new GoTitleModel();
		goTitleModel3.setGoTitle("--为*你*推*荐--");
		goTitleModel3.setTypeParam(IndexTools.GOReTitle);
		mList.add(goTitleModel3);

		for(int i=0;i<IndexTools.list.length;i++){
			RecommendModel reModel=new RecommendModel();
			reModel.setGoodsPath(IndexTools.list[i]);
			reModel.setGoodsTitle(IndexTools.title);
			reModel.setTypeParam(IndexTools.Recommend);
			Random random=new Random();
			int num=random.nextInt(1000);
			int num2=random.nextInt(2)+1;
			int num3=random.nextInt(2)+1;
			reModel.setReduce(num2==1);
			reModel.setVoucher(num3==2);
			reModel.setPrice("￥:"+num+".00");
			mList.add(reModel);
		}
		mIndexAdapter.setModelList(mList);
		mRecyclerView.setAdapter(mIndexAdapter);

	}
	private void initEvent(){
		mIndexAdapter.setOnBannerClickListener(this);
		mIndexAdapter.setOnItemClickListener(this);
		chatMsg.setOnClickListener(this);
		scanCode.setOnClickListener(this);
		backTop.setOnClickListener(this);

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
			case R.id.backTop:
				mRecyclerView.scrollToPosition(0);
				backTop.setVisibility(View.GONE);
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


	@Override
	public void onBannerListener(BannerModel model,int index) {
		String str=model.getListShopId().get(index);
		UtilToast.showLong(getActivity(),   str + "," + index);
	}


	@Override
	public void onItemClick(View view, TypeMode typeMode) {
		String str="";
		Intent intent=null;
		switch (typeMode.getTypeParam()){

			case IndexTools.NAV:
				NavModel navModel= (NavModel)typeMode;
				str=navModel.getNavTitle();
				intent=new Intent(getActivity(),SupermarketActivity.class);
				intent.putExtra("mTitle",str);
				startActivity(intent);
				break;
			case IndexTools.KILLTITLE:
				intent=new Intent(getActivity(),SecKillActivity.class);
				startActivity(intent);
				break;
			case IndexTools.KILL:
				KillModel killModel= (KillModel)typeMode;
				str=killModel.getPrice();
				intent=new Intent(getActivity(),DescribeActivity.class);
				startActivity(intent);
				break;
			case IndexTools.SHOPTITLE:
				intent=new Intent(getActivity(),GoodsShopActivity.class);
				startActivity(intent);
				break;
			case IndexTools.GOODSSHOP:
				ShopModel model= (ShopModel)typeMode;
				str=model.getShopName();
				break;
			case IndexTools.NOTICE:
				str="更多";
				break;
			case IndexTools.LOVE_TWO:
			case IndexTools.LOVE_FOUR:
			case IndexTools.GOSHOPTWO:
			case IndexTools.GOSHOPFOUR:
				LoveModel loveModel= (LoveModel) typeMode;
				str=loveModel.getLoveTitle();
				// intent=new Intent(getActivity(),DescribeActivity.class);
				 intent=new Intent(getActivity(),ShopIndexActivity.class);
				startActivity(intent);
				break;
			case IndexTools.Recommend:
				RecommendModel remodel= (RecommendModel) typeMode;

				if(view instanceof TextView){
					str=remodel.getPrice()+"找相似";
				}else {
					intent=new Intent(getActivity(),GoodsDetailActivity.class);
					startActivity(intent);
				}

				break;
			default:
				break ;

		}
		Toast.makeText(getActivity(),"你点击了"+str,Toast.LENGTH_SHORT).show();
	}
}
