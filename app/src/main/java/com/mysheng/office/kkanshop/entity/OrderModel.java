package com.mysheng.office.kkanshop.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by myaheng on 2018/10/9.
 */

public class OrderModel implements Serializable {
    private String orderId;
    private List<OrderShopModel> OrderShopModels;
    private String createDate;
    private String userAddress;
    private String telPhone;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public List<OrderShopModel> getOrderShopModels() {
        return OrderShopModels;
    }

    public void setOrderShopModels(List<OrderShopModel> orderShopModels) {
        OrderShopModels = orderShopModels;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }



}
