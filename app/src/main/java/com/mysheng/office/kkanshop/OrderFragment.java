package com.mysheng.office.kkanshop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.adapter.NearbyViewAdapter;
import com.mysheng.office.kkanshop.adapter.OrderAdapter;
import com.mysheng.office.kkanshop.entity.GoodsModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.InfoOrderFooterModel;
import com.mysheng.office.kkanshop.entity.OrderShopModel;
import com.mysheng.office.kkanshop.entity.ShopModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    private int position;
    private static String PARAM="PARAM";
    private RecyclerView orderView;
    private OrderAdapter mAdapter;
    private List<OrderShopModel> shopModels=new ArrayList<>();

    private List<String> status= Arrays.asList("待支付","待支付","待收货","已完成","已取消");

    public static OrderFragment newInstance(int param) {
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM,param);
        OrderFragment fragment=new OrderFragment();
            fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
        if(bundle!=null){
            position=bundle.getInt(PARAM);
        }
        return inflater.inflate(R.layout.common_recycler_view, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderView=view.findViewById(R.id.commonRecycler);

        if(mAdapter==null){
            mAdapter=new OrderAdapter(getActivity());
        }else{
            mAdapter.notifyDataSetChanged();
        }
        shopModels.clear();
        OrderShopModel shopModel=new OrderShopModel();
        List<GoodsModel> goodsModels=new ArrayList<>();

        for (int i=0;i<10;i++){
            GoodsModel goodsModel=new GoodsModel();
            goodsModel.setGoodsName(IndexTools.title);
            goodsModel.setGoodsPath(IndexTools.ONLYONE);
            goodsModel.setGoodsType("红色 64G");
            goodsModel.setGoodsPrice(3299);
            goodsModel.setGoodsAmount(i+1);
            goodsModels.add(goodsModel);
        }
        shopModel.setSendType("店家配送");
        shopModel.setShopName("小米旗舰店");
        if(position==0){
            position=1;
        }
        shopModel.setStatusCode(position);
        shopModel.setStatusName(status.get(position));
        shopModel.setGoodsModels(goodsModels);
        shopModels.add(shopModel);
        OrderShopModel shopModel2=new OrderShopModel();
        List<GoodsModel> goodsModels2=new ArrayList<>();
        GoodsModel goodsModel2=new GoodsModel();
        goodsModel2.setGoodsName(IndexTools.title);
        goodsModel2.setGoodsPath(IndexTools.HUAWEI);
        goodsModel2.setGoodsPrice(4999);
        goodsModels2.add(goodsModel2);
        shopModel2.setSendType("店家配送");
        shopModel2.setShopName("华为旗舰店");
        shopModel2.setStatusCode(position);
        shopModel2.setStatusName(status.get(position));
        shopModel2.setGoodsModels(goodsModels2);
        shopModels.add(shopModel2);

        OrderShopModel shopModel3=new OrderShopModel();
        List<GoodsModel> goodsModels3=new ArrayList<>();
        GoodsModel goodsModel3=new GoodsModel();
        goodsModel3.setGoodsName(IndexTools.title);
        goodsModel3.setGoodsPath(IndexTools.HUAWEI);
        goodsModel3.setGoodsPrice(4999);
        goodsModels3.add(goodsModel2);
        shopModel3.setSendType("店家配送");
        shopModel3.setShopName("华为专卖店");
        shopModel3.setGoodsModels(goodsModels3);
        shopModel3.setStatusCode(position);
        shopModel3.setStatusName(status.get(position));
        shopModels.add(shopModel3);
        mAdapter.setOrderData(shopModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderView.setLayoutManager(linearLayoutManager);
        orderView.setAdapter(mAdapter);
    }
}
