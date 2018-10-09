package com.mysheng.office.kkanshop.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by myaheng on 2018/9/26.
 */

public class GoodsModel implements Serializable {
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String shopId;
    private String shopName;
    private List<String> goodsPath;
    private int modelType=IndexTools.Recommend;
    private String goodsPrice;
    private String goodsOldPrice;
    private boolean isReduce;//是否满减
    private boolean isVoucher;//是否含优惠卷
    private String CreateDate;
    private String updateDate;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<String> getGoodsPath() {
        return goodsPath;
    }

    public void setGoodsPath(List<String> goodsPath) {
        this.goodsPath = goodsPath;
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsOldPrice() {
        return goodsOldPrice;
    }

    public void setGoodsOldPrice(String goodsOldPrice) {
        this.goodsOldPrice = goodsOldPrice;
    }

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

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
