package com.mysheng.office.kkanshop.entity;



import java.io.Serializable;
import java.util.Date;

/**
 * Created by myaheng on 2018/5/11.
 * 1 左边文字，2 右边文字
 * 3 左边图片，4 右边图片
 * 5 左边语音 6 右边语音
 */

public class ChatModel implements Serializable {

    public int mesId;//消息Id
    public  int mesType;//消息类型
    public int mesTime;//消息时长
    public  String IconPath;//  头像地址
    public  String content;//消息内容
    public String contentPath;//消息内容地址
    public String videoPath;
    public String sendUserId;//发送人id
    public String sendUser;//发送人
    public String receiveUserId;//接收人id
    public String receiveUser;//接收人
    public String mesStatus;//消息状态
    public Date mesDate; //消息时间
    public String address;
    public String tabTitle;
    public double longitude;//经度
    public double latitude;//纬度

    public String locationPath;

    public int width;
    public int height;

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getLocationPath() {
        return locationPath;
    }

    public void setLocationPath(String locationPath) {
        this.locationPath = locationPath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getMesId() {
        return mesId;
    }

    public void setMesId(int mesId) {
        this.mesId = mesId;
    }

    public int getMesType() {
        return mesType;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
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
