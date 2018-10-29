package com.mysheng.office.kkanshop.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by myaheng on 2018/5/11.
 */

public abstract class CommonAbstractViewHolder<T>extends RecyclerView.ViewHolder{
    public CommonAbstractViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindHolder(T model);

}
