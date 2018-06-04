package com.mysheng.office.kkanshop.entity;

import com.mysheng.office.kkanshop.R;

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
    public  int type;
    public float time;
    public  String IconPath;//  头像图片地址
    public  String content;
    public String contentPath;
}
