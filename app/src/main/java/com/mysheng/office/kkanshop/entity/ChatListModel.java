package com.mysheng.office.kkanshop.entity;

import java.util.Date;

/**
 * Created by myaheng on 2018/7/20.
 */

public class ChatListModel {
    private String userId;
    private String userName;
    private String userIcon;
    private String lastMsg;
    private int unReadNum=0;
    private Date lastMsgData;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public int getUnReadNum() {
        return unReadNum;
    }

    public void setUnReadNum(int unReadNum) {
        this.unReadNum = unReadNum;
    }

    public Date getLastMsgData() {
        return lastMsgData;
    }

    public void setLastMsgData(Date lastMsgData) {
        this.lastMsgData = lastMsgData;
    }
}
