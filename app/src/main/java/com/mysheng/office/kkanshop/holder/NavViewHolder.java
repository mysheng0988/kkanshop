package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.RelativeLayout;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.NavModel;

public class NavViewHolder extends IndexAbstractViewHolder<NavModel> {
    public RelativeLayout index1;
    public RelativeLayout index2;
    public RelativeLayout index3;
    public RelativeLayout index4;
    public RelativeLayout index5;
    public RelativeLayout index6;
    public RelativeLayout index7;
    public RelativeLayout index8;
    public NavViewHolder(View itemView) {
        super(itemView);
        index1=itemView.findViewById(R.id.supermarket);
        index2=itemView.findViewById(R.id.trappings);
        index3=itemView.findViewById(R.id.travel);
        index4=itemView.findViewById(R.id.catering);
        index5=itemView.findViewById(R.id.fresh);
        index6=itemView.findViewById(R.id.delicatessen);
        index7=itemView.findViewById(R.id.grain_and_oil);
        index8=itemView.findViewById(R.id.voucher);
    }
    public void bindHolder(NavModel model){
    }
}
