package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.LabelModel;
import com.mysheng.office.kkanshop.entity.NavHeadModel;
import com.mysheng.office.kkanshop.entity.NavModel;
import com.mysheng.office.kkanshop.view.LabelsView;

public class NavHeadViewHolder extends IndexAbstractViewHolder<NavHeadModel> {
    public LabelsView labelsView;
    public TextView navTitle;

    public NavHeadViewHolder(View itemView) {
        super(itemView);
        labelsView=itemView.findViewById(R.id.labelsView);

    }
    public void bindHolder(NavHeadModel model){
        if(model.getLabelModels()!=null){
            labelsView.setLabels(model.getLabelModels(), new LabelsView.LabelTextProvider<LabelModel>() {
                @Override
                public CharSequence getLabelText(TextView label, int position, LabelModel data) {
                    return data.getLabelTitle();
                }
            });
            labelsView.setVisibility(View.VISIBLE);
        }else{
            labelsView.setVisibility(View.GONE);
        }
    }
}
