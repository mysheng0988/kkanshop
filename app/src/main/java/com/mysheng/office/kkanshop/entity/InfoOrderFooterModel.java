package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/10/10.
 */

public class InfoOrderFooterModel {
    private float totalPrice;
    private float orderFare;
    private String invoiceMessage;
    private String orderDiscount;
    private String payType;

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getOrderFare() {
        return orderFare;
    }

    public void setOrderFare(float orderFare) {
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
}
