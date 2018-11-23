package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/11/23.
 */

public class VoucherModel extends TypeModel {
    private String voucherId;
    private String shopName;
    private String reduce;
    private String limit;
    private String subhead;
    private String startDate;
    private String endDate;

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getReduce() {
        return reduce;
    }

    public void setReduce(String reduce) {
        this.reduce = reduce;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
