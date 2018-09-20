package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.NavModel;
import com.mysheng.office.kkanshop.holder.NavViewHolder;

import java.util.ArrayList;
import java.util.List;


public class NavAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private LayoutInflater mLayoutInflater;
    private List<NavModel> mList=new ArrayList<>();
    private OnItemClickCallback mCallback;

    public NavAdapter(Context context) {

        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public NavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= mLayoutInflater.inflate(R.layout.item_nav_copy, parent,false);
        return new NavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        NavViewHolder viewHolder= (NavViewHolder) holder;
       viewHolder.bindHolder(mList.get(position));
       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mCallback!=null)
               mCallback.onItemClick(v,position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setData(List<NavModel> list) {
        mList.addAll(list);
    }
    public void setOnItemClickCallback(OnItemClickCallback clickCallback) {
        this.mCallback = clickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClick(View view, int position);
    }
}