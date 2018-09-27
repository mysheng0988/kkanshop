package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mysheng.office.kkanshop.adapter.ResembleAdapter;
import com.mysheng.office.kkanshop.decoration.DividerGridItemDecoration;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.RecommendModel;
import com.mysheng.office.kkanshop.view.LabelsView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by myaheng on 2018/9/27.
 */

public class NavItemFragment extends Fragment{
    private int position;
    private static String PARAM="PARAM";
    private LabelsView labelsView;
    private RecyclerView navRecyclerView;
    private ResembleAdapter mAdapter;
    private List<RecommendModel> modelslist=new ArrayList<>();
    private List<String> labels= Arrays.asList("坚果炒货","糖果/巧克力","休闲零食","膨化食品","肉干肉铺","饼干蛋糕","低糖食品","蜜饯果干");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle=getArguments();
        if(bundle!=null){
            position=bundle.getInt(PARAM);
        }
        return inflater.inflate(R.layout.nav_common_layout, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        labelsView=view.findViewById(R.id.labelsView);
        if(position==0){
            labelsView.setVisibility(View.GONE);
        }else{
            labelsView.setLabels(labels);
            labelsView.setVisibility(View.VISIBLE);
        }

        navRecyclerView=view.findViewById(R.id.commonRecycler);
        if(mAdapter==null){
            mAdapter=new ResembleAdapter(KkanApplication.mContext);
        }else{
            mAdapter.notifyDataSetChanged();
        }
        modelslist.clear();
        for(int i = 0; i< IndexTools.Describe.length; i++){
            RecommendModel reModel=new RecommendModel();
            reModel.setGoodsPath(IndexTools.Describe[i]);
            reModel.setGoodsTitle(IndexTools.title);

            Random random=new Random();
            int num=random.nextInt(1000);
            int num2=random.nextInt(2)+1;
            int num3=random.nextInt(2)+1;
            reModel.setReduce(num2==1);
            reModel.setVoucher(num3==2);
            reModel.setPrice("￥:"+num+".00");
            modelslist.add(reModel);
        }
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        navRecyclerView.addItemDecoration(new DividerGridItemDecoration());
        navRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter.setData(modelslist);
        navRecyclerView.setAdapter(mAdapter);
    }




    public static NavItemFragment getInstance(int param){
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM,param);
        NavItemFragment fragment=new NavItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
