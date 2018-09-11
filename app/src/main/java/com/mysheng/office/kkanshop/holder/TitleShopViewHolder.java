package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.TitleModel;
import com.mysheng.office.kkanshop.entity.TitleShopModel;

public class TitleShopViewHolder extends IndexAbstractViewHolder<TitleShopModel> {
    public TextView leftTitle;
    public TextView centerTitle;
    public LinearLayout moreList;
    public TitleShopViewHolder(View itemView) {
        super(itemView);
        leftTitle=itemView.findViewById(R.id.leftTitle);
        centerTitle=itemView.findViewById(R.id.centerTitle);
        moreList=itemView.findViewById(R.id.moreList);

    }
    public void bindHolder(TitleShopModel model){
        leftTitle.setText(model.getLeftTitle());
        if(model.getCenterTitle()==null){
            centerTitle.setVisibility(View.INVISIBLE);
        }else {
            centerTitle.setText(model.getCenterTitle());
            centerTitle.setVisibility(View.VISIBLE);
        }

    }
}
