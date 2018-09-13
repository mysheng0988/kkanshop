package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/9/11.
 */

public class TitleShopModel {
    private String leftTitle;
    private String centerTitle;
    private int modelType= IndexTools.SHOPTITLE;
    public int getModelType() {
        return modelType;
    }

    public String getLeftTitle() {
        return leftTitle;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle = leftTitle;
    }

    public String getCenterTitle() {
        return centerTitle;
    }

    public void setCenterTitle(String centerTitle) {
        this.centerTitle = centerTitle;
    }
}
