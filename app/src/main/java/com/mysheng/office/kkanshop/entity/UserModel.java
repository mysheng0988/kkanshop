package com.mysheng.office.kkanshop.entity;

/**
 * Created by myaheng on 2018/4/28.
 */

/**
 *  "UserId": 0,
 "UserName": "string",
 "Password": "string",
 "TrueName": "string",
 "Sex": "string",
 "Phone": "string",
 */
public class UserModel {

    private String UserId;
    private String UserName;
    private String Password;
    private String TrueName;
    private String Sex;
    private String Phone;
    private String SessionKey;

//    public UserModel(String userId, String userName, String password, String trueName, String sex, String phone) {
//        UserId = userId;
//        UserName = userName;
//        Password = password;
//        TrueName = trueName;
//        Sex = sex;
//        Phone = phone;
//    }

    public String getSessionKey() {
        return SessionKey;
    }

    public void setSessionKey(String sessionKey) {
        SessionKey = sessionKey;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getTrueName() {
        return TrueName;
    }

    public void setTrueName(String trueName) {
        TrueName = trueName;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
