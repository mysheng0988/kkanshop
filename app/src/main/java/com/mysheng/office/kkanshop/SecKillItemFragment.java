package com.mysheng.office.kkanshop;

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
import com.mysheng.office.kkanshop.entity.RecommendModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/9/27.
 */

public class SecKillItemFragment extends Fragment{
    private int position;
    private static String PARAM="PARAM";
    private RecyclerView navRecyclerView;
    private NavAdapter mAdapter;
    private List<RecommendModel> modelslist=new ArrayList<>();

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



//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        navRecyclerView.addItemDecoration(new DividerGridItemDecoration());
        navRecyclerView.setLayoutManager(gridLayoutManager);

        mAdapter.setOnItemClickCallback(new NavAdapter.OnItemClickCallback() {
            @Override
            public void onItemClick(View view, Object mode) {

            }
        });
        navRecyclerView.setAdapter(mAdapter);
    }




    public static SecKillItemFragment getInstance(int param){
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM,param);
        SecKillItemFragment fragment=new SecKillItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
