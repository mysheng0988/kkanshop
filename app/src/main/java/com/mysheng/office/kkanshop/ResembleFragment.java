package com.mysheng.office.kkanshop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mysheng.office.kkanshop.adapter.ResembleAdapter;
import com.mysheng.office.kkanshop.decoration.DividerGridItemDecoration;

import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.RecommendModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 相似商品
 */
public class ResembleFragment extends Fragment {

    private RecyclerView resembleView;
    private ResembleAdapter mAdapter;
    private List<RecommendModel> modelslist=new ArrayList<>();

    private static ResembleFragment fragment = null;

    public static ResembleFragment newInstance() {
        if (fragment == null) {
            fragment = new ResembleFragment();
        }
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_recycler_view, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resembleView=view.findViewById(R.id.commonRecycler);

        if(mAdapter==null){
            mAdapter=new ResembleAdapter(KkanApplication.mContext);
        }else{
            mAdapter.notifyDataSetChanged();
        }
        modelslist.clear();
        for(int i = 0; i< IndexTools.Describe.length; i++){
            RecommendModel reModel=new RecommendModel();
            reModel.setGoodsPath(IndexTools.list[i]);
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
        resembleView.addItemDecoration(new DividerGridItemDecoration());
        resembleView.setLayoutManager(gridLayoutManager);
        mAdapter.setData(modelslist);
        resembleView.setAdapter(mAdapter);


    }
}
