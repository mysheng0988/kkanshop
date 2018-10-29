package com.mysheng.office.kkanshop.entity;
import java.util.List;

/**
 * Created by myaheng on 2018/9/19.
 */

public class EvaluateModel extends TypeMode  {
    private String userIcon;
    private String userName;
    private float score;
    private String comment;
    private List<String> goodsImgPath;
    private String goodsType;
    private String strData;

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getGoodsImgPath() {
        return goodsImgPath;
    }

    public void setGoodsImgPath(List<String> goodsImgPath) {
        this.goodsImgPath = goodsImgPath;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getStrData() {
        return strData;
    }

    public void setStrData(String strData) {
        this.strData = strData;
    }
}
