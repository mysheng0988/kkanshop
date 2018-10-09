package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.OrderModel;

public class OrderContentViewHolder extends IndexAbstractViewHolder<OrderModel> {
    private LinearLayout onlyOne;
    private LinearLayout moreList;
    private TextView shopName;
    private TextView goodsName;
    private TextView goodsPrice;
    private ImageView goodsImage;
    private TextView goodsNum;
    private ImageView allGoods;
    private ImageView goodsImage1;
    private ImageView goodsImage2;
    private TextView sendType;
    private ImageView sendMore;
    private TextView remark;
    public OrderContentViewHolder(View itemView) {
        super(itemView);
        onlyOne=itemView.findViewById(R.id.onlyOne);
        moreList=itemView.findViewById(R.id.moreList);
        shopName=itemView.findViewById(R.id.shopName);
        goodsName=itemView.findViewById(R.id.goodsName);
        goodsPrice=itemView.findViewById(R.id.goodsPrice);
        goodsImage=itemView.findViewById(R.id.goodsImage);
        goodsNum=itemView.findViewById(R.id.goodsNum);
        allGoods=itemView.findViewById(R.id.allGoods);
        goodsImage1=itemView.findViewById(R.id.goodsImage1);
        goodsImage2=itemView.findViewById(R.id.goodsImage2);
        sendType=itemView.findViewById(R.id.sendType);
        sendMore=itemView.findViewById(R.id.sendMore);
        remark=itemView.findViewById(R.id.remark);
    }
    public void bindHolder(OrderModel model){
//        shopName.setText(model.getOrderShopModels().;
//        if(model.getGoodsModels().size()>1){
//            onlyOne.setVisibility(View.GONE);
//            moreList.setVisibility(View.VISIBLE);
//            goodsNum.setText("共"+model.getGoodsModels().size()+"件");
//            Glide.with(goodsImage1.getContext()).load(model.getGoodsModels().get(0).getGoodsPath().get(0)).into(goodsImage);
//            Glide.with(goodsImage2.getContext()).load(model.getGoodsModels().get(1).getGoodsPath().get(0)).into(goodsImage);
//        }else{
//            goodsName.setText(model.getGoodsModels().get(0).getGoodsName());
//            goodsPrice.setText(model.getGoodsModels().get(0).getGoodsPrice());
//            Glide.with(goodsImage.getContext()).load(model.getGoodsModels().get(0).getGoodsPath().get(0)).into(goodsImage);
//            onlyOne.setVisibility(View.VISIBLE);
//            moreList.setVisibility(View.GONE);
//        }
//        sendType.setText(model.getSendType());
//        remark.setText(model.getRemark());
    }
}
