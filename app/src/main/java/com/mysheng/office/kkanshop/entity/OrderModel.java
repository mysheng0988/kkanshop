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
    private String sendType;
    private String totalAmount;
    private String orderFare;
    private String invoiceMessage;
    private String orderDiscount;
    private String payType;
    private String remark;

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

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderFare() {
        return orderFare;
    }

    public void setOrderFare(String orderFare) {
        this.orderFare = orderFare;
    }

    public String getInvoiceMessage() {
        return invoiceMessage;
    }

    public void setInvoiceMessage(String invoiceMessage) {
        this.invoiceMessage = invoiceMessage;
    }

    public String getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(String orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
