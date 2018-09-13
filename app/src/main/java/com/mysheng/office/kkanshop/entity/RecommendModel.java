package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/9/12.
 */

public class RecommendModel {
    private String goodsTitle;
    private String goodsPath;
    private int modelType=IndexTools.Recommend;
    private String price;

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsPath() {
        return goodsPath;
    }

    public void setGoodsPath(String goodsPath) {
        this.goodsPath = goodsPath;
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
