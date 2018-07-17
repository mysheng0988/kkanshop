package com.mysheng.office.kkanshop.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.holder.DeleteViewHolder;
import com.mysheng.office.kkanshop.view.SlideLayout;

import java.util.ArrayList;

/**
 * @Author: duke
 * @DateTime: 2017-01-03 22:24
 * @Description:
 */
public class ChatListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> lists = new ArrayList<>();
    public SlideLayout slideLayout;
    private OnDeleteViewClickListener listener;
    public ChatListViewAdapter(ArrayList<String> lists) {
        this.lists.clear();
        this.lists.addAll(lists);
    }

    @Override
    public DeleteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * 加载滑动布局item_root，其中已经包含了content和optinos布局
         */
        return new DeleteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_root, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DeleteViewHolder viewHolder= (DeleteViewHolder) holder;
        viewHolder.content.setText(lists.get(position));
        slideLayout= (SlideLayout) viewHolder.itemView;
        slideLayout.setOnChildClickListener(new SlideLayout.OnChildClickListener() {
            @Override
            public void onChildClick(View view) {
                if (listener != null) {
                    listener.onChildClick(view);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void setOnDeleteViewClickListener(OnDeleteViewClickListener listener) {
        this.listener = listener;
    }
    public interface OnDeleteViewClickListener {
        void onChildClick(View view);
    }
}