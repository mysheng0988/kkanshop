package com.mysheng.office.kkanshop;


import android.content.Context;
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
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.adapter.DescribeViewAdapter;
import com.mysheng.office.kkanshop.adapter.GridImageViewAdapter;
import com.mysheng.office.kkanshop.banner.Banner;
import com.mysheng.office.kkanshop.banner.GlideImageLoader;
import com.mysheng.office.kkanshop.entity.DescribeModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.listenter.ChangePage;
import com.mysheng.office.kkanshop.page.Page;
import com.mysheng.office.kkanshop.page.PageBehavior;
import com.mysheng.office.kkanshop.page.PageContainer;
import com.mysheng.office.kkanshop.util.Tools;
import com.mysheng.office.kkanshop.view.GridImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsDetailFragment extends Fragment{


    private Banner banner;
    private ArrayList<String> list = new ArrayList<>();
    private Page pageOne;
    private ChangePage mChangePage;
    private PageContainer container;
    private Page.OnScrollListener onScrollListener;
    private ImageView goodsFollow;
    private GridImageView gridImageView;
    private RecyclerView describeView;
    private DescribeViewAdapter mAdapter;
    private List<DescribeModel> modelslist=new ArrayList<>();


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
        goodsFollow =  view.findViewById(R.id.goodsFollow);
        container = (PageContainer) view.findViewById(R.id.container);
        banner.setImages(list).setImageLoader(new GlideImageLoader()).start();
        gridImageView=view.findViewById(R.id.gridImageView);
        describeView=view.findViewById(R.id.describe_view);

        gridImageView.setAdapter(new GridImageViewAdapter<String>() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String path) {
                if(!Tools.isNetUri(path)){
                    path="file://"+path;
                }
                Glide.with(context).load(path).centerCrop().override(400, 400).into(imageView);
            }

            @Override
            public void onAddClick(Context context, List<String> list) {
               // show();
            }

            @Override
            public int getShowStyle() {
                return GridImageView.STYLE_GRID;
            }

            @Override
            public void onItemImageClick(ImageView imageView,List<ImageView> imageViews,int index, List<String> list) {
                super.onItemImageClick(imageView,imageViews,index, list);
//                vImageWatcher.show(imageView,imageViews,list);
//                Toast.makeText(getApplicationContext(), "--->" + index, Toast.LENGTH_SHORT).show();
            }
        });
        gridImageView.setImageData(list,false);
        if(mAdapter==null){
            mAdapter=new DescribeViewAdapter(getActivity());
        }else{
            mAdapter.notifyDataSetChanged();
        }

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
                mAdapter.notifyDataSetChanged();

            }
        });
        goodsFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChangePage.showTabPage(3);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mChangePage = (ChangePage) getActivity();
    }

}
