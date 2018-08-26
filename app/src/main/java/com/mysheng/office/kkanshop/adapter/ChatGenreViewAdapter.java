package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ChatGenreBean;
import com.mysheng.office.kkanshop.holder.ChatGenreViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: duke
 * @DateTime: 2017-01-03 22:24
 * @Description:
 */
public class ChatGenreViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ChatGenreBean> lists = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private LayoutInflater mLayoutInflater;
    public ChatGenreViewAdapter(Context context) {
        lists.clear();
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public ChatGenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * 加载滑动布局item_root，其中已经包含了content和optinos布局
         */
        View view=mLayoutInflater.inflate(R.layout.item_genre_layout, parent, false);
        return new ChatGenreViewHolder(view);
    }
    public void addList(List<ChatGenreBean> list){
        lists.addAll(list);
    }
    public void addModel(ChatGenreBean model){
        lists.add(model);
    }
    public void removeData(int position) {
        lists.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, getItemCount());
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ChatGenreViewHolder viewHolder= (ChatGenreViewHolder) holder;
        viewHolder.bindHolder(lists.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener!=null){
                    mItemClickListener.onItemClick(v,lists.get(position));
                }

            }
        });
    }



    @Override
    public int getItemCount() {
        return lists.size();
    }

    public void setItemClickListener(ChatGenreViewAdapter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, ChatGenreBean model);
    }
}