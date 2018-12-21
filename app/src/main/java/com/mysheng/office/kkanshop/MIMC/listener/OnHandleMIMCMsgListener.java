package com.mysheng.office.kkanshop.MIMC.listener;

import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.xiaomi.mimc.MIMCGroupMessage;
import com.xiaomi.mimc.MIMCMessage;
import com.xiaomi.mimc.MIMCServerAck;
import com.xiaomi.mimc.common.MIMCConstant;

/**
 * Created by myaheng on 2018/12/21.
 */

public interface OnHandleMIMCMsgListener {
    void onHandleMessage(ChatMsg chatMsg);
    void onHandleGroupMessage(ChatMsg chatMsg);
    void onHandleStatusChanged(MIMCConstant.OnlineStatus status);
    void onHandleServerAck(MIMCServerAck serverAck);
    void onHandleCreateGroup(String json, boolean isSuccess);
    void onHandleQueryGroupInfo(String json, boolean isSuccess);
    void onHandleQueryGroupsOfAccount(String json, boolean isSuccess);
    void onHandleJoinGroup(String json, boolean isSuccess);
    void onHandleQuitGroup(String json, boolean isSuccess);
    void onHandleKickGroup(String json, boolean isSuccess);
    void onHandleUpdateGroup(String json, boolean isSuccess);
    void onHandleDismissGroup(String json, boolean isSuccess);
    void onHandlePullP2PHistory(String json, boolean isSuccess);
    void onHandlePullP2THistory(String json, boolean isSuccess);
    void onHandleSendMessageTimeout(MIMCMessage message);
    void onHandleSendGroupMessageTimeout(MIMCGroupMessage groupMessage);
    void onHandleJoinUnlimitedGroup(long topicId, int code, String errMsg);
    void onHandleQuitUnlimitedGroup(long topicId, int code, String errMsg);
    void onHandleDismissUnlimitedGroup(String json, boolean isSuccess);
    void onHandleQueryUnlimitedGroupMembers(String json, boolean isSuccess);
    void onHandleQueryUnlimitedGroups(String json, boolean isSuccess);
    void onHandleQueryUnlimitedGroupOnlineUsers(String json, boolean isSuccess);
}
