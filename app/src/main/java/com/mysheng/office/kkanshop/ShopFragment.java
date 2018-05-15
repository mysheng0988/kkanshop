package com.mysheng.office.kkanshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.mysheng.office.kkanshop.adapter.ClassifyAdapter;
import com.mysheng.office.kkanshop.entity.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/5/8.
 */

public class ShopFragment extends Fragment{
    public static final String TAG = "ShopFragment";
    private String str;
    private RecyclerView mRecyclerView;
    private ClassifyAdapter classifyAdapter;
    int color[]={android.R.color.holo_red_light,android.R.color.holo_blue_light,android.R.color.holo_orange_dark};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.shopfragment, null);
//        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
//        //得到数据
//        str = getArguments().getString(TAG);
//        tv_title.setText(str);
        str = getArguments().getString(TAG);
        mRecyclerView=view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        classifyAdapter=new ClassifyAdapter(getActivity());
        mRecyclerView.setAdapter(classifyAdapter);
        initData();
        return view;
    }

    private void initData() {
        List<DataModel> list=new ArrayList<>();
        for(int i=0;i<30;i++){
            int type= (int) ((Math.random()*3)+1);
            DataModel model=new DataModel();
            model.avatarColor=color[type-1];
            model.type=type;
            model.name=str+i;
            model.content="content"+i;
            model.contentColor=color[(type+1)%3];
            list.add(model);
        }
        classifyAdapter.addList(list);
        classifyAdapter.notifyDataSetChanged();
    }
}
