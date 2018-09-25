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
import com.mysheng.office.kkanshop.adapter.NearbyViewAdapter;
import com.mysheng.office.kkanshop.entity.DescribeModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.ShopModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class VpNearbyFragment extends Fragment {

    private RecyclerView nearbyView;
    private NearbyViewAdapter mAdapter;
    private List<ShopModel> modelslist=new ArrayList<>();
    public VpNearbyFragment() {
        // Required empty public constructor
    }


    private static VpNearbyFragment fragment = null;

    public static VpNearbyFragment newInstance() {
        if (fragment == null) {
            fragment = new VpNearbyFragment();
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
        nearbyView=view.findViewById(R.id.commonRecycler);

        if(mAdapter==null){
            mAdapter=new NearbyViewAdapter(getActivity());
        }else{
            mAdapter.notifyDataSetChanged();
        }
        modelslist.clear();
        for(int i = 0; i< IndexTools.nearby.size(); i++){
            Random random=new Random();
            int num=random.nextInt(10000);
            ShopModel shopModel=new ShopModel();
            shopModel.setMainPath(IndexTools.nearby.get(i));
            shopModel.setDistance(num+"");
            modelslist.add(shopModel);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        nearbyView.setLayoutManager(linearLayoutManager);
        mAdapter.addList(modelslist);
        nearbyView.setAdapter(mAdapter);
    }
}
