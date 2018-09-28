package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.LabelModel;
import com.mysheng.office.kkanshop.entity.NavHeadModel;
import com.mysheng.office.kkanshop.entity.NavModel;
import com.mysheng.office.kkanshop.entity.RecommendModel;
import com.mysheng.office.kkanshop.holder.BannerViewHolder;
import com.mysheng.office.kkanshop.holder.NavHeadViewHolder;
import com.mysheng.office.kkanshop.holder.NavViewHolder;
import com.mysheng.office.kkanshop.holder.ResembleViewHolder;

import java.util.ArrayList;
import java.util.List;


public class NavAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private LayoutInflater mLayoutInflater;
    private List<RecommendModel> mList=new ArrayList<>();
    private List<NavHeadModel> navHeadModels=new ArrayList<>();
    private OnItemClickCallback mCallback;

    public NavAdapter(Context context) {

        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_HEADER:
                View view1= mLayoutInflater.inflate(R.layout.nav_head_layout, parent,false);
                return new NavHeadViewHolder(view1);
            case TYPE_NORMAL:
                View view2= mLayoutInflater.inflate(R.layout.item_resemble_layout, parent,false);
                return new ResembleViewHolder(view2);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final int viewType=getItemViewType(position);
        final int realPosition=position-navHeadModels.size();
        switch (viewType){
            case TYPE_HEADER:
                ((NavHeadViewHolder)holder).bindHolder(navHeadModels.get(0));

                break;
            case TYPE_NORMAL:
                ((ResembleViewHolder)holder).bindHolder(mList.get(realPosition));
                ((ResembleViewHolder)holder).infoCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mCallback!=null){
                            mCallback.onItemClick(v,mList.get(realPosition));
                        }
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(navHeadModels == null)
            return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }


    public void setNormalData(List<RecommendModel> list) {
        mList.clear();
        mList.addAll(list);
    }
    public void setHeadData(NavHeadModel navHeadModel) {
        navHeadModels.clear();
        navHeadModels.add(navHeadModel);
    }
    public void setOnItemClickCallback(OnItemClickCallback clickCallback) {
        this.mCallback = clickCallback;
    }

    public interface OnItemClickCallback<T> {
        void onItemClick(View view, T mode );
    }
}
