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
import com.mysheng.office.kkanshop.entity.VoucherModel;
import com.mysheng.office.kkanshop.holder.CouponViewHolder;
import com.mysheng.office.kkanshop.holder.OrderViewHolder;
import com.mysheng.office.kkanshop.holder.VoucherViewHolder;

import java.util.ArrayList;
import java.util.List;


public class VoucherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private LayoutInflater mLayoutInflater;
    private List<VoucherModel> mList=new ArrayList<>();
    private OnItemClickCallback mCallback;

    public VoucherAdapter(Context context,List<VoucherModel> list) {
        this.mList=list;
        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= mLayoutInflater.inflate(R.layout.item_voucher_layout, parent,false);
                return new VoucherViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        VoucherViewHolder viewHolder= (VoucherViewHolder) holder;
        viewHolder.bindHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }




    public void setVoucherData(List<VoucherModel> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void setOnItemClickCallback(OnItemClickCallback clickCallback) {
        this.mCallback = clickCallback;
    }

    public interface OnItemClickCallback{
        void onItemClick(View view,int position);
    }
}
