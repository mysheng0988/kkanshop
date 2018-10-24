package com.mysheng.office.kkanshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by myaheng on 2018/10/9.
 */

public class OrderShopModel implements Serializable {
    private String shopName;
    private List<GoodsModel> goodsModels;
    private String sendType;
    private String remark;
    private int statusCode;
    private String statusName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<GoodsModel> getGoodsModels() {
        return goodsModels;
    }

    public void setGoodsModels(List<GoodsModel> goodsModels) {
        this.goodsModels = goodsModels;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
