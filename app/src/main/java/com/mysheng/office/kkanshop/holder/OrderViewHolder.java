package com.mysheng.office.kkanshop.holder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.adapter.GoodsImageAdapter;
import com.mysheng.office.kkanshop.entity.GoodsModel;
import com.mysheng.office.kkanshop.entity.OrderShopModel;

import java.util.List;

public class OrderViewHolder extends CommonAbstractViewHolder<OrderShopModel> {
    private GoodsImageAdapter imageAdapter;
    public TextView shopName;
    public TextView orderStatus;
    public LinearLayout onlyOne;
    public ImageView goodsImage;
    public TextView goodsName;
    public TextView goodsType;
    public TextView goodsPrice;
    public TextView goodsAmount;
    public LinearLayout moreList;
    public RecyclerView moreListView;
    public TextView goodsNum;
    public TextView totalPrice;
    public TextView orderEvaluate;
    public TextView againBuy;
    public TextView confirmOrder;
    public TextView reminder;
    public TextView logisticsMsg;
    public TextView toPay;


    public OrderViewHolder(View itemView) {
        super(itemView);
        shopName=itemView.findViewById(R.id.shopName);
        orderStatus=itemView.findViewById(R.id.orderStatus);
        onlyOne=itemView.findViewById(R.id.onlyOne);
        goodsImage=itemView.findViewById(R.id.goodsImage);
        goodsName=itemView.findViewById(R.id.goodsName);
        goodsType=itemView.findViewById(R.id.goodsType);
        goodsPrice=itemView.findViewById(R.id.goodsPrice);
        goodsAmount=itemView.findViewById(R.id.goodsAmount);
        moreList=itemView.findViewById(R.id.moreList);
        moreListView=itemView.findViewById(R.id.moreListView);
        goodsNum=itemView.findViewById(R.id.goodsNum);
        totalPrice=itemView.findViewById(R.id.totalPrice);
        orderEvaluate=itemView.findViewById(R.id.orderEvaluate);
        againBuy=itemView.findViewById(R.id.againBuy);
        confirmOrder=itemView.findViewById(R.id.confirmOrder);
        reminder=itemView.findViewById(R.id.reminder);
        logisticsMsg=itemView.findViewById(R.id.logisticsMsg);
        toPay=itemView.findViewById(R.id.toPay);

    }
    public void bindHolder(OrderShopModel model){
        initFootButton(model.getStatusCode());
        orderStatus.setText(model.getStatusName());
        shopName.setText(model.getShopName());
        List<GoodsModel> goodsModels=model.getGoodsModels();
        int num=0;
        int price=0;
        for(int i=0;i<goodsModels.size();i++){
            num+=goodsModels.get(i).getGoodsAmount();
            price+=goodsModels.get(i).getGoodsPrice()*goodsModels.get(i).getGoodsAmount();
        }
        goodsNum.setText(String.format("共%d件", num));
        totalPrice.setText(String.format("￥:%s", price));
        if(goodsModels.size()>1){
            onlyOne.setVisibility(View.GONE);
            moreList.setVisibility(View.VISIBLE);
            if(imageAdapter==null){
                imageAdapter=new GoodsImageAdapter(moreListView.getContext());
            }else{
                imageAdapter.notifyDataSetChanged();
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(moreListView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            moreListView.setLayoutManager(linearLayoutManager);
            imageAdapter.setData(goodsModels);
            moreListView.setAdapter(imageAdapter);

        }else{
            goodsName.setText(goodsModels.get(0).getGoodsName());
            goodsPrice.setText(String.format("￥:%s", goodsModels.get(0).getGoodsPrice()));
            goodsAmount.setText(num+"");
            Glide.with(goodsImage.getContext()).load(goodsModels.get(0).getGoodsPath().get(0)).into(goodsImage);
            onlyOne.setVisibility(View.VISIBLE);
            moreList.setVisibility(View.GONE);
        }

    }
  private void   initFootButton(int num){
      switch (num){
          case 1://待付款
              orderEvaluate.setVisibility(View.GONE);
              againBuy.setVisibility(View.GONE);
              confirmOrder.setVisibility(View.GONE);
              reminder.setVisibility(View.GONE);
              logisticsMsg.setVisibility(View.GONE);
              toPay.setVisibility(View.VISIBLE);
              break;
          case 2://待收货
              orderEvaluate.setVisibility(View.GONE);
              againBuy.setVisibility(View.VISIBLE);
              confirmOrder.setVisibility(View.VISIBLE);
              reminder.setVisibility(View.VISIBLE);
              logisticsMsg.setVisibility(View.VISIBLE);
              toPay.setVisibility(View.GONE);
              break;
          case 3://已完成
              orderEvaluate.setVisibility(View.VISIBLE);
              againBuy.setVisibility(View.VISIBLE);
              confirmOrder.setVisibility(View.GONE);
              reminder.setVisibility(View.GONE);
              logisticsMsg.setVisibility(View.GONE);
              toPay.setVisibility(View.GONE);
              break;
          case 4://已取消
              orderEvaluate.setVisibility(View.GONE);
              againBuy.setVisibility(View.VISIBLE);
              confirmOrder.setVisibility(View.GONE);
              reminder.setVisibility(View.GONE);
              logisticsMsg.setVisibility(View.GONE);
              toPay.setVisibility(View.GONE);
              break;

      }
  }
}
