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

public class OrderContentViewHolder extends CommonAbstractViewHolder<OrderShopModel> {
    private LinearLayout onlyOne;
    private LinearLayout moreList;
    private GoodsImageAdapter imageAdapter;
    private TextView shopName;
    private TextView goodsName;
    private TextView goodsPrice;
    private ImageView goodsImage;
    private TextView goodsNum;
    private TextView goodsAmount;
    private TextView totalPrice;
    private RecyclerView moreListView;
    public ImageView listMore;
    private TextView sendType;
    private ImageView sendMore;
    private TextView remark;
    public ImageView explain;
    public ImageView explain2;
    public OrderContentViewHolder(View itemView) {
        super(itemView);
        onlyOne=itemView.findViewById(R.id.onlyOne);
        moreList=itemView.findViewById(R.id.moreList);
        shopName=itemView.findViewById(R.id.shopName);
        goodsName=itemView.findViewById(R.id.goodsName);
        goodsPrice=itemView.findViewById(R.id.goodsPrice);
        goodsImage=itemView.findViewById(R.id.goodsImage);
        goodsNum=itemView.findViewById(R.id.goodsNum);
        goodsAmount=itemView.findViewById(R.id.goodsAmount);
        totalPrice=itemView.findViewById(R.id.totalPrice);
        moreListView=itemView.findViewById(R.id.moreListView);
        sendType=itemView.findViewById(R.id.sendType);
        sendMore=itemView.findViewById(R.id.sendMore);
        remark=itemView.findViewById(R.id.remark);
        listMore=itemView.findViewById(R.id.listMore);
        explain=itemView.findViewById(R.id.explain);
        explain2=itemView.findViewById(R.id.explain2);
    }
    public void bindHolder(OrderShopModel model){
        shopName.setText(model.getShopName());
        List<GoodsModel> goodsModels=model.getGoodsModels();
        int num=0;
        int price=0;
        for(int i=0;i<goodsModels.size();i++){
            num+=goodsModels.get(i).getGoodsAmount();
            price+=goodsModels.get(i).getGoodsPrice()*goodsModels.get(i).getGoodsAmount();
        }
        if(goodsModels.size()>1){
            onlyOne.setVisibility(View.GONE);
            moreList.setVisibility(View.VISIBLE);
            goodsNum.setText(String.format("共%d件", num));
            totalPrice.setText(String.format("￥:%s", price));
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
}
