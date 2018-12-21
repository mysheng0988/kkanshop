package com.mysheng.office.kkanshop.MIMC.receiveChat;

import com.alibaba.fastjson.JSON;
import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.MIMC.bean.Msg;
import com.mysheng.office.kkanshop.MIMC.common.UserManager;
import com.mysheng.office.kkanshop.MIMC.constant.Constant;
import com.mysheng.office.kkanshop.MIMC.listener.OnHandleMIMCMsgListener;
import com.xiaomi.mimc.MIMCGroupMessage;
import com.xiaomi.mimc.MIMCMessage;
import com.xiaomi.mimc.MIMCMessageHandler;
import com.xiaomi.mimc.MIMCServerAck;

import java.util.List;

/**
 * Created by myaheng on 2018/12/21.
 */

public class MessageHandler implements MIMCMessageHandler {

    private OnHandleMIMCMsgListener onHandleMIMCMsgListener;

    public void setOnHandleMIMCMsgListener(OnHandleMIMCMsgListener onHandleMIMCMsgListener) {
        this.onHandleMIMCMsgListener = onHandleMIMCMsgListener;
    }

    /**
     * 接收单聊消息
     * MIMCMessage类
     * String packetId 消息ID
     * long sequence 序列号
     * String fromAccount 发送方帐号
     * String toAccount 接收方帐号
     * byte[] payload 消息体
     * long timestamp 时间戳
     */

    @Override
    public void handleMessage(List<MIMCMessage> packets) {
        for (int i = 0; i < packets.size(); ++i) {
            MIMCMessage mimcMessage = packets.get(i);
            try {
                Msg msg = JSON.parseObject(new String(mimcMessage.getPayload()), Msg.class);
               // if (msg.getMsgType() == Constant.TEXT) {
                    ChatMsg chatMsg = new ChatMsg();
                    chatMsg.setFromAccount(mimcMessage.getFromAccount());
                    chatMsg.setMsg(msg);
                    chatMsg.setSingle(true);
                    UserManager.getInstance().addMsg(chatMsg);
                //}
            } catch (Exception e) {
                Msg msg = new Msg();
                msg.setVersion(Constant.VERSION);
                msg.setMsgId(msg.getMsgId());
                msg.setMsgType(Constant.TEXT);
                msg.setTimestamp(System.currentTimeMillis());
                msg.setContent(mimcMessage.getPayload());
                ChatMsg chatMsg = new ChatMsg();
                chatMsg.setFromAccount(mimcMessage.getFromAccount());
                chatMsg.setMsg(msg);
                chatMsg.setSingle(true);
                UserManager.getInstance().addMsg(chatMsg);
            }
        }
    }

    /**
     * 接收群聊消息
     * MIMCGroupMessage类
     * String packetId 消息ID
     * long groupId 群ID
     * long sequence 序列号
     * String fromAccount 发送方帐号
     * byte[] payload 消息体
     * long timestamp 时间戳
     */
    @Override
    public void handleGroupMessage(List<MIMCGroupMessage> packets) {
        for (int i = 0; i < packets.size(); i++) {
            MIMCGroupMessage mimcGroupMessage = packets.get(i);
            try {
                Msg msg = JSON.parseObject(new String(packets.get(i).getPayload()), Msg.class);
                //if (msg.getMsgType() == Constant.TEXT) {
                    ChatMsg chatMsg = new ChatMsg();
                    chatMsg.setFromAccount(mimcGroupMessage.getFromAccount());
                    chatMsg.setMsg(msg);
                    chatMsg.setSingle(false);
                    UserManager.getInstance().addGroupMsg(chatMsg);
               // }
            } catch (Exception e) {
                Msg msg = new Msg();
                msg.setVersion(Constant.VERSION);
                msg.setMsgId(msg.getMsgId());
                msg.setMsgType(Constant.MSG_TEXT);
                msg.setTimestamp(System.currentTimeMillis());
                msg.setContent(packets.get(i).getPayload());
                ChatMsg chatMsg = new ChatMsg();
                chatMsg.setFromAccount(mimcGroupMessage.getFromAccount());
                chatMsg.setMsg(msg);
                chatMsg.setSingle(false);
                UserManager.getInstance().addGroupMsg(chatMsg);
            }
        }
    }

    /**
     * 接收服务端已收到发送消息确认
     * MIMCServerAck类
     * String packetId 消息ID
     * long sequence 序列号
     * long timestamp 时间戳
     */
    @Override
    public void handleServerAck(MIMCServerAck serverAck) {
        onHandleMIMCMsgListener.onHandleServerAck(serverAck);
    }

    /**
     * 接收单聊超时消息
     * @param message 单聊消息类
     */
    @Override
    public void handleSendMessageTimeout(MIMCMessage message) {
        onHandleMIMCMsgListener.onHandleSendMessageTimeout(message);
    }

    /**
     *接收发送群聊超时消息
     * @param groupMessage 群聊消息类
     */
    @Override
    public void handleSendGroupMessageTimeout(MIMCGroupMessage groupMessage) {
        onHandleMIMCMsgListener.onHandleSendGroupMessageTimeout(groupMessage);
    }

    @Override
    public void handleSendUnlimitedGroupMessageTimeout(MIMCGroupMessage mimcGroupMessage) {

    }

    @Override
    public void handleUnlimitedGroupMessage(List<MIMCGroupMessage> packets) {
        for (int i = 0; i < packets.size(); i++) {
            MIMCGroupMessage mimcGroupMessage = packets.get(i);
            try {
                Msg msg = JSON.parseObject(new String(packets.get(i).getPayload()), Msg.class);
                ChatMsg chatMsg = new ChatMsg();
                chatMsg.setFromAccount(mimcGroupMessage.getFromAccount());
                chatMsg.setMsg(msg);
                chatMsg.setSingle(false);
                UserManager.getInstance().addGroupMsg(chatMsg);
            } catch (Exception e) {
                Msg msg = new Msg();
                msg.setVersion(Constant.VERSION);
                msg.setMsgId(msg.getMsgId());
                msg.setMsgType(Constant.TEXT);
                msg.setTimestamp(System.currentTimeMillis());
                msg.setContent(packets.get(i).getPayload());
                ChatMsg chatMsg = new ChatMsg();
                chatMsg.setFromAccount(mimcGroupMessage.getFromAccount());
                chatMsg.setMsg(msg);
                chatMsg.setSingle(false);
                UserManager.getInstance().addGroupMsg(chatMsg);
            }
        }
    }
}
