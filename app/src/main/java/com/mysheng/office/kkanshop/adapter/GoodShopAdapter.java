package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ShopModel;
import com.mysheng.office.kkanshop.holder.GoodShopViewHolder;
import com.mysheng.office.kkanshop.holder.KillViewHolder;

import java.util.ArrayList;
import java.util.List;


public class GoodShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private LayoutInflater mLayoutInflater;
    private List<ShopModel> mList=new ArrayList<>();
    private OnItemClickCallback mCallback;

    public GoodShopAdapter(Context context) {
        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= mLayoutInflater.inflate(R.layout.item_good_shop_layout, parent,false);
        return new GoodShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        GoodShopViewHolder viewHolder= (GoodShopViewHolder) holder;
       viewHolder.bindHolder(mList.get(position));
       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mCallback!=null){
                   mCallback.onItemClick(position,v);
               }
           }
       });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<ShopModel> list) {
        mList.addAll(list);
    }
    public void setOnItemClickCallback(OnItemClickCallback clickCallback) {
        this.mCallback = clickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClick(int position, View view);
    }
}
