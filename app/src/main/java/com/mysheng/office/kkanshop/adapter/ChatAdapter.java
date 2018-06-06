package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ChatModel;
import com.mysheng.office.kkanshop.entity.DataModel;
import com.mysheng.office.kkanshop.holder.TypeAbstractViewHolder;
import com.mysheng.office.kkanshop.holder.TypeLeftImageViewHolder;
import com.mysheng.office.kkanshop.holder.TypeLeftRecorderViewHolder;
import com.mysheng.office.kkanshop.holder.TypeLeftTextViewHolder;
import com.mysheng.office.kkanshop.holder.TypeRightImageViewHolder;
import com.mysheng.office.kkanshop.holder.TypeRightRecorderViewHolder;
import com.mysheng.office.kkanshop.holder.TypeRightTextViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/5/11.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private LayoutInflater mLayoutInflater;
    private List<ChatModel> mList=new ArrayList<>();
    private int mMinItemWidth;
    private int mMaxItemWidth;

    public ChatAdapter(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mMaxItemWidth = (int) (outMetrics.widthPixels * 0.7f);
        mMinItemWidth = (int) (outMetrics.widthPixels * 0.15f);
        mLayoutInflater=LayoutInflater.from(context);
    }
    private OnItemClickListener mItemClickListener;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){
            case ChatModel.TYPE_ONE:
            return new TypeLeftTextViewHolder(mLayoutInflater.inflate(R.layout.items_left_text,parent,false));
            case ChatModel.TYPE_TWO:
                View view1=mLayoutInflater.inflate(R.layout.items_right_text,parent,false);
              //  view.setOnClickListener(this);
                RecyclerView.ViewHolder viewHolder=new TypeRightTextViewHolder(view1);
                return viewHolder;
            case ChatModel.TYPE_THREE:
                return new TypeLeftImageViewHolder(mLayoutInflater.inflate(R.layout.items_left_image,parent,false));
            case ChatModel.TYPE_FOUR:
                return new TypeRightImageViewHolder(mLayoutInflater.inflate(R.layout.items_right_image,parent,false));
            case ChatModel.TYPE_FIVE:
                return new TypeLeftRecorderViewHolder(mLayoutInflater.inflate(R.layout.items_left_recorder,parent,false));
            case ChatModel.TYPE_SIX:
                View view6=mLayoutInflater.inflate(R.layout.items_right_recorder,parent,false);
                view6.setOnClickListener(this);
                RecyclerView.ViewHolder viewHolder6=new TypeRightRecorderViewHolder(view6);
                return viewHolder6;
        }
        return null;
    }

    public void addList(List<ChatModel> list){
        mList.addAll(list);
    }
    public void addModel(ChatModel model){
        mList.add(model);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TypeLeftRecorderViewHolder ||holder instanceof TypeRightRecorderViewHolder){
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) holder.itemView.findViewById(R.id.id_recorder_length).getLayoutParams();
            lp.width= (int) (mMinItemWidth + (mMaxItemWidth / 60f)*mList.get(position).time);
            Log.d("mys", "onBindViewHolder: "+lp.width);
            holder.itemView.findViewById(R.id.id_recorder_length).setLayoutParams(lp);
        }

       ((TypeAbstractViewHolder)holder).bindHolder(mList.get(position));

         //holder.itemView.findViewById(R.id.id_recorder_length);
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }

    @Override
    public void onClick(View v) {
        if(mItemClickListener!=null){
            mItemClickListener.onItemClick(v,mList.get((Integer) v.getTag()));
        }

    }
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, ChatModel model);
    }
}
