package com.mysheng.office.kkanshop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.adapter.DescribeViewAdapter;
import com.mysheng.office.kkanshop.adapter.EvaluateAdapter;
import com.mysheng.office.kkanshop.adapter.IndexAdapter;
import com.mysheng.office.kkanshop.adapter.ResembleAdapter;
import com.mysheng.office.kkanshop.decoration.DividerGridItemDecoration;
import com.mysheng.office.kkanshop.entity.DescribeModel;
import com.mysheng.office.kkanshop.entity.EvaluateModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.SelectModel;
import com.mysheng.office.kkanshop.entity.ShopModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 评论页面
 */
public class CommentFragment extends Fragment {


    public CommentFragment() {
        // Required empty public constructor
    }


    private static CommentFragment fragment = null;

    public static CommentFragment newInstance() {
        if (fragment == null) {
            fragment = new CommentFragment();
        }
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private EvaluateAdapter mAdapter;
    private List<SelectModel> selectModels=new ArrayList<>();
    private List<EvaluateModel> evaluateModels=new ArrayList<>();
    private List<String> images=new ArrayList<>();
    private String[] selectStr={"全部","好评","中评","差评"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.common_recycler_view, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView=view.findViewById(R.id.commonRecycler);
        if(mAdapter==null){
            mAdapter=new EvaluateAdapter(KkanApplication.mContext);
        }else{
            mAdapter.notifyDataSetChanged();
        }
        selectModels.clear();
        evaluateModels.clear();
        for (int i=0;i<selectStr.length;i++){
            SelectModel selectModel=new SelectModel();
            if(i==0){
                selectModel.setSelect(true);
            }
            selectModel.setSelectName(selectStr[i]);
            selectModels.add(selectModel);
        }
        for (int i=0;i<IndexTools.IMAGELIST.length;i++){

            images.add(IndexTools.IMAGELIST[i]);
        }
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),4);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration());
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType=mRecyclerView.getAdapter().getItemViewType(position);
                switch (viewType){
                    case IndexTools.SELECT:
                        return 1;
                    case IndexTools.LOVE_TWO:
                        return 4;
                    default:
                        return 4;
                }
            }
        });
        for (int i=0;i<30;i++){
            EvaluateModel model=new EvaluateModel();
            model.setComment("十分仲意性价比超高好喜欢哦 听说客服小姐姐才华横溢 请小姐姐作一首诗《吾爱莉莉》十分感谢哦爱你哦");
            model.setGoodsType("红色,128G");
            model.setGoodsImgPath(images);
            model.setUserName("15****555");
            model.setStrData("2018-10-10");
            Random random=new Random();
            float num=random.nextFloat()*5;
            model.setScore(num);
            evaluateModels.add(model);
        }
        mAdapter.setSelectModels(selectModels);
        mAdapter.setEvaluateModels(evaluateModels);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
