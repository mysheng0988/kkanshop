package com.mysheng.office.kkanshop.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/9/10.
 */

public class BannerModel extends TypeModel {

    private List<String> listPath=new ArrayList<>();
    private List<String> listTitle=new ArrayList<>();
    private List<String> listShopId=new ArrayList<>();
    private List<String> listGoodsId=new ArrayList<>();

    public List<String> getListPath() {
        return listPath;
    }

    public void setListPath(List<String> listPath) {
        this.listPath = listPath;
    }

    public List<String> getListTitle() {
        return listTitle;
    }

    public void setListTitle(List<String> listTitle) {
        this.listTitle = listTitle;
    }

    public List<String> getListShopId() {
        return listShopId;
    }

    public void setListShopId(List<String> listShopId) {
        this.listShopId = listShopId;
    }

    public List<String> getListGoodsId() {
        return listGoodsId;
    }

    public void setListGoodsId(List<String> listGoodsId) {
        this.listGoodsId = listGoodsId;
    }
}
