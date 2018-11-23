package com.mysheng.office.kkanshop.entity;

import java.util.List;

/**
 * Created by myaheng on 2018/9/19.
 */

public class SelectModel extends TypeModel {
    private int selectId;
    private List<String> selectitem;
    private int praise;
    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }

    public List<String> getSelectitem() {
        return selectitem;
    }

    public void setSelectitem(List<String> selectitem) {
        this.selectitem = selectitem;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }
}
