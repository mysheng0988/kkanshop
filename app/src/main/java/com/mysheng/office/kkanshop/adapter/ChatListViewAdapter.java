package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ChatListModel;
import com.mysheng.office.kkanshop.holder.ChatListViewHolder;
import com.mysheng.office.kkanshop.view.SlideLayout;

import java.util.ArrayList;

/**
 * @Author: duke
 * @DateTime: 2017-01-03 22:24
 * @Description:
 */
public class ChatListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatListModel> lists = new ArrayList<>();
    public SlideLayout slideLayout;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OnChatListViewClickListener listener;
    public ChatListViewAdapter(Context context,ArrayList<ChatListModel> lists) {
        this.mContext=context;
        mLayoutInflater=LayoutInflater.from(context);
        this.lists=lists;

    }

    @Override
    public ChatListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * 加载滑动布局item_root，其中已经包含了content和optinos布局
         */
        return new ChatListViewHolder(mLayoutInflater.inflate(R.layout.chat_item_root, parent, false));
    }

    public void removeData(int position) {
        lists.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, getItemCount());
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ChatListViewHolder viewHolder= (ChatListViewHolder) holder;
        viewHolder.bindHolder(lists.get(position));
        slideLayout= (SlideLayout) viewHolder.itemView;
        slideLayout.setOnChildClickListener(new SlideLayout.OnChildClickListener() {
            @Override
            public void onChildClick(View view) {
                if (listener != null) {
                    listener.onChildClick(view,position);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void setOnDeleteViewClickListener(OnChatListViewClickListener listener) {
        this.listener = listener;
    }
    public interface OnChatListViewClickListener {
        void onChildClick(View view, int position);
    }
}