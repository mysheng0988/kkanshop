package com.mysheng.office.kkanshop.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.entity.DataModel;

/**
 * Created by myaheng on 2018/5/11.
 */

public abstract class TypeViewHolder extends RecyclerView.ViewHolder{
    public TypeViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindHolder(DataModel model);

}
