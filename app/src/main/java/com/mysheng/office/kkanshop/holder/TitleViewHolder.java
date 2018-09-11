package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.TitleModel;

public class TitleViewHolder extends IndexAbstractViewHolder<TitleModel> {
    public TextView leftTitle;
    public TextView centerTitle;
    public LinearLayout moreList;
    public TitleViewHolder(View itemView) {
        super(itemView);
        leftTitle=itemView.findViewById(R.id.leftTitle);
        centerTitle=itemView.findViewById(R.id.centerTitle);
        moreList=itemView.findViewById(R.id.moreList);

    }
    public void bindHolder(TitleModel model){
        leftTitle.setText(model.getLeftTitle());
        if(model.getCenterTitle()==null){
            centerTitle.setVisibility(View.INVISIBLE);
        }else {
            centerTitle.setText(model.getCenterTitle());
            centerTitle.setVisibility(View.VISIBLE);
        }

    }
}
