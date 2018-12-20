package com.mysheng.office.kkanshop.MIMC.bean;


/**
 * Created by muzi on 18-3-30.
 */

public class ChatMsg {
    private String fromAccount;
    private Msg msg;
    private Boolean isSingle;
    private String bizType;

    public ChatMsg() {}

    public ChatMsg(String fromAccount, Msg msg, Boolean isSingle,String bizType) {
        this.fromAccount = fromAccount;
        this.msg = msg;
        this.isSingle = isSingle;
        this.bizType=bizType;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }

    public Boolean getSingle() {
        return isSingle;
    }

    public void setSingle(Boolean single) {
        isSingle = single;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}
