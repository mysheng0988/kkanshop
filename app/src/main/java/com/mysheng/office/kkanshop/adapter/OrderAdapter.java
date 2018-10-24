package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.OrderShopModel;
import com.mysheng.office.kkanshop.holder.OrderContentViewHolder;
import com.mysheng.office.kkanshop.holder.OrderViewHolder;
import java.util.ArrayList;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private LayoutInflater mLayoutInflater;
    private List<OrderShopModel> mList=new ArrayList<>();
    private OnItemClickCallback mCallback;

    public OrderAdapter(Context context) {

        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= mLayoutInflater.inflate(R.layout.item_order_content, parent,false);
                return new OrderViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        OrderViewHolder viewHolder= (OrderViewHolder) holder;
        Log.e("DescribeViewHolder", "onBindViewHolder: "+position );
        viewHolder.bindHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




    public void setOrderData(List<OrderShopModel> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void setOnItemClickCallback(OnItemClickCallback clickCallback) {
        this.mCallback = clickCallback;
    }

    public interface OnItemClickCallback<T> {
        void onItemClick(View view, int type, T mode);
    }
}
