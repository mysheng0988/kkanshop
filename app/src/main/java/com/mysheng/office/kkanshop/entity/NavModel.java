package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/9/7.
 */

public class NavModel extends TypeMode{
    private int navIcon;
    private String navTitle;
    private int modelParam;

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

    public int getModelParam() {
        return modelParam;
    }

    public void setModelParam(int modelParam) {
        this.modelParam = modelParam;
    }
}
