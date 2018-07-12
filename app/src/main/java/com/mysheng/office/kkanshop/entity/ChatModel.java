package com.mysheng.office.kkanshop.entity;

import com.mysheng.office.kkanshop.R;

import java.util.Date;

/**
 * Created by myaheng on 2018/5/11.
 * 1 左边文字，2 右边文字
 * 3 左边图片，4 右边图片
 * 5 左边语音 6 右边语音
 */

public class ChatModel {
    public static final  int TYPE_ONE=1;
    public static final int TYPE_TWO=2;
    public static final int TYPE_THREE=3;
    public static final int TYPE_FOUR=4;
    public static final int TYPE_FIVE=5;
    public static final int TYPE_SIX=6;
    public static final int TYPE_TIME=7;
    public int mesId;//消息Id
    public  int mesType;//消息类型
    public int mesTime;//消息时长
    public  String IconPath;//  头像地址
    public  String content;//消息内容
    public String contentPath;//消息内容地址
    public String sendUser;//发送人
    public String receiveUser;//接收人
    public String mesStatus;//消息状态
    public Date mesDate; //消息时间

    public int getMesId() {
        return mesId;
    }

    public void setMesId(int mesId) {
        this.mesId = mesId;
    }

    public int getMesType() {
        return mesType;
    }

    public void setMesType(int mesType) {
        this.mesType = mesType;
    }

    public int getMesTime() {
        return mesTime;
    }

    public void setMesTime(int mesTime) {
        this.mesTime = mesTime;
    }

    public String getIconPath() {
        return IconPath;
    }

    public void setIconPath(String iconPath) {
        IconPath = iconPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getSendUser() {
        return sendUser;
    }

    public void setSendUser(String sendUser) {
        this.sendUser = sendUser;
    }

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getMesStatus() {
        return mesStatus;
    }

    public void setMesStatus(String mesStatus) {
        this.mesStatus = mesStatus;
    }

    public Date getMesDate() {
        return mesDate;
    }

    public void setMesDate(Date mesDate) {
        this.mesDate = mesDate;
    }
}
