package com.mysheng.office.kkanshop.MIMC.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.mysheng.office.kkanshop.MIMC.common.MsgHelper;


/**
 * Created by muzi on 18-3-30.
 */

public class Msg {
    @JSONField(ordinal = 1)
    private int version;

    @JSONField(ordinal = 2)
    private String msgId;

    @JSONField(ordinal = 3)
    private int msgType;

    @JSONField(ordinal = 4)
    private long timestamp;

    @JSONField(ordinal = 5)
    private byte[] content;

    @JSONField(ordinal = 6)
    private  String IconPath;//  头像地址

    @JSONField(ordinal = 7)
    private  String fromName;

    @JSONField(ordinal = 8)
    private String address;

    @JSONField(ordinal = 9)
    private String tabTitle;

    @JSONField(ordinal = 10)
    private double longitude;//经度

    @JSONField(ordinal = 11)
    private double latitude;//纬度

    @JSONField(ordinal = 12)
    private  int msgLongTime;

    @JSONField(ordinal = 13)
    private  String codeNum;

    @JSONField(ordinal = 14)
    private  boolean isTrue;

    @JSONField(ordinal = 15)
    private  String extra;

    @JSONField(ordinal = 16)
    private  int readStatus;


    public Msg() {}

    public Msg(int version, String msgId, int msgType, long timestamp, byte[] content) {
        this.version = version;
        this.msgId = msgId;
        this.msgType = msgType;
        this.timestamp = timestamp;
        this.content = content;
    }

    public int getMsgLongTime() {
        return msgLongTime;
    }

    public void setMsgLongTime(int msgLongTime) {
        this.msgLongTime = msgLongTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getMsgId() {
        if (msgId == null) {
            msgId = MsgHelper.nextID();
        }
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getIconPath() {
        return IconPath;
    }

    public void setIconPath(String iconPath) {
        IconPath = iconPath;
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

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(String codeNum) {
        this.codeNum = codeNum;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }
}
