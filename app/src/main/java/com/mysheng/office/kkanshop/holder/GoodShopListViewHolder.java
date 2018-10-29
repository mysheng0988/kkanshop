package com.mysheng.office.kkanshop.holder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ShopModel;

public class GoodShopListViewHolder extends CommonAbstractViewHolder<ShopModel> {
    private ImageView infoShop;
    private ImageView imagePath1;
    private ImageView imagePath2;
    private ImageView imagePath3;
    private TextView shopName;
    private ImageView locationMap;
    private TextView address;
    private TextView telPhone;

    public GoodShopListViewHolder(View itemView) {
        super(itemView);
        infoShop=itemView.findViewById(R.id.infoShop);
        imagePath1=itemView.findViewById(R.id.goodsPath1);
        imagePath2=itemView.findViewById(R.id.goodsPath2);
        imagePath3=itemView.findViewById(R.id.goodsPath3);
        locationMap=itemView.findViewById(R.id.locationMap);
        shopName=itemView.findViewById(R.id.shopName);
        address=itemView.findViewById(R.id.address);
        telPhone=itemView.findViewById(R.id.telPhone);

    }
    public void bindHolder(ShopModel model){
        Glide.with(imagePath1.getContext()).load(model.getImagePath1()).into(imagePath1);
        Glide.with(imagePath2.getContext()).load(model.getImagePath2()).into(imagePath2);
        Glide.with(imagePath3.getContext()).load(model.getImagePath3()).into(imagePath3);
        shopName.setText(model.getShopName());
//        address.setText(model.getAddress());
//        telPhone.setText(model.getTelPhone());
    }
}
