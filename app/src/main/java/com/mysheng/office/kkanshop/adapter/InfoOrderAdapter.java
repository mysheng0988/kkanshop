package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.InfoOrderFooterModel;
import com.mysheng.office.kkanshop.entity.InfoOrderShopModel;
import com.mysheng.office.kkanshop.holder.OrderContentViewHolder;
import com.mysheng.office.kkanshop.holder.OrderFooterViewHolder;
import java.util.ArrayList;
import java.util.List;


public class InfoOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public static final int ORDER_CONTENT = 0;
    public static final int ORDER_FOOTER = 1;
    private LayoutInflater mLayoutInflater;
    private List<InfoOrderShopModel> mList=new ArrayList<>();
    private List<InfoOrderFooterModel> mFooterModels=new ArrayList<>();
    private OnItemClickCallback mCallback;

    public InfoOrderAdapter(Context context) {

        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case ORDER_CONTENT:
                View view1= mLayoutInflater.inflate(R.layout.item_info_order_content, parent,false);
                return new OrderContentViewHolder(view1);
            case ORDER_FOOTER:
                View view2= mLayoutInflater.inflate(R.layout.item_info_order_footer, parent,false);
                return new OrderFooterViewHolder(view2);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final int viewType=getItemViewType(position);
        Log.d("viewType", "onBindViewHolder: "+viewType);
        switch (viewType){
            case ORDER_CONTENT:
                ((OrderContentViewHolder)holder).bindHolder(mList.get(position));
                break;
            case ORDER_FOOTER:
                ((OrderFooterViewHolder)holder).bindHolder(mFooterModels.get(0));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size()+mFooterModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mFooterModels.size()==0)return ORDER_CONTENT;
        if(position ==mList.size()) return ORDER_FOOTER;
        return ORDER_CONTENT;
    }



    public void setOrderContentData(List<InfoOrderShopModel> list) {
        mList.clear();
        mList.addAll(list);
    }
    public void setOrderFooterData(InfoOrderFooterModel footerModel) {
        mFooterModels.clear();
        mFooterModels.add(footerModel);
    }

    public void setOnItemClickCallback(OnItemClickCallback clickCallback) {
        this.mCallback = clickCallback;
    }

    public interface OnItemClickCallback<T> {
        void onItemClick(View view, T mode);
    }
}
