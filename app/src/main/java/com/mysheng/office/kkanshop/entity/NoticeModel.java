package com.mysheng.office.kkanshop.entity;

import java.util.List;

/**
 * Created by myaheng on 2018/9/10.
 */

public class NoticeModel {

    private List<String> textList;
    private int modelType= IndexTools.NOTICE;
    public int getModelType() {
        return modelType;
    }
    public List<String> getTextList() {
        return textList;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }


}
