package com.mysheng.office.kkanshop.entity;

import java.util.List;

/**
 * Created by myaheng on 2018/9/10.
 */

public class BannerModel extends TypeMode {
    private List<String> imgPaths;
    private List<String> titles;
    public List<String> getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(List<String> imgPaths) {
        this.imgPaths = imgPaths;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
}
