package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/9/7.
 */

public class NavModel {
    private int navIcon;
    private String navTitle;

    private int modelType= IndexTools.NAV;

    public int getNavIcon() {
        return navIcon;
    }

    public void setNavIcon(int navIcon) {
        this.navIcon = navIcon;
    }

    public String getNavTitle() {
        return navTitle;
    }

    public void setNavTitle(String navTitle) {
        this.navTitle = navTitle;
    }

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }
}
