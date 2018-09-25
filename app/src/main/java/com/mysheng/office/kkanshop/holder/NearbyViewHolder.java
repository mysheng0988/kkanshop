package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.NavModel;
import com.mysheng.office.kkanshop.entity.ShopModel;

public class NearbyViewHolder extends IndexAbstractViewHolder<ShopModel> {
    public TextView shopName;
    public TextView telPhone;
    public ImageView mainPath;
    public TextView mainTitle;
    public TextView address;
    public TextView distance;

    public NearbyViewHolder(View itemView) {
        super(itemView);
        shopName=itemView.findViewById(R.id.shopName);
        mainPath=itemView.findViewById(R.id.mainPath);
        mainTitle=itemView.findViewById(R.id.mainTitle);
        telPhone=itemView.findViewById(R.id.telPhone);
        address=itemView.findViewById(R.id.address);
        distance=itemView.findViewById(R.id.distance);
    }
    public void bindHolder(ShopModel model){
        Glide.with(mainPath.getContext()).load(model.getMainPath()).into(mainPath);
        distance.setText("距您:"+model.getDistance()+"米");
    }
}
