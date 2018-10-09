package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.OrderModel;
import com.mysheng.office.kkanshop.holder.OrderContentViewHolder;
import com.mysheng.office.kkanshop.holder.OrderFooterViewHolder;
import java.util.ArrayList;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public static final int ORDER_CONTENT = 0;
    public static final int ORDER_FOOTER = 1;
    private LayoutInflater mLayoutInflater;
    private List<OrderModel> mList=new ArrayList<>();
    private OnItemClickCallback mCallback;

    public OrderAdapter(Context context) {

        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case ORDER_CONTENT:
                View view1= mLayoutInflater.inflate(R.layout.item_order_content, parent,false);
                return new OrderContentViewHolder(view1);
            case ORDER_FOOTER:
                View view2= mLayoutInflater.inflate(R.layout.item_order_footer, parent,false);
                return new OrderFooterViewHolder(view2);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final int viewType=getItemViewType(position);
        switch (viewType){
            case ORDER_CONTENT:

                break;
            case ORDER_FOOTER:

                break;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position ==mList.size()-1) return ORDER_FOOTER;
        return ORDER_CONTENT;
    }


    public void setData(List<OrderModel> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void setOnItemClickCallback(OnItemClickCallback clickCallback) {
        this.mCallback = clickCallback;
    }

    public interface OnItemClickCallback<T> {
        void onItemClick(View view, T mode);
    }
}
