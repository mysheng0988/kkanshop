package com.mysheng.office.kkanshop.entity;

import java.io.Serializable;

/**
 * Created by myaheng on 2018/9/6.
 */

public class DescribeModel implements Serializable {
    private String ImagePath;
    private String ImageParam;

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getImageParam() {
        return ImageParam;
    }

    public void setImageParam(String imageParam) {
        ImageParam = imageParam;
    }
}
