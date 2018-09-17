package com.mysheng.office.kkanshop;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.adapter.DescribeViewAdapter;

import com.mysheng.office.kkanshop.entity.DescribeModel;
import com.mysheng.office.kkanshop.entity.IndexTools;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentDetailFragment extends Fragment {

    private RecyclerView describeView;
    private DescribeViewAdapter mAdapter;
    private List<DescribeModel> modelslist=new ArrayList<>();
    public ContentDetailFragment() {
        // Required empty public constructor
    }


    private static ContentDetailFragment fragment = null;

    public static ContentDetailFragment newInstance() {
        if (fragment == null) {
            fragment = new ContentDetailFragment();
        }
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content_detail, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        describeView=view.findViewById(R.id.describe_view);

        if(mAdapter==null){
            mAdapter=new DescribeViewAdapter(getActivity());
        }else{
            mAdapter.notifyDataSetChanged();
        }
        for(int i = 0; i< IndexTools.Describe.length; i++){
            DescribeModel describeModel=new DescribeModel();
            describeModel.setImagePath(IndexTools.Describe[i]);
            modelslist.add(describeModel);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        describeView.setLayoutManager(linearLayoutManager);
//        describeView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Glide.with(getActivity()).resumeRequests();
//                }else {
//                    Glide.with(getActivity()).pauseRequests();
//                }
//            }
//        });
        describeView.setAdapter(mAdapter);
        mAdapter.addList(modelslist);
        mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
    }
}
