package com.mysheng.office.kkanshop.entity;

public class KillModel {
    private  String imagePath;
    private String oldPrice;
    private  String price;

    private int modelType= IndexTools.KILL;
    public int getModelType() {
        return modelType;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
