package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.EvaluateModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.SelectModel;
import com.mysheng.office.kkanshop.entity.TypeMode;
import com.mysheng.office.kkanshop.holder.EvaluateViewHolder;
import com.mysheng.office.kkanshop.holder.SelectViewHolder;
import com.mysheng.office.kkanshop.listenter.OnItemClickListener;
import com.mysheng.office.kkanshop.view.MessagePicturesLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/9/10.
 */

/**
 * 评价适配器
 */
public class CommentAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;
    private MessagePicturesLayout.Callback mCallback;
    private List<TypeMode> mList=new ArrayList<>();
    public CommentAdapter(Context context,List<TypeMode> lists) {
        this.mLayoutInflater=LayoutInflater.from(context);
        this.mList=lists;
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




    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final int viewType=getItemViewType(position);
        switch (viewType){
            case IndexTools.SELECT:
                SelectModel selectModel= (SelectModel) mList.get(position);
                ((SelectViewHolder)holder).bindHolder(selectModel);
                break;
            case IndexTools.EVALUATE:
                EvaluateModel evaluateModel= (EvaluateModel) mList.get(position);
                ((EvaluateViewHolder)holder).bindHolder(evaluateModel);
                ((EvaluateViewHolder)holder).msgImg.setCallback(mCallback);
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
    public CommentAdapter setPictureClickCallback(MessagePicturesLayout.Callback callback) {
        mCallback = callback;
        return this;
    }
}
