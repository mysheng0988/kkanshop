package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/9/12.
 */

public class GoTitleModel {
    private String goTitle;
    private int modelType= IndexTools.GOTitle;
    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public String getGoTitle() {
        return goTitle;
    }

    public void setGoTitle(String goTitle) {
        this.goTitle = goTitle;
    }
}
