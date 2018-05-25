package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/5/11.
 */

public class ClassifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;

    private List<DataModel> mList=new ArrayList<>();

    public ClassifyAdapter(Context context) {
        mLayoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case DataModel.TYPE_ONE:

            return new TypeOneViewHolder(mLayoutInflater.inflate(R.layout.item_type_one,parent,false));
            case DataModel.TYPE_TWO:
                return new TypeTwoViewHolder(mLayoutInflater.inflate(R.layout.item_type_two,parent,false));
            case DataModel.TYPE_THREE:
                return new TypeThreeViewHolder(mLayoutInflater.inflate(R.layout.item_type_three,parent,false));
        }
        return null;
    }

    public void addList(List<DataModel> list){
        mList.addAll(list);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      //  int viewType=getItemViewType(position);
        ((TypeAbstractViewHolder)holder).bindHolder(mList.get(position));

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }
}
