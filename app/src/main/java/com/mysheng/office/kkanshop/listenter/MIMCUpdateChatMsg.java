package com.mysheng.office.kkanshop.listenter;

import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;

/**
 * Created by myaheng on 2018/12/18.
 */

public interface MIMCUpdateChatMsg {
    public void noticeNewMsg(ChatMsg chatMsg);
}
