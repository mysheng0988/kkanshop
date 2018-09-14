package com.mysheng.office.kkanshop;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;


import com.mysheng.office.kkanshop.banner.Banner;
import com.mysheng.office.kkanshop.banner.GlideImageLoader;
import com.mysheng.office.kkanshop.listenter.ChangePage;
import com.mysheng.office.kkanshop.page.Page;
import com.mysheng.office.kkanshop.page.PageBehavior;
import com.mysheng.office.kkanshop.page.PageContainer;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsDetailFragment extends Fragment{


    private WebView webview;
    private Banner banner;
    private ArrayList<String> list = new ArrayList<>();
    private Page pageOne;
    private ChangePage mChangePage;
    private PageContainer container;
    private Page.OnScrollListener onScrollListener;
    private ImageView goodsFollow;

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
        webview = (WebView) view.findViewById(R.id.webview);

        webview.loadUrl("https://github.com/ysnows");

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
