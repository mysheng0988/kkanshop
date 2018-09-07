package com.mysheng.office.kkanshop.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.NavModel;

public class NavViewHolder extends RecyclerView.ViewHolder {
    private ImageView itemIcon;
    private TextView itemTitle;
    public NavViewHolder(View itemView) {
        super(itemView);
        itemIcon=itemView.findViewById(R.id.itemIcon);
        itemTitle=itemView.findViewById(R.id.itemTitle);
    }
    public void bindHolder(NavModel model){
       // Glide.with(itemIcon.getContext()).load(model.getImagePath()).into(itemIcon);
        itemIcon.setImageResource(model.getItemIcon());
        itemTitle.setText(model.getItemTitle());
    }
}
