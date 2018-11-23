package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/9/12.
 */

public class RecommendModel  extends TypeModel {
    private String goodsTitle;
    private String goodsPath;
    private String price;
    private boolean isReduce;//是否满减
    private boolean isVoucher;//是否含优惠卷


    public boolean isReduce() {
        return isReduce;
    }

    public void setReduce(boolean reduce) {
        isReduce = reduce;
    }

    public boolean isVoucher() {
        return isVoucher;
    }

    public void setVoucher(boolean voucher) {
        isVoucher = voucher;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
