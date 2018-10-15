package com.mysheng.office.kkanshop;


import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.adapter.DescribeViewAdapter;
import com.mysheng.office.kkanshop.adapter.GridImageViewAdapter;
import com.mysheng.office.kkanshop.banner.Banner;
import com.mysheng.office.kkanshop.banner.GlideImageLoader;
import com.mysheng.office.kkanshop.entity.DescribeModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.LabelModel;
import com.mysheng.office.kkanshop.listenter.ChangePage;
import com.mysheng.office.kkanshop.page.Page;
import com.mysheng.office.kkanshop.page.PageBehavior;
import com.mysheng.office.kkanshop.page.PageContainer;
import com.mysheng.office.kkanshop.util.Tools;
import com.mysheng.office.kkanshop.view.GoodsParamDialog;
import com.mysheng.office.kkanshop.view.GridImageView;
import com.mysheng.office.kkanshop.view.MessagePicturesLayout;
import com.mysheng.office.kkanshop.view.ShoppingCartAnimationView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsDetailFragment extends Fragment implements View.OnClickListener{


    private Banner banner;
    private ArrayList<String> list = new ArrayList<>();
    private Page pageOne;
    private ChangePage mChangePage;
    private PageContainer container;
    private Page.OnScrollListener onScrollListener;
    private ImageView goodsFollow;
    private MessagePicturesLayout msgImg;
    private RecyclerView describeView;
    private DescribeViewAdapter mAdapter;
    private List<DescribeModel> modelslist=new ArrayList<>();
    private TextView infoCart;
    private TextView goodsNum;
    private RelativeLayout typeSelect;
    private String[] goodsColor={"红色","黄色","绿色","蓝色","白色","橘黄色","淡绿色"};
    private String[] goodsContent={"32G","64G","128G","256G"};
    public GoodsDetailFragment() {
        // Required empty public constructor
        //
    }

    private static GoodsDetailFragment fragment = null;

    public static GoodsDetailFragment newInstance() {
        if (fragment == null) {
            fragment = new GoodsDetailFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_goods_detail_with_webview, container, false);
        list.clear();
        list.add("http://img10.360buyimg.com/n1/s450x450_jfs/t13459/165/1849162356/71608/94425578/5a2a2ea3Nc30d9428.jpg");
        list.add("http://img10.360buyimg.com/n1/s450x450_jfs/t11845/73/694278454/68120/a4eb4468/59f69650Ndb06c709.jpg");
        list.add("http://img11.360buyimg.com/n1/s450x450_jfs/t11680/317/723006781/63418/f644d838/59f69653N15893d32.jpg");
        return inflater.inflate(R.layout.fragment_goods_detail, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        banner = (Banner) view.findViewById(R.id.banner);
        pageOne = (Page) view.findViewById(R.id.pageOne);
        typeSelect=view.findViewById(R.id.typeSelect);
        typeSelect.setOnClickListener(this);
        goodsFollow =  view.findViewById(R.id.goodsFollow);
        container = (PageContainer) view.findViewById(R.id.container);
        banner.setImages(list).setImageLoader(new GlideImageLoader()).isAutoPlay(false).start();
        describeView=view.findViewById(R.id.describe_view);
        infoCart=view.findViewById(R.id.infoCart);
        goodsNum=view.findViewById(R.id.goods_num);
        msgImg=view.findViewById(R.id.msgImg);
        msgImg.set(list,list);
        infoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAction(v);
            }
        });


        if(mAdapter==null){
            mAdapter=new DescribeViewAdapter(getActivity());
        }else{
            mAdapter.notifyDataSetChanged();
        }



        PageContainer pageContainer = (PageContainer) view.findViewById(R.id.container);

        pageContainer.setOnPageChanged(new PageBehavior.OnPageChanged(){

            @Override
            public void toTop() {
                //位于第一页

                mChangePage.showTabPage(1);
            }

            @Override
            public void toBottom() {
                //位于第二页
                mChangePage.showTabPage(2);
                Log.e("mys", "toBottom: "+ modelslist.size());
                Log.e("mys", "toBottom: "+ mAdapter.getItemCount());
                initDetailsData();
            }
        });
        goodsFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChangePage.showTabPage(3);
            }
        });
    }

    /**
     * 添加商品动画
     * @param view
     */
    public void addAction(View view) {
        ShoppingCartAnimationView shoppingCartAnimationView = new ShoppingCartAnimationView(getActivity());
        int position[] = new int[2];
        view.getLocationInWindow(position);
        int width=view.getWidth()/2;
        shoppingCartAnimationView.setStartPosition(new Point(position[0]+width, position[1]));
        ViewGroup rootView = (ViewGroup) getActivity().getWindow().getDecorView();
        rootView.addView(shoppingCartAnimationView);
        int endPosition[] = new int[2];
        goodsNum.getLocationInWindow(endPosition);
        shoppingCartAnimationView.setEndPosition(new Point(endPosition[0], endPosition[1]));
        shoppingCartAnimationView.startBeizerAnimation(goodsNum);
//


    }
    /**
     * 初始化详情页面数据
     */
    private void initDetailsData(){
        modelslist.clear();
        for(int i = 0; i< IndexTools.Describe.length; i++){
            DescribeModel describeModel=new DescribeModel();
            describeModel.setImagePath(IndexTools.Describe[i]);
            modelslist.add(describeModel);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        describeView.setHasFixedSize(true);
        describeView.setLayoutManager(linearLayoutManager);
        describeView.setNestedScrollingEnabled(false);
        mAdapter.addList(modelslist);
        describeView.setAdapter(mAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mChangePage = (ChangePage) getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.typeSelect:
                GoodsParamDialog paramDialog=new GoodsParamDialog(getActivity());
                paramDialog.show();
                break;
        }

    }
}
