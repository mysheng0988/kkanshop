package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.MIMC.common.UserManager;
import com.mysheng.office.kkanshop.MIMC.constant.Constant;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ChatTools;
import com.mysheng.office.kkanshop.holder.BannerViewHolder;
import com.mysheng.office.kkanshop.holder.TypeAbstractViewHolder;
import com.mysheng.office.kkanshop.holder.TypeLeftImageViewHolder;
import com.mysheng.office.kkanshop.holder.TypeLeftAudioViewHolder;
import com.mysheng.office.kkanshop.holder.TypeLeftTextViewHolder;
import com.mysheng.office.kkanshop.holder.TypeRightImageViewHolder;
import com.mysheng.office.kkanshop.holder.TypeLocationViewHolder;
import com.mysheng.office.kkanshop.holder.TypeRightAudioViewHolder;
import com.mysheng.office.kkanshop.holder.TypeRightTextViewHolder;
import com.mysheng.office.kkanshop.holder.TypeRightVideoViewHolder;
import com.mysheng.office.kkanshop.holder.TypeTimeViewHolder;

import java.util.List;

/**
 * Created by myaheng on 2018/5/11.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ChatMsg> mList;
    private List<String> imagePath;
    private int mMinItemWidth;
    private int mMaxItemWidth;
    private OnItemClickListener mItemClickListener;
    public ChatAdapter(Context context,List<ChatMsg> chatMsgs,List<String> imagePath) {
        this.mContext=context;
        this.mList=chatMsgs;
        this.imagePath=imagePath;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mMaxItemWidth = (int) (outMetrics.widthPixels * 0.7f);
        mMinItemWidth = (int) (outMetrics.widthPixels * 0.15f);
        mLayoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case ChatTools.LEFT_TEXT:
            return new TypeLeftTextViewHolder(mLayoutInflater.inflate(R.layout.items_left_text,parent,false));
            case ChatTools.RIGHT_TEXT:
                View view1=mLayoutInflater.inflate(R.layout.items_right_text,parent,false);
                RecyclerView.ViewHolder viewHolder=new TypeRightTextViewHolder(view1);
                return viewHolder;
            case ChatTools.LEFT_IMAGE:
                return new TypeLeftImageViewHolder(mLayoutInflater.inflate(R.layout.items_left_image,parent,false));
            case ChatTools.RIGHT_IMAGE:
                View view4=mLayoutInflater.inflate(R.layout.items_right_image,parent,false);
                return new TypeRightImageViewHolder(view4);
            case ChatTools.LIFT_AUDIO:
                return new TypeLeftAudioViewHolder(mLayoutInflater.inflate(R.layout.items_left_recorder,parent,false));
            case ChatTools.RIGHT_AUDION:
                View view6=mLayoutInflater.inflate(R.layout.items_right_recorder,parent,false);
                RecyclerView.ViewHolder viewHolder6=new TypeRightAudioViewHolder(view6);
                return viewHolder6;
            case ChatTools.SEND_TIME:
                View view7=mLayoutInflater.inflate(R.layout.item_time_layout,parent,false);
                RecyclerView.ViewHolder viewHolder7=new TypeTimeViewHolder(view7);
                return viewHolder7;
            case ChatTools.RIGHT_LOCATION:
                View view8=mLayoutInflater.inflate(R.layout.items_right_location2,parent,false);
                RecyclerView.ViewHolder viewHolder8=new TypeLocationViewHolder(view8);
                return viewHolder8;
            case ChatTools.LEFT_LOCATION:
                View view11=mLayoutInflater.inflate(R.layout.items_left_location2,parent,false);
                RecyclerView.ViewHolder viewHolder11=new TypeLocationViewHolder(view11);
                return viewHolder11;

        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof TypeLeftAudioViewHolder ||holder instanceof TypeRightAudioViewHolder){
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) holder.itemView.findViewById(R.id.id_recorder_length).getLayoutParams();
            lp.width= (int) (mMinItemWidth + (mMaxItemWidth / 60f)*mList.get(position).getMsg().getMsgLongTime());
            Log.d("mys", "onBindViewHolder: "+lp.width);
            holder.itemView.findViewById(R.id.id_recorder_length).setLayoutParams(lp);
        }

       ((TypeAbstractViewHolder)holder).bindHolder(mList.get(position));
        setRecursionClick(holder.itemView,mList.get(position),imagePath);
        holder.itemView.setTag(position);
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (mList.get(position).getFromAccount().equals(UserManager.getInstance().getAccount())) {
            switch (mList.get(position).getMsg().getMsgType()){
                case Constant.MSG_TEXT:
                    return ChatTools.RIGHT_TEXT;
                case Constant.MSG_IMAGE:
                    return ChatTools.RIGHT_IMAGE;
                case Constant.MSG_AUDIO:
                    return ChatTools.LIFT_AUDIO;
                case Constant.MSG_VIDEO:
                    return ChatTools.RIAGHT_VIDEO;
                case Constant.MSG_LOCATION:
                    return ChatTools.RIGHT_LOCATION;
            }
        }else {
            switch (mList.get(position).getMsg().getMsgType()){
                case Constant.MSG_TEXT:
                    return ChatTools.LEFT_TEXT;
                case Constant.MSG_IMAGE:
                    return ChatTools.LEFT_IMAGE;
                case Constant.MSG_AUDIO:
                    return ChatTools.LIFT_AUDIO;
                case Constant.MSG_VIDEO:
                    return ChatTools.LEFT_VIDEO;
                case Constant.MSG_LOCATION:
                    return ChatTools.LEFT_LOCATION;
            }

        }
        return  ChatTools.SEND_TIME;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, ChatMsg model, List<String> lists);
    }
    //递归设置点击事件
    private void setRecursionClick(final View view, final ChatMsg model, final List<String> lists) {
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view,  model,lists);
                    }
                }
            });
            for (int i = 0; i < group.getChildCount(); i++) {
                setRecursionClick(group.getChildAt(i),model,lists);
            }
        } else {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, model,lists);
                    }
                }
            });
        }
    }
}
