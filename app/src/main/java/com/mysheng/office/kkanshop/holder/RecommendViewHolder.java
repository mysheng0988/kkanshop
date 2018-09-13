package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.RecommendModel;

public class RecommendViewHolder extends IndexAbstractViewHolder<RecommendModel> {
    public TextView goodsTitle;
    public TextView goodsPrice;
    public TextView resemble;
    public ImageView goodsPath;
    public RecommendViewHolder(View itemView) {
        super(itemView);
        goodsTitle=itemView.findViewById(R.id.goodsTitle);
        goodsPrice=itemView.findViewById(R.id.goodsPrice);
        resemble=itemView.findViewById(R.id.resemble);
        goodsPath=itemView.findViewById(R.id.goodsPath);
    }
    public void bindHolder(RecommendModel model){
        goodsTitle.setText(model.getGoodsTitle());
        goodsPrice.setText(model.getPrice());
        Glide.with(goodsPath.getContext()).load(model.getGoodsPath()).into(goodsPath);
    }
}
