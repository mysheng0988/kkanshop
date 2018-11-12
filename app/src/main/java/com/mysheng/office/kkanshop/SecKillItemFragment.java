package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.adapter.SecKillAdapter;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.KillModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by myaheng on 2018/9/27.
 */

public class SecKillItemFragment extends Fragment{
    private int position;
    private static String PARAM="PARAM";
    private RecyclerView killRecyclerView;
    private SecKillAdapter mAdapter;
    private List<KillModel> killModels=new ArrayList<>();

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

        killRecyclerView=view.findViewById(R.id.commonRecycler);
        if(mAdapter==null){
            mAdapter=new SecKillAdapter(KkanApplication.mContext);
        }else{
            mAdapter.notifyDataSetChanged();
        }
        killModels.clear();
        for(int i=0;i<IndexTools.list.length;i++){
            KillModel model=new KillModel();
            model.setImagePath(IndexTools.list[i]);
            model.setGoodsTitle("一加手机6 8GB+128GB 月牙白 全面屏双摄游戏手机 全网通4G 双卡双待");
            Random random=new Random();
            int num=random.nextInt(1000);
            int num2=random.nextInt(1000);
            model.setOldPrice("￥"+Math.max(num,num2)+".00");
            model.setPrice("￥"+Math.min(num,num2)+".00");
            int num3=random.nextInt(10);
            int num4=random.nextInt(10);
            model.setAllNum(Math.max(num3,num4));
            model.setConfirmNum(Math.min(num3,num4));
            if(position==0){
                if (num3==num4){
                    model.setStatus(0);
                }else{
                    model.setStatus(1);
                }
            }else{
                model.setStatus(-1);
            }
            killModels.add(model);
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
//        navRecyclerView.addItemDecoration(new DividerGridItemDecoration());
        killRecyclerView.setLayoutManager(linearLayoutManager);

        mAdapter.setOnItemClickCallback(new SecKillAdapter.OnItemClickCallback() {
            @Override
            public void onItemClick(View view, KillModel model) {

            }
        });
        mAdapter.setData(killModels);
        killRecyclerView.setAdapter(mAdapter);
    }




    public static SecKillItemFragment getInstance(int param){
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM,param);
        SecKillItemFragment fragment=new SecKillItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
