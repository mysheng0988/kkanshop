package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.EvaluateModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.SelectModel;

import com.mysheng.office.kkanshop.holder.EvaluateViewHolder;

import com.mysheng.office.kkanshop.holder.SelectViewHolder;

import com.mysheng.office.kkanshop.listenter.OnItemClickListener;
import com.mysheng.office.kkanshop.view.MessagePicturesLayout;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by myaheng on 2018/9/10.
 */

public class EvaluateAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;
    private MessagePicturesLayout.Callback mCallback;

    private List<Integer> types=new ArrayList<>();
    private Map<Integer,Integer> mPosition=new HashMap<>();//存储list的初始位置

    private List<SelectModel> selectModels=new ArrayList<>();
    private List<EvaluateModel> evaluateModels=new ArrayList<>();

    public EvaluateAdapter(Context context) {
        this.mLayoutInflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case IndexTools.SELECT:
               View bannerView=  mLayoutInflater.inflate(R.layout.item_select_layout,parent,false);
               return new SelectViewHolder(bannerView);
            case IndexTools.EVALUATE:
                View navView=  mLayoutInflater.inflate(R.layout.item_evaluate_layout,parent,false);
                return new EvaluateViewHolder(navView);


        }
        return null;
    }
    public void setSelectModels(List<SelectModel> selectModels){
        addListByType(IndexTools.SELECT,selectModels);
        this.selectModels.addAll(selectModels);

    }
    public void setEvaluateModels(List<EvaluateModel> evaluateModels){
        addListByType(IndexTools.EVALUATE,evaluateModels);
        this.evaluateModels.addAll(evaluateModels);

    }


    private void addListByType(int type,List list){
        mPosition.put(type,types.size());
        for(int i=0;i<list.size();i++){
            types.add(type);
        }
    }
    private void reListByType(int type,List list){
        for(int i=0;i<list.size();i++){
            types.add(type);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final int viewType=getItemViewType(position);

        final int realPosition=position-mPosition.get(viewType);
        Log.e("mys", "position: "+ position);
        Log.e("mys", "mPosition.get(viewType): "+ mPosition.get(viewType));
        Log.e("mys", "onBindViewHolder: "+ viewType);
        switch (viewType){
            case IndexTools.SELECT:
                ((SelectViewHolder)holder).bindHolder(selectModels.get(realPosition));
                break;
            case IndexTools.EVALUATE:
                ((EvaluateViewHolder)holder).bindHolder(evaluateModels.get(realPosition));
                ((EvaluateViewHolder)holder).msgImg.setCallback(mCallback);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return types.get(position);
    }

    @Override
    public int getItemCount() {
        return types.size();
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }
    public EvaluateAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
    }
}
