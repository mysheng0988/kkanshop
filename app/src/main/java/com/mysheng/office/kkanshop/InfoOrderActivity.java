package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.adapter.InfoOrderAdapter;
import com.mysheng.office.kkanshop.entity.GoodsModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.InfoOrderFooterModel;
import com.mysheng.office.kkanshop.entity.InfoOrderShopModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/10/9.
 */

public class InfoOrderActivity extends Activity implements View.OnClickListener{
    private RecyclerView orderView;
    private InfoOrderAdapter mAdapter;
    private ImageView comeBack;
    private ImageView addressMore;
    private TextView totalPrice;
    private List<InfoOrderShopModel> shopModels=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_order_layout);
        initView();
        initEvent();

    }



    private void initView() {

        orderView=findViewById(R.id.orderView);
        comeBack=findViewById(R.id.comeBack);
        addressMore=findViewById(R.id.addressMore);
        totalPrice=findViewById(R.id.totalPrice);
        if(mAdapter==null){
            mAdapter=new InfoOrderAdapter(this);
        }else{
            mAdapter.notifyDataSetChanged();
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderView.setLayoutManager(linearLayoutManager);
        InfoOrderShopModel shopModel=new InfoOrderShopModel();
        List<GoodsModel> goodsModels=new ArrayList<>();
        float totalPriceNum=0;
        for (int i=0;i<10;i++){
            GoodsModel goodsModel=new GoodsModel();
            goodsModel.setGoodsName(IndexTools.title);
            goodsModel.setGoodsPath(IndexTools.ONLYONE);
            goodsModel.setGoodsPrice(3299);
            goodsModel.setGoodsAmount(i+1);
            goodsModels.add(goodsModel);
            totalPriceNum+=goodsModel.getGoodsPrice()*goodsModel.getGoodsAmount();
        }
        Log.d("mys", "initView: "+totalPriceNum);
//
//        GoodsModel goodsModel11=new GoodsModel();
//        goodsModel11.setGoodsName(IndexTools.title);
//        goodsModel11.setGoodsPath(IndexTools.TWO);
//        goodsModel11.setGoodsPrice(3299);
//        goodsModel11.setGoodsAmount(2);
//        goodsModels.add(goodsModel11);
        shopModel.setSendType("店家配送");
        shopModel.setShopName("小米旗舰店");
        shopModel.setGoodsModels(goodsModels);
        shopModels.add(shopModel);
        InfoOrderShopModel shopModel2=new InfoOrderShopModel();
        List<GoodsModel> goodsModels2=new ArrayList<>();
        GoodsModel goodsModel2=new GoodsModel();
        goodsModel2.setGoodsName(IndexTools.title);
        goodsModel2.setGoodsPath(IndexTools.HUAWEI);
        goodsModel2.setGoodsPrice(4999);
        goodsModels2.add(goodsModel2);
        shopModel2.setSendType("店家配送");
        shopModel2.setShopName("华为旗舰店");
        shopModel2.setGoodsModels(goodsModels2);
        shopModels.add(shopModel2);

        InfoOrderShopModel shopModel3=new InfoOrderShopModel();
        List<GoodsModel> goodsModels3=new ArrayList<>();
        GoodsModel goodsModel3=new GoodsModel();
        goodsModel3.setGoodsName(IndexTools.title);
        goodsModel3.setGoodsPath(IndexTools.HUAWEI);
        goodsModel3.setGoodsPrice(4999);
        goodsModels3.add(goodsModel2);
        shopModel3.setSendType("店家配送");
        shopModel3.setShopName("华为专卖店");
        shopModel3.setGoodsModels(goodsModels3);
        shopModels.add(shopModel3);
        InfoOrderFooterModel footerModel=new InfoOrderFooterModel();
        footerModel.setInvoiceMessage("普通发票");
        footerModel.setOrderDiscount("无可用优惠");
        footerModel.setPayType("货到付款");
        footerModel.setTotalPrice(totalPriceNum+(goodsModel3.getGoodsPrice()*goodsModel3.getGoodsAmount())+(goodsModel2.getGoodsPrice()*goodsModel2.getGoodsAmount()));
        footerModel.setOrderFare(10);
        mAdapter.setOrderContentData(shopModels);
        mAdapter.setOrderFooterData(footerModel);
        orderView.setAdapter(mAdapter);
        float total=footerModel.getTotalPrice()+footerModel.getOrderFare();
        totalPrice.setText(String.format("￥:%s", total));
    }
    private void initEvent() {

        comeBack.setOnClickListener(this);
        addressMore.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comeBack:
                finish();
                break;
            case R.id.addressMore:
//                new ConfirmDialog(InfoOrderActivity.this, "您确定删除此信息？", new ConfirmDialog.OnCloseListener() {
//                    @Override
//                    public void onClick(Dialog dialog,boolean confirm) {
//                        if(confirm){
//                            Toast.makeText(InfoOrderActivity.this,"点击确定", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//                        }
//
//                    }
//                }).setTitle("提示").show();
                Intent intent=new Intent(InfoOrderActivity.this,EditAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null) {
                if (getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }


        return super.onTouchEvent(event);
    }


}