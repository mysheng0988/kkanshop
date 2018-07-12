package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ChatModel;
import com.mysheng.office.kkanshop.util.UtilDate;

/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeTimeViewHolder extends TypeAbstractViewHolder{
    private TextView showTime;
    public TypeTimeViewHolder(View itemView) {
        super(itemView);
        showTime=itemView.findViewById(R.id.showTime);
    }
    @Override
    public void bindHolder(Object model){
        if(model instanceof ChatModel){
            ChatModel chatModel= (ChatModel) model;
            String strTime=UtilDate.showDate(chatModel.getMesDate(),"yyyy-MM-dd HH:mm:ss");
            showTime.setText(strTime);

        }
    }
}
