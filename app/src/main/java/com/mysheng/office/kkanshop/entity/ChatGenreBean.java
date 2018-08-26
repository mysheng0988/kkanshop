package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/8/9.
 */

public class ChatGenreBean {
    private  int position;
    private int  genreImageId;
    private String genreName;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getGenreImageId() {
        return genreImageId;
    }

    public void setGenreImageId(int genreImageId) {
        this.genreImageId = genreImageId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}
