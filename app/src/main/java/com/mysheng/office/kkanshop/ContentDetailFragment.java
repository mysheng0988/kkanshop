package com.mysheng.office.kkanshop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return inflater.inflate(R.layout.common_recycler_view, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        describeView=view.findViewById(R.id.commonRecycler);

        if(mAdapter==null){
            mAdapter=new DescribeViewAdapter(getActivity());
        }else{
            mAdapter.notifyDataSetChanged();
        }
        modelslist.clear();
        for(int i = 0; i< IndexTools.Describe.length; i++){
            DescribeModel describeModel=new DescribeModel();
            describeModel.setImagePath(IndexTools.Describe[i]);
            modelslist.add(describeModel);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        describeView.setLayoutManager(linearLayoutManager);
        mAdapter.addList(modelslist);
        describeView.setAdapter(mAdapter);


    }
}
