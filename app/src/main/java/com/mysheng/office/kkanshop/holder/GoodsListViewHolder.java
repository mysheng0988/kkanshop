package com.mysheng.office.kkanshop.holder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.GoodsModel;


public class GoodsListViewHolder extends IndexAbstractViewHolder<GoodsModel>{
    private ImageView goodsImage;
    private TextView goodsName;
    private TextView goodsType;
    private TextView goodsPrice;
    private TextView goodsAmount;

    public GoodsListViewHolder(View itemView) {
        super(itemView);
        goodsImage=itemView.findViewById(R.id.goodsImage);
        goodsName=itemView.findViewById(R.id.goodsName);
        goodsType=itemView.findViewById(R.id.goodsType);
        goodsPrice=itemView.findViewById(R.id.goodsPrice);
        goodsAmount=itemView.findViewById(R.id.goodsAmount);

    }
    public void bindHolder(GoodsModel model){
        Glide.with(goodsImage.getContext()).load(model.getGoodsPath().get(0)).into(goodsImage);
        goodsName.setText(model.getGoodsName());
        goodsType.setText(model.getGoodsType());
        goodsPrice.setText(String.format("￥:%s", model.getGoodsPrice()));
        goodsAmount.setText(String.format("%d", model.getGoodsAmount()));
    }
}
