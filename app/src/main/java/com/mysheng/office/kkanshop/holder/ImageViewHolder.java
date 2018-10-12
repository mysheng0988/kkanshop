package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.GoodsModel;
import com.mysheng.office.kkanshop.entity.NavModel;

public class ImageViewHolder extends IndexAbstractViewHolder<GoodsModel> {
    public ImageView goodsIcon;
    private TextView goodsAmount;
    public ImageViewHolder(View itemView) {
        super(itemView);
        goodsIcon=itemView.findViewById(R.id.goodsIcon);
        goodsAmount=itemView.findViewById(R.id.goodsAmount);

    }
    public void bindHolder(GoodsModel model){
        Glide.with(goodsIcon.getContext()).load(model.getGoodsPath().get(0)).into(goodsIcon);
        goodsAmount.setText(String.format("x %d", model.getGoodsAmount()));
    }
}
