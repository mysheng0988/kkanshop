package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.RecommendModel;
import com.mysheng.office.kkanshop.entity.ShopIndexTitleModel;
import com.mysheng.office.kkanshop.entity.TypeModel;
import com.mysheng.office.kkanshop.holder.RecommendViewHolder;
import com.mysheng.office.kkanshop.holder.ShopIndexTitleViewHolder;
import com.mysheng.office.kkanshop.listenter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/9/10.
 */

/**
 * 评价适配器
 */
public class ShopIndexAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater mLayoutInflater;
    private OnItemClickListener<TypeModel> mOnItemClickListener;
    private List<TypeModel> mList=new ArrayList<>();
    public ShopIndexAdapter(Context context, List<TypeModel> lists) {
        this.mLayoutInflater=LayoutInflater.from(context);
        this.mList=lists;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
               View titleView=  mLayoutInflater.inflate(R.layout.item_shop_index_title,parent,false);
               return new ShopIndexTitleViewHolder(titleView);
            case 1:
                View contentView=  mLayoutInflater.inflate(R.layout.item_re_layout,parent,false);
                return new RecommendViewHolder(contentView);


        }
        return null;
    }




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int viewType=getItemViewType(position);
        switch (viewType){
            case 0:
                ShopIndexTitleModel titleModel= (ShopIndexTitleModel) mList.get(position);
                ((ShopIndexTitleViewHolder)holder).bindHolder(titleModel);
                break;
            case 1:
                RecommendModel recommendModel= (RecommendModel) mList.get(position);
                ((RecommendViewHolder)holder).bindHolder(recommendModel);
                break;
        }
    }

    @Override
    public int getItemViewType(int position){
        return mList.get(position).getTypeParam();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

}
