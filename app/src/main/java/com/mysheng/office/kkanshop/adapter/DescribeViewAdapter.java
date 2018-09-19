package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;


import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.DescribeModel;
import com.mysheng.office.kkanshop.holder.DescribeViewHolder;
import com.mysheng.office.kkanshop.view.ResizableImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: duke
 * @DateTime: 2017-01-03 22:24
 * @Description:
 */
public class DescribeViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DescribeModel> lists = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private LayoutInflater mLayoutInflater;
    public DescribeViewAdapter(Context context) {
        lists.clear();
        mLayoutInflater=LayoutInflater.from(context);

    }

    @Override
    public DescribeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * 加载滑动布局item_root，其中已经包含了content和optinos布局
         */
        View view=mLayoutInflater.inflate(R.layout.item_describe_layout, parent, false);
        return new DescribeViewHolder(view);
    }
    public void addList(List<DescribeModel> list){
        lists.clear();
        lists.addAll(list);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final DescribeViewHolder viewHolder= (DescribeViewHolder) holder;
        Log.e("DescribeViewHolder", "onBindViewHolder: "+position );
        ResizableImageView describeImg=holder.itemView.findViewById(R.id.describe_img);
        viewHolder.bindHolder(lists.get(position));
        describeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener!=null){
                    mItemClickListener. onItemClick(v , position);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return lists.size();
    }
    public List<DescribeModel> getImagesList(){
        return lists;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}