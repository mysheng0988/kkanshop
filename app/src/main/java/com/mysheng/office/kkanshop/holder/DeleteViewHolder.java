package com.mysheng.office.kkanshop.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.view.SlideLayout;

/**
 * Created by myaheng on 2018/7/17.
 */

public class DeleteViewHolder extends  RecyclerView.ViewHolder{

    public TextView content;


    public DeleteViewHolder(View itemView) {
        super(itemView);
        content = (TextView) itemView.findViewById(R.id.recycler_view_item_content);
    }

}
