package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.DescribeModel;
import com.mysheng.office.kkanshop.entity.ShopModel;
import com.mysheng.office.kkanshop.holder.DescribeViewHolder;
import com.mysheng.office.kkanshop.holder.NearbyViewHolder;
import com.mysheng.office.kkanshop.view.ResizableImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: duke
 * @DateTime: 2017-01-03 22:24
 * @Description:
 */
public class NearbyViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShopModel> lists = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private LayoutInflater mLayoutInflater;
    public NearbyViewAdapter(Context context) {
        lists.clear();
        mLayoutInflater=LayoutInflater.from(context);

    }

    @Override
    public NearbyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * 加载滑动布局item_root，其中已经包含了content和optinos布局
         */
        View view=mLayoutInflater.inflate(R.layout.item_nearby_layout, parent, false);
        return new NearbyViewHolder(view);
    }
    public void addList(List<ShopModel> list){
        lists.clear();
        lists.addAll(list);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        NearbyViewHolder viewHolder= (NearbyViewHolder) holder;
        Log.e("DescribeViewHolder", "onBindViewHolder: "+position );
        viewHolder.bindHolder(lists.get(position));
    }



    @Override
    public int getItemCount() {
        return lists.size();
    }
    public List<ShopModel> getImagesList(){
        return lists;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}