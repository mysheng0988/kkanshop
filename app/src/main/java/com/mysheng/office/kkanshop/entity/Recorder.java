package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/6/1.
 */

public class Recorder {
    public float time;
    public String filePath;
    public Recorder(float time,String filePath) {
        super();
        this.time = time;
        this.filePath = filePath;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public float getTime() {

        return time;
    }
    public String getFilePath() {
        return filePath;
    }
}
