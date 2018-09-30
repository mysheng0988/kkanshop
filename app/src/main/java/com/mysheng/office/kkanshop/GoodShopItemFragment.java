package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.adapter.GoodShopAdapter;
import com.mysheng.office.kkanshop.adapter.SecKillAdapter;
import com.mysheng.office.kkanshop.adapter.ShopAdapter;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.KillModel;
import com.mysheng.office.kkanshop.entity.ShopModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by myaheng on 2018/9/27.
 */

public class GoodShopItemFragment extends Fragment{
    private int position;
    private static String PARAM="PARAM";
    private RecyclerView shopRecyclerView;
    private GoodShopAdapter mAdapter;
    private List<ShopModel> shopModels=new ArrayList<>();
    private String[] shopName={"小米官方旗舰店","魅族官方旗舰店","华为小店","锤子小店","苹果专卖店"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        if(bundle!=null){
            position=bundle.getInt(PARAM);
        }
        return inflater.inflate(R.layout.common_recycler_view, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        shopRecyclerView=view.findViewById(R.id.commonRecycler);
        if(mAdapter==null){
            mAdapter=new GoodShopAdapter(KkanApplication.mContext);
        }else{
            mAdapter.notifyDataSetChanged();
        }
        shopModels.clear();
        for(int i=0;i<IndexTools.pictureList.size();i++){
            ShopModel shopModel=new ShopModel();
            Random random=new Random();
            int num=random.nextInt(5);
            shopModel.setShopName(shopName[num]);
            shopModel.setImagePath1(IndexTools.pictureList.get(i));
            i++;
            shopModel.setImagePath2(IndexTools.pictureList.get(i));
            i++;
            shopModel.setImagePath3(IndexTools.pictureList.get(i));
            shopModels.add(shopModel);
        }
        for(int i=0;i<IndexTools.list.length;i++){
            ShopModel shopModel=new ShopModel();
            Random random=new Random();
            int num=random.nextInt(5);
            shopModel.setShopName(shopName[num]);
            shopModel.setImagePath1(IndexTools.list[i]);
             i++;
            shopModel.setImagePath2(IndexTools.list[i]);
             i++;
            shopModel.setImagePath3(IndexTools.list[i]);
            shopModels.add(shopModel);
        }



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
//        navRecyclerView.addItemDecoration(new DividerGridItemDecoration());
        shopRecyclerView.setLayoutManager(linearLayoutManager);


        mAdapter.setData(shopModels);
        shopRecyclerView.setAdapter(mAdapter);
    }




    public static GoodShopItemFragment getInstance(int param){
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM,param);
        GoodShopItemFragment fragment=new GoodShopItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
