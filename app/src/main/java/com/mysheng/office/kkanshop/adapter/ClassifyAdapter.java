package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.DataModel;
import com.mysheng.office.kkanshop.holder.TypeAbstractViewHolder;
import com.mysheng.office.kkanshop.holder.TypeOneViewHolder;
import com.mysheng.office.kkanshop.holder.TypeThreeViewHolder;
import com.mysheng.office.kkanshop.holder.TypeTwoViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/5/11.
 */

public class ClassifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private LayoutInflater mLayoutInflater;
    private List<DataModel> mList=new ArrayList<>();
    protected boolean isScrolling = true;
    public ClassifyAdapter(Context context) {
        mLayoutInflater=LayoutInflater.from(context);
    }
    private OnItemClickListener mItemClickListener;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case DataModel.TYPE_ONE:
//                 view=mLayoutInflater.inflate(R.layout.item_type_one,parent,false);
//                view.setOnClickListener(this);
            return new TypeOneViewHolder(mLayoutInflater.inflate(R.layout.item_type_one,parent,false));
            case DataModel.TYPE_TWO:
                view=mLayoutInflater.inflate(R.layout.item_type_two,parent,false);
                view.setOnClickListener(this);
                RecyclerView.ViewHolder viewHolder=new TypeTwoViewHolder(view);
                return viewHolder;
            case DataModel.TYPE_THREE:
                return new TypeThreeViewHolder(mLayoutInflater.inflate(R.layout.item_type_three,parent,false));
        }
        return null;
    }

    public void addList(List<DataModel> list){
        mList.addAll(list);
    }
    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      //  int viewType=getItemViewType(position);
        ((TypeAbstractViewHolder)holder).bindHolder(mList.get(position),isScrolling);
        //TypeTwoViewHolder viewHolder=((TypeTwoViewHolder) holder).bindHolder(mList.get(position));
        holder.itemView.setTag(position);

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }

    @Override
    public void onClick(View v) {
        if(mItemClickListener!=null){
            mItemClickListener.onItemClick(v,(Integer) v.getTag());
        }

    }
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
