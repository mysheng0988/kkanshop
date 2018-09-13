package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/9/11.
 */

public class LoveModel {
    private String loveTitle;
    private String discountTitle;
    private String labelTitle;
    private String lovePath;
    private int modelType= IndexTools.LOVE_TWO;

    public String getLoveTitle() {
        return loveTitle;
    }

    public void setLoveTitle(String loveTitle) {
        this.loveTitle = loveTitle;
    }

    public String getDiscountTitle() {
        return discountTitle;
    }

    public void setDiscountTitle(String discountTitle) {
        this.discountTitle = discountTitle;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getLovePath() {
        return lovePath;
    }

    public void setLovePath(String lovePath) {
        this.lovePath = lovePath;
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }
}
