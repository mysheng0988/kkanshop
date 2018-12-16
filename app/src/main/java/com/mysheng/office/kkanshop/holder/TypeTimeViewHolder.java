package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.TextView;

import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ChatModel;
import com.mysheng.office.kkanshop.util.UtilDate;
import com.mysheng.office.kkanshop.util.UtilsDate;


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
    public void bindHolder(ChatMsg model){
        String strTime= UtilsDate.showDateBeforeTime(model.getMsg().getTimestamp());
        showTime.setText(strTime);
    }
}
