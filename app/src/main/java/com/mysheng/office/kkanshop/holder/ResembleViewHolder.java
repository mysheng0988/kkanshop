package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.RecommendModel;

public class ResembleViewHolder extends IndexAbstractViewHolder<RecommendModel> {
    public TextView goodsTitle;
    public TextView goodsPrice;
    public TextView goodsReduce;
    public TextView goodsVoucher;
    public ImageView goodsPath;
    public ImageView infoCart;
    public ResembleViewHolder(View itemView) {
        super(itemView);
        goodsTitle=itemView.findViewById(R.id.goodsTitle);
        goodsPrice=itemView.findViewById(R.id.goodsPrice);
        goodsReduce=itemView.findViewById(R.id.goodsReduce);
        goodsVoucher=itemView.findViewById(R.id.goodsVoucher);
        goodsPath=itemView.findViewById(R.id.goodsPath);
        infoCart=itemView.findViewById(R.id.infoCart);
    }
    public void bindHolder(RecommendModel model){
        if (model.isReduce()){
            goodsReduce.setVisibility(View.VISIBLE);
        }else{
            goodsReduce.setVisibility(View.GONE);
        }
        if (model.isVoucher()){
            goodsVoucher.setVisibility(View.VISIBLE);
        }else{
            goodsVoucher.setVisibility(View.GONE);
        }
        goodsTitle.setText(model.getGoodsTitle());
        goodsPrice.setText(model.getPrice());
        Glide.with(goodsPath.getContext()).load(model.getGoodsPath()).into(goodsPath);
    }
}
