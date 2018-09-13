package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.NavModel;

public class NavViewHolder extends IndexAbstractViewHolder<NavModel> {
    public ImageView navIcon;
    public TextView navTitle;

    public NavViewHolder(View itemView) {
        super(itemView);
        navIcon=itemView.findViewById(R.id.navIcon);
        navTitle=itemView.findViewById(R.id.navTitle);
    }
    public void bindHolder(NavModel model){
        navIcon.setImageResource(model.getNavIcon());
        navTitle.setText(model.getNavTitle());
    }
}
