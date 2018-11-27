package com.mysheng.office.kkanshop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.adapter.OrderAdapter;
import com.mysheng.office.kkanshop.adapter.VoucherAdapter;
import com.mysheng.office.kkanshop.entity.GoodsModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.OrderShopModel;
import com.mysheng.office.kkanshop.entity.VoucherModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class VoucherFragment extends Fragment {
    private int position;
    private static String PARAM="PARAM";
    private RecyclerView orderView;
    private VoucherAdapter mAdapter;
    private List<VoucherModel> voucherModels=new ArrayList<>();
    public static VoucherFragment newInstance(int param) {
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM,param);
        VoucherFragment fragment=new VoucherFragment();
            fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
        if(bundle!=null){
            position=bundle.getInt(PARAM);
        }
        return inflater.inflate(R.layout.common_recycler_view, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderView=view.findViewById(R.id.commonRecycler);


        voucherModels.clear();


        for (int i=0;i<30;i++){
            VoucherModel model=new VoucherModel();
            model.setShopName("小米手机旗舰店");
            Random random=new Random();
            int reduce=random.nextInt(20)*10;
            model.setReduce(reduce);
            model.setStartDate("2018-11-01");
            model.setEndDate("2018-11-30");
            model.setLimit("满"+(reduce*10)+"减"+reduce+"(限部分商品)");
            model.setStatus(position);
            voucherModels.add(model);
        }

        if(mAdapter==null){
            mAdapter=new VoucherAdapter(getActivity(),voucherModels);
        }else{
            mAdapter.notifyDataSetChanged();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderView.setLayoutManager(linearLayoutManager);
        orderView.setAdapter(mAdapter);
    }
}
