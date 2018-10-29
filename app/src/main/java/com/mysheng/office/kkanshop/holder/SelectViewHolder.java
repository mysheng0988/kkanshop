package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.TextView;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.SelectModel;
import com.mysheng.office.kkanshop.view.LabelsView;

public class SelectViewHolder extends CommonAbstractViewHolder<SelectModel> {
    public TextView praise;
    public LabelsView labelsView;

    public SelectViewHolder(View itemView) {
        super(itemView);
        praise=itemView.findViewById(R.id.praise);
        labelsView=itemView.findViewById(R.id.labelsView);


    }
    public void bindHolder(SelectModel model){
        praise.setText("好评率:"+model.getPraise()+"%");
        labelsView.setLabels(model.getSelectitem());

    }
}
