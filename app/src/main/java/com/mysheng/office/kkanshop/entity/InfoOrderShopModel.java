package com.mysheng.office.kkanshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by myaheng on 2018/10/9.
 */

public class InfoOrderShopModel implements Serializable {
    private String shopName;
    private List<GoodsModel> goodsModels;
    private String sendType;
    private String remark;

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
}
