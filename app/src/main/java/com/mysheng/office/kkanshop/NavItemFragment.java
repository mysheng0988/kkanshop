package com.mysheng.office.kkanshop;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mysheng.office.kkanshop.adapter.NavAdapter;
import com.mysheng.office.kkanshop.decoration.DividerGridItemDecoration;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.LabelModel;
import com.mysheng.office.kkanshop.entity.NavHeadModel;
import com.mysheng.office.kkanshop.entity.RecommendModel;
import com.mysheng.office.kkanshop.listenter.ChangeGoodsNum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by myaheng on 2018/9/27.
 */

public class NavItemFragment extends Fragment{
    private int position;
    private ChangeGoodsNum changeGoodsNum;
    private static String PARAM="PARAM";
    private RecyclerView navRecyclerView;
    private NavAdapter mAdapter;
    private List<RecommendModel> modelslist=new ArrayList<>();
    private List<LabelModel> labelModels=new ArrayList<>();
    private List<String> labels= Arrays.asList("坚果炒货","糖果/巧克力","休闲零食","膨化食品","肉干肉铺","饼干蛋糕","低糖食品","蜜饯果干");
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

        navRecyclerView=view.findViewById(R.id.commonRecycler);
        if(mAdapter==null){
            mAdapter=new NavAdapter(KkanApplication.mContext);
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
        labelModels.clear();
        NavHeadModel navHeadModel=new NavHeadModel();
        for (int i=0;i<labels.size();i++){
            LabelModel labelModel=new LabelModel();
            labelModel.setLabelId(i);
            labelModel.setLabelTitle(labels.get(i));
            labelModels.add(labelModel);
        }
        if(position!=0){
            navHeadModel.setLabelModels(labelModels);
        }

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        navRecyclerView.addItemDecoration(new DividerGridItemDecoration());
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {


            @Override
            public int getSpanSize(int position) {
                int viewType=navRecyclerView.getAdapter().getItemViewType(position);
                switch (viewType){
                    case 0:
                       return 2;
                    case 1:
                        return 1;
                }
                return 1;
            }
        });
        navRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter.setHeadData(navHeadModel);
        mAdapter.setNormalData(modelslist);
        mAdapter.setOnItemClickCallback(new NavAdapter.OnItemClickCallback() {
            @Override
            public void onItemClick(View view, Object mode) {
                if(mode instanceof RecommendModel){
                    changeGoodsNum.changeNun(view);
                }
            }
        });
        navRecyclerView.setAdapter(mAdapter);
    }




    public static NavItemFragment getInstance(int param){
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM,param);
        NavItemFragment fragment=new NavItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changeGoodsNum= (ChangeGoodsNum) getActivity();
    }
}
