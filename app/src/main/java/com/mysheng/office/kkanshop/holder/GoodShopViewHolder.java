package com.mysheng.office.kkanshop.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ShopModel;

public class GoodShopViewHolder extends RecyclerView.ViewHolder {
    private ImageView imagePath1;
    private ImageView imagePath2;
    private ImageView imagePath3;
    private TextView shopName;

    public GoodShopViewHolder(View itemView) {
        super(itemView);
        imagePath1=itemView.findViewById(R.id.goodsPath1);
        imagePath2=itemView.findViewById(R.id.goodsPath2);
        imagePath3=itemView.findViewById(R.id.goodsPath3);
        shopName=itemView.findViewById(R.id.shopName);

    }
    public void bindHolder(ShopModel model){
        Glide.with(imagePath1.getContext()).load(model.getImagePath1()).into(imagePath1);
        Glide.with(imagePath2.getContext()).load(model.getImagePath2()).into(imagePath2);
        Glide.with(imagePath3.getContext()).load(model.getImagePath3()).into(imagePath3);
        shopName.setText(model.getShopName());
    }
}
