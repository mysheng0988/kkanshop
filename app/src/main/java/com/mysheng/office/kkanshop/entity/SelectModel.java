package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/9/19.
 */

public class SelectModel {
    private int selectId;
    private String selectName;
    private boolean isSelect=false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }
}
