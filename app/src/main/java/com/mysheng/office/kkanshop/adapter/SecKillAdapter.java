package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.KillModel;
import com.mysheng.office.kkanshop.holder.KillViewHolder;
import com.mysheng.office.kkanshop.holder.SecKillViewHolder;

import java.util.ArrayList;
import java.util.List;


public class SecKillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private LayoutInflater mLayoutInflater;
    private List<KillModel> mList=new ArrayList<>();
    private OnItemClickCallback mCallback;

    public SecKillAdapter(Context context) {
        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= mLayoutInflater.inflate(R.layout.item_seckill_layout, parent,false);
        return new SecKillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        SecKillViewHolder viewHolder= (SecKillViewHolder) holder;
       viewHolder.bindHolder(mList.get(position));
       viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(mCallback!=null)
               mCallback.onItemClick(v,mList.get(position));
           }
       });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public void setData(List<KillModel> list) {
        mList.clear();
        mList.addAll(list);
    }
    public void setOnItemClickCallback(OnItemClickCallback clickCallback) {
        this.mCallback = clickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClick(View view,KillModel model);
    }
}
