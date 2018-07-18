package com.mysheng.office.kkanshop.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;

/**
 * Created by myaheng on 2018/7/17.
 */

public class DeleteViewHolder extends  RecyclerView.ViewHolder{

    public TextView content;


    public DeleteViewHolder(View itemView) {
        super(itemView);
        content = itemView.findViewById(R.id.userName);
    }

}
