package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.RecommendModel;

public class RecommendViewHolder extends CommonAbstractViewHolder<RecommendModel> {
    public TextView goodsTitle;
    public TextView goodsPrice;
    public TextView resemble;
    public TextView goodsReduce;
    public TextView goodsVoucher;
    public ImageView goodsPath;
    public RecommendViewHolder(View itemView) {
        super(itemView);
        goodsTitle=itemView.findViewById(R.id.goodsTitle);
        goodsPrice=itemView.findViewById(R.id.goodsPrice);
        resemble=itemView.findViewById(R.id.resemble);
        goodsReduce=itemView.findViewById(R.id.goodsReduce);
        goodsVoucher=itemView.findViewById(R.id.goodsVoucher);
        goodsPath=itemView.findViewById(R.id.goodsPath);
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
