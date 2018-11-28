package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.adapter.ShopIndexAdapter;
import com.mysheng.office.kkanshop.decoration.DividerGridItemDecoration;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.RecommendModel;
import com.mysheng.office.kkanshop.entity.ShopIndexTitleModel;
import com.mysheng.office.kkanshop.entity.TypeModel;
import com.mysheng.office.kkanshop.entity.VoucherModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by myaheng on 2018/9/27.
 */

public class ShopIndexFragment extends Fragment{
    private int position;
    private static String PARAM="PARAM";
    private RecyclerView shopIndexView;
    private ShopIndexAdapter mAdapter;
    private List<TypeModel> modeList=new ArrayList<>();
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

        shopIndexView=view.findViewById(R.id.commonRecycler);
        modeList.clear();
        for (int i=0;i<6;i++){
            VoucherModel model=new VoucherModel();
            model.setTypeParam(0);
            model.setShopName("小米手机旗舰店");
            Random random=new Random();
            int reduce=random.nextInt(20)*10;
            model.setReduce(reduce);
            model.setEndDate("2018-11-30");
            model.setLimit("满"+(reduce*10)+"减"+reduce+"(限部分商品)");
            model.setStatus(random.nextInt(3));
            modeList.add(model);
        }
        for(int i = 0; i< IndexTools.shopIndex.length; i++){
            RecommendModel reModel=new RecommendModel();
            if(i%6==0){
                ShopIndexTitleModel titleModel=new ShopIndexTitleModel();
                titleModel.setTitle("新品券上市");
                titleModel.setTypeParam(1);
                modeList.add(titleModel);
            }
            reModel.setGoodsPath(IndexTools.shopIndex[i]);
            reModel.setGoodsTitle(IndexTools.title);
            reModel.setTypeParam(2);
            Random random=new Random();
            int num=random.nextInt(1000);
            int num2=random.nextInt(2)+1;
            int num3=random.nextInt(2)+1;
            reModel.setReduce(num2==1);
            reModel.setVoucher(num3==2);
            reModel.setPrice("￥:"+num+".00");
            modeList.add(reModel);
        }

        if(mAdapter==null){
            mAdapter=new ShopIndexAdapter(KkanApplication.mContext,modeList);
        }else{
            mAdapter.notifyDataSetChanged();
        }
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),6);
        shopIndexView.addItemDecoration(new DividerGridItemDecoration());
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType=shopIndexView.getAdapter().getItemViewType(position);
                switch (viewType){
                    case 0:
                       return 2;
                    case 1:
                        return 6;
                    case 2:
                        return 3;
                }
                return 6;
            }
        });
        shopIndexView.setLayoutManager(gridLayoutManager);


        shopIndexView.setAdapter(mAdapter);
    }




    public static ShopIndexFragment getInstance(int param){
        Bundle bundle=new Bundle();
        bundle.putInt(PARAM,param);
        ShopIndexFragment fragment=new ShopIndexFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


}
