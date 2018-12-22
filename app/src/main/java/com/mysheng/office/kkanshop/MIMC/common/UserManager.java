package com.mysheng.office.kkanshop.MIMC.common;

import android.util.Log;


import com.alibaba.fastjson.JSON;
import com.mysheng.office.kkanshop.KkanApplication;
import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.MIMC.bean.Msg;
import com.mysheng.office.kkanshop.MIMC.constant.Constant;
import com.mysheng.office.kkanshop.MIMC.listener.OnCallStateListener;
import com.mysheng.office.kkanshop.MIMC.listener.OnHandleMIMCMsgListener;
import com.mysheng.office.kkanshop.MIMC.receiveChat.MessageHandler;
import com.mysheng.office.kkanshop.MIMC.receiveChat.TokenFetcher;
import com.mysheng.office.kkanshop.VoiceCallActivity;
import com.mysheng.office.kkanshop.util.SharedPreferencesUtils;
import com.xiaomi.mimc.MIMCGroupMessage;
import com.xiaomi.mimc.MIMCMessage;
import com.xiaomi.mimc.MIMCMessageHandler;
import com.xiaomi.mimc.MIMCOnlineStatusListener;
import com.xiaomi.mimc.MIMCRtsCallHandler;
import com.xiaomi.mimc.MIMCServerAck;
import com.xiaomi.mimc.MIMCTokenFetcher;
import com.xiaomi.mimc.MIMCUnlimitedGroupHandler;
import com.xiaomi.mimc.MIMCUser;
import com.xiaomi.mimc.common.MIMCConstant;
import com.xiaomi.mimc.data.LaunchedResponse;
import com.xiaomi.mimc.data.RtsChannelType;
import com.xiaomi.mimc.data.RtsDataType;

import com.xiaomi.msg.data.XMDPacket;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserManager {
    /**
     * appId/appKey/appSecret，小米开放平台(https://dev.mi.com/console/appservice/mimc.html)申请
     * 其中appKey和appSecret不可存储于APP端，应存储于APP自己的服务器，以防泄漏。
     *
     * 此处appId/appKey/appSec为小米MimcDemo所有，会在一定时间后失效，建议开发者自行申请
     **/
    // online
    private long appId = 2882303761517808316L;
    private String domain = "https://mimc.chat.xiaomi.net/";



    // 用户登录APP的帐号
    private String appAccount = "";
    private String fromName="";
    private String url;
    private MIMCUser mUser;
    private MIMCConstant.OnlineStatus mStatus;
    private SharedPreferencesUtils shareData;
    private final static UserManager instance = new UserManager();
    private OnHandleMIMCMsgListener onHandleMIMCMsgListener;
    private OnCallStateListener onCallStateListener;
    public static int TIMEOUT_ON_LAUNCHED = 1 * 30 * 1000;
    public static int STATE_TIMEOUT = 0;
    public static int STATE_AGREE = 1;
    public static int STATE_REJECT = 2;
    public static int STATE_INTERRUPT = 3;
    private volatile int answer = STATE_TIMEOUT;
    private Object lock = new Object();
    private static final String TAG = "UserManager";


    // 序列化 建议用parcelable
    public static byte[] toByteArray(Object object){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    // 反序列化 建议用parcelable
    public static Object fromByteArray(byte[] bytes){
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    // 设置消息监听
    public void setHandleMIMCMsgListener(OnHandleMIMCMsgListener onHandleMIMCMsgListener) {
        this.onHandleMIMCMsgListener = onHandleMIMCMsgListener;
    }

    public void setCallStateListener(OnCallStateListener onCallStateListener) {
        this.onCallStateListener = onCallStateListener;
    }


    public static UserManager getInstance() {
        return instance;
    }

    /**
     * 获取用户帐号
     * @return 成功返回用户帐号，失败返回""
     */
    public String getAccount() {
        return appAccount;
    }

    /**
     * 获取用户在线状态
     * @return STATUS_LOGIN_SUCCESS 在线，STATUS_LOGOUT 下线，STATUS_LOGIN_FAIL 登录失败
     */
    public MIMCConstant.OnlineStatus getStatus() {
        return mStatus;
    }

    public void addMsg(ChatMsg chatMsg) {
        onHandleMIMCMsgListener.onHandleMessage(chatMsg);
    }

    public void addGroupMsg(ChatMsg chatMsg) {
        onHandleMIMCMsgListener.onHandleGroupMessage(chatMsg);
    }

    public void sendMsg(String toAppAccount, byte[] content, int msgType) {
        Msg msg = new Msg();
        //ping/pong/add_friend_request不设置content，默认为空
        if (msgType == Constant.PING) {
            msg.setVersion(Constant.VERSION);
            msg.setMsgId(msg.getMsgId());
            msg.setMsgType(msgType);
            msg.setTimestamp(System.currentTimeMillis());
            String json = JSON.toJSONString(msg);
            mUser.sendMessage(toAppAccount, json.getBytes());
        } else if (msgType == Constant.PONG) {
            msg.setVersion(Constant.VERSION);
            msg.setMsgId(msg.getMsgId());
            msg.setMsgType(msgType);
            msg.setTimestamp(System.currentTimeMillis());
            String json = JSON.toJSONString(msg);
            mUser.sendMessage(toAppAccount, json.getBytes());
        } else if (msgType ==Constant.MSG_TEXT ) {
            msg.setVersion(Constant.VERSION);
            msg.setMsgId(msg.getMsgId());
            msg.setMsgType(msgType);
            msg.setFromName(fromName);
            msg.setTimestamp(System.currentTimeMillis());
            msg.setContent(content);
            String json = JSON.toJSONString(msg);
            mUser.sendMessage(toAppAccount, json.getBytes());
            ChatMsg chatMsg = new ChatMsg();
            chatMsg.setFromAccount(appAccount);
            chatMsg.setMsg(msg);
            chatMsg.setSingle(true);
            addMsg(chatMsg);
        } else if (msgType == Constant.MSG_IMAGE) {
            msg.setVersion(Constant.VERSION);
            msg.setMsgId(msg.getMsgId());
            msg.setMsgType(msgType);
            msg.setFromName(fromName);
            msg.setTimestamp(System.currentTimeMillis());
            msg.setContent(content);
            String json = JSON.toJSONString(msg);
            mUser.sendMessage(toAppAccount, json.getBytes());
            ChatMsg chatMsg = new ChatMsg();
            chatMsg.setFromAccount(appAccount);
            chatMsg.setMsg(msg);
            chatMsg.setSingle(true);
            addMsg(chatMsg);
        } else if (msgType == Constant.TEXT_READ) {
            msg.setVersion(Constant.VERSION);
            msg.setMsgId(msg.getMsgId());
            msg.setMsgType(Constant.TEXT_READ);
            msg.setTimestamp(System.currentTimeMillis());
            //content为已读消息的msgId
            msg.setContent(content);
            String json = JSON.toJSONString(msg);
            mUser.sendMessage(toAppAccount, json.getBytes());
        }
        else if (msgType == Constant.TEXT_RECALL) {
            msg.setVersion(Constant.VERSION);
            msg.setMsgId(msg.getMsgId());
            msg.setMsgType(Constant.TEXT_RECALL);
            msg.setTimestamp(System.currentTimeMillis());
            //content为需要撤回消息的msgId
            msg.setContent(content);
            String json = JSON.toJSONString(msg);
            mUser.sendMessage(toAppAccount, json.getBytes());
        }
        else if (msgType == Constant.ADD_FRIEND_REQUEST) {
            msg.setVersion(Constant.VERSION);
            msg.setMsgId(msg.getMsgId());
            msg.setMsgType(Constant.ADD_FRIEND_REQUEST);
            msg.setTimestamp(System.currentTimeMillis());
            String json = JSON.toJSONString(msg);
            mUser.sendMessage(toAppAccount, json.getBytes());
        }
        else if (msgType == Constant.ADD_FRIEND_RESPONSE) {
            msg.setVersion(Constant.VERSION);
            msg.setMsgId(msg.getMsgId());
            msg.setMsgType(Constant.ADD_FRIEND_RESPONSE);
            msg.setTimestamp(System.currentTimeMillis());

            //content为true or false,表示同意或拒绝
            msg.setContent(content);
            String json = JSON.toJSONString(msg);
            mUser.sendMessage(toAppAccount, json.getBytes());
        }
    }
    public void sendLocationMsg(String toAppAccount, byte[] content,double v1,double v2,String title){
        Msg msg=new Msg();
        msg.setVersion(Constant.VERSION);
        msg.setMsgId(msg.getMsgId());
        msg.setMsgType(Constant.MSG_LOCATION);
        msg.setFromName(fromName);
        msg.setTimestamp(System.currentTimeMillis());
        msg.setContent(content);
        msg.setTabTitle(title);
        msg.setLatitude(v1);
        msg.setLongitude(v2);
        String json = JSON.toJSONString(msg);
        mUser.sendMessage(toAppAccount, json.getBytes());
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setFromAccount(appAccount);
        chatMsg.setMsg(msg);
        chatMsg.setSingle(true);
        addMsg(chatMsg);
    }
    public void sendGroupMsg(long groupID, byte[] content, int msgType, boolean isUnlimitedGroup) {
        Msg msg = new Msg();
        if (msgType == Constant.MSG_TEXT) {
            msg.setVersion(Constant.VERSION);
            msg.setMsgId(msg.getMsgId());
            msg.setMsgType(Constant.MSG_TEXT);
            msg.setTimestamp(System.currentTimeMillis());
            msg.setContent(content);
            String json = JSON.toJSONString(msg);
            if (isUnlimitedGroup) {
                mUser.sendUnlimitedGroupMessage(groupID, json.getBytes());
            } else {
                mUser.sendGroupMessage(groupID, json.getBytes());
            }
            ChatMsg chatMsg = new ChatMsg();
            chatMsg.setFromAccount(appAccount);
            chatMsg.setMsg(msg);
            chatMsg.setSingle(false);
            addMsg(chatMsg);
        }
        else if (msgType == Constant.MSG_IMAGE) {
            msg.setVersion(Constant.VERSION);
            msg.setMsgId(msg.getMsgId());
            msg.setMsgType(msgType);
            msg.setTimestamp(System.currentTimeMillis());
            msg.setContent(content);
            String json = JSON.toJSONString(msg);
            if (isUnlimitedGroup) {
                mUser.sendUnlimitedGroupMessage(groupID, json.getBytes());
            } else {
                mUser.sendGroupMessage(groupID, json.getBytes());
            }
            ChatMsg chatMsg = new ChatMsg();
            chatMsg.setFromAccount(appAccount);
            chatMsg.setMsg(msg);
            chatMsg.setSingle(false);
            addMsg(chatMsg);
        }
        else if (msgType == Constant.TEXT_RECALL) {
            msg.setVersion(Constant.VERSION);
            msg.setMsgId(msg.getMsgId());
            msg.setMsgType(Constant.TEXT_RECALL);
            msg.setTimestamp(System.currentTimeMillis());
            //content为需要撤回消息的msgId
            msg.setContent(content);
            String json = JSON.toJSONString(msg);
            if (isUnlimitedGroup) {
                mUser.sendUnlimitedGroupMessage(groupID, json.getBytes());
            } else {
                mUser.sendGroupMessage(groupID, json.getBytes());
            }
        }
    }

    /**
     * 获取用户
     * @return  返回已创建用户
     */
    public MIMCUser getUser() {
        return mUser;
    }

    /**
     * 创建用户
     * @param appAccount APP自己维护的用户帐号，不能为null
     * @return 返回新创建的用户
     */
    public MIMCUser newUser(String appAccount){
        if (appAccount == null || appAccount.isEmpty()) return null;
        if (this.appAccount.equals(appAccount)) return getUser();

        // 若是新用户，先释放老用户资源
        if (getUser() != null) {
            getUser().logout();
            getUser().destroy();
        }

        // online
        // cachePath必须传入，用于缓存文件读写，否则返回null
        mUser = MIMCUser.newInstance(appAccount, KkanApplication.mContext.getExternalFilesDir(null).getAbsolutePath());
        // staging
//        mUser = MIMCUser.newInstance(appAccount, MimcApplication.getContext().getExternalFilesDir(null).getAbsolutePath(), "http://10.38.162.117:6000/gslb/", "http://10.38.162.149/");
        // 注册相关监听，必须
        mUser.registerTokenFetcher(new TokenFetcher(appAccount));
        MessageHandler messageHandler=new MessageHandler();
        messageHandler.setOnHandleMIMCMsgListener(onHandleMIMCMsgListener);
        mUser.registerMessageHandler(messageHandler);
        mUser.registerOnlineStatusListener(new OnlineStatusListener());
        mUser.registerRtsCallHandler(new RTSHandler());
        mUser.registerUnlimitedGroupHandler(new UnlimitedGroupHandler());
        shareData=new SharedPreferencesUtils(KkanApplication.mContext);
        this.appAccount = appAccount;
        this.fromName= (String) shareData.getParam("userName","");
        return mUser;
    }

    class UnlimitedGroupHandler implements MIMCUnlimitedGroupHandler {
        @Override
        public void handleCreateUnlimitedGroup(long topicId, String topicName, boolean success, String errMsg, Object obj) {
            Log.i(TAG, String.format("handleCreateUnlimitedGroup topicId:%d topicName:%s success:%b errMsg:%s"
                    , topicId, topicName, success, errMsg));
        }

        @Override
        public void handleJoinUnlimitedGroup(long topicId, int code, String errMsg, Object obj) {
            onHandleMIMCMsgListener.onHandleJoinUnlimitedGroup(topicId, code, errMsg);
        }

        @Override
        public void handleQuitUnlimitedGroup(long topicId, int code, String errMsg, Object obj) {
            onHandleMIMCMsgListener.onHandleQuitUnlimitedGroup(topicId, code, errMsg);
        }

        @Override
        public void handleDismissUnlimitedGroup(long topicId, int code, String errMsg, Object obj) {
            onHandleMIMCMsgListener.onHandleDismissUnlimitedGroup(errMsg, false);
        }
    }

    class RTSHandler implements MIMCRtsCallHandler {
        @Override
        public LaunchedResponse onLaunched(String fromAccount, String fromResource, long chatId, byte[] appContent) {
            synchronized(lock) {
                Log.i(TAG, String.format("-----------新会话请求来了 chatId:%d", chatId));
                String callType = new String(appContent);
                if (callType.equalsIgnoreCase("AUDIO")) {
                    VoiceCallActivity.actionStartActivity(KkanApplication.mContext, fromAccount, chatId);
                } else if (callType.equalsIgnoreCase("VIDEO")) {

                }

                try {
                    answer = STATE_TIMEOUT;
                    lock.wait(TIMEOUT_ON_LAUNCHED);
                } catch (InterruptedException e) {
                    Log.w(TAG, "Call lock exception:", e);
                    answer = STATE_INTERRUPT;
                }

                boolean isAgree = false;
                String msg = "timeout";
                if (answer == STATE_TIMEOUT) {
                    if (onCallStateListener != null) onCallStateListener.onClosed(chatId, msg);
                } else if (answer == STATE_AGREE) {
                    isAgree = true;
                    msg = "agreed";
                } else if (answer == STATE_REJECT) {
                    msg = "rejected";
                    if (onCallStateListener != null) {
                        onCallStateListener.onClosed(chatId, msg);
                    }
                } else if (answer == STATE_INTERRUPT) {
                    msg = "interrupted";
                    if (onCallStateListener != null) {
                        onCallStateListener.onClosed(chatId, msg);
                    }
                }

                return new LaunchedResponse(isAgree, msg);
            }
        }

        @Override
        public void onAnswered(long chatId, boolean accepted, String errMsg) {
            Log.i(TAG, "-------------会话接通 chatId:" + chatId + " accepted:" + accepted + " errMsg:" + errMsg);
            if (onCallStateListener != null) onCallStateListener.onAnswered(chatId, accepted, errMsg);
        }

        @Override
        public void handleData(long chatId, byte[] data, RtsDataType dataType, RtsChannelType channelType) {
            Log.i(TAG, "-------------处理数据 chatId:" + chatId + " dataType:" + dataType + " channelType:" + channelType + " data.length:" + data.length);

            if (onCallStateListener != null) onCallStateListener.handleData(chatId, dataType, data);
        }

        @Override
        public void handleSendDataSuccess(long chatId, int groupId, Object context) {

        }

        @Override
        public void handleSendDataFail(long chatId, int groupId, Object context) {

        }

        @Override
        public void onClosed(long chatId, String errMsg) {
            Log.i(TAG, "-------------会话关闭 chatId:" + chatId + " errMsg:" + errMsg);
            if (onCallStateListener != null) onCallStateListener.onClosed(chatId, errMsg);
        }
    }

    public void answerCall() {
        synchronized (lock) {
            answer = STATE_AGREE;
            lock.notify();
        }
    }

    public void rejectCall() {
        synchronized (lock) {
            answer = STATE_REJECT;
            lock.notify();
        }
    }

    class OnlineStatusListener implements MIMCOnlineStatusListener {
        @Override
        public void statusChange(MIMCConstant.OnlineStatus status, String errType, String errReason, String errDescription) {
            mStatus = status;
            onHandleMIMCMsgListener.onHandleStatusChanged(status);
        }
    }




    public long dialCall(String toAppAccount, String toResource, byte[] data) {
        if (getUser() != null) {
            return getUser().dialCall(toAppAccount, toResource, data);
        }

        return -1;
    }

    public void closeCall(long chatId) {
        if (getUser() != null) {
            getUser().closeCall(chatId);
        }
    }

    public int sendRTSData(long chatId, byte[] data, RtsDataType dataType) {
        if (getUser() != null) {
            return getUser().sendRtsData(chatId, data, dataType, XMDPacket.DataPriority.P0, true, 0, RtsChannelType.RELAY, null);
        }

        return -1;
    }

    /**
     * 创建群
     * @param groupName 群名
     * @param users 群成员，多个成员之间用英文逗号(,)分隔
     */
    public void createGroup(final String groupName, final String users) {
        url = domain + "api/topic/" + appId;
        String json = "{\"topicName\":\"" + groupName + "\", \"accounts\":\"" + users + "\"}";
        MediaType JSON = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .addHeader("token", mUser.getToken())
                .post(RequestBody.create(JSON, json))
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandleCreateGroup(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onHandleMIMCMsgListener.onHandleCreateGroup(response.body().string(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询指定群信息
     * @param groupId 群ID
     */
    public void queryGroupInfo(final String groupId) {
        url = domain + "api/topic/" + appId + "/" + groupId;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .addHeader("token", mUser.getToken())
                .get()
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandleCreateGroup(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onHandleMIMCMsgListener.onHandleQueryGroupInfo(response.body().string(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所属群信息
     */
    public void queryGroupsOfAccount() {
        url = domain + "api/topic/" + appId + "/account";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .addHeader("token", mUser.getToken())
                .get()
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandleCreateGroup(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onHandleMIMCMsgListener.onHandleQueryGroupsOfAccount(response.body().string(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 邀请用户加入群
     * @param groupId 群ID
     * @param users 加入成员，多个成员之间用英文逗号(,)分隔
     */
    public void joinGroup(final String groupId, final String users) {
        url = domain + "api/topic/" + appId + "/" + groupId + "/accounts";
        String json = "{\"accounts\":\"" + users + "\"}";
        MediaType JSON = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .addHeader("token", mUser.getToken())
                .post(RequestBody.create(JSON, json))
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandleCreateGroup(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onHandleMIMCMsgListener.onHandleJoinGroup(response.body().string(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 非群主成员退群
     * @param groupId 群ID
     */
    public void quitGroup(final String groupId) {
        url = domain + "api/topic/" + appId + "/" + groupId + "/account";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .addHeader("token", mUser.getToken())
                .delete()
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandleCreateGroup(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onHandleMIMCMsgListener.onHandleQuitGroup(response.body().string(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 群主踢成员出群
     * @param groupId 群ID
     * @param users 群成员，多个成员之间用英文逗号(,)分隔
     */
    public void kickGroup(final String groupId, final String users) {
        url = domain + "api/topic/" + appId + "/" + groupId + "/accounts?accounts=" + users;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .addHeader("token", mUser.getToken())
                .delete()
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandleCreateGroup(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onHandleMIMCMsgListener.onHandleKickGroup(response.body().string(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 群主更新群信息
     * @param groupId 群ID
     * @param newOwnerAccount 若为群成员则指派新的群主
     * @param newGroupName 群名
     * @param newGroupBulletin 群公告
     */
    public void updateGroup(final String groupId, final String newOwnerAccount,  final String newGroupName, final String newGroupBulletin) {
        url = domain + "api/topic/" + appId + "/" + groupId;
        // 注意：不指定的信息则不更新（键值对一起不指定）
        String json = "{";
        if (!newOwnerAccount.isEmpty()) {
            json += "\"ownerAccount\":\"" + newOwnerAccount + "\"";
        } if (!newGroupName.isEmpty()) {
            json += "\"topicName\":\""+ newGroupName + "\"";
        } if (!newGroupBulletin.isEmpty()) {
            json += "\"bulletin\":\""+ newGroupBulletin + "\"";
        }
        json += "}";
        MediaType JSON = MediaType.parse("application/json");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .addHeader("token", mUser.getToken())
                .put(RequestBody.create(JSON, json))
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandleCreateGroup(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onHandleMIMCMsgListener.onHandleUpdateGroup(response.body().string(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *群主销毁群
     * @param groupId 群ID
     */
    public void dismissGroup(final String groupId) {
        url = domain + "api/topic/" + appId + "/" + groupId;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .addHeader("token", mUser.getToken())
                .delete()
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandleCreateGroup(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onHandleMIMCMsgListener.onHandleDismissGroup(response.body().string(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拉取单聊消息记录
     * @param toAccount 接收方帐号
     * @param fromAccount 发送方帐号
     * @param utcFromTime 开始时间
     * @param utcToTime 结束时间
     * 注意：utcFromTime和utcToTime的时间间隔不能超过24小时，查询状态为[utcFromTime,utcToTime)，单位毫秒，UTC时间
     */
    public void pullP2PHistory(String toAccount, String fromAccount, String utcFromTime, String utcToTime) {
        url = domain + "api/msg/p2p/query/";
        String json = "{\"toAccount\":\"" + toAccount + "\", \"fromAccount\":\""
                + fromAccount + "\", \"utcFromTime\":\"" + utcFromTime + "\", \"utcToTime\":\"" +
                utcToTime + "\"}";
        MediaType JSON = MediaType.parse("application/json;charset=UTF-8");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .addHeader("Accept", "application/json;charset=UTF-8")
                .addHeader("token", mUser.getToken())
                .post(RequestBody.create(JSON, json))
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandlePullP2PHistory(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onHandleMIMCMsgListener.onHandlePullP2PHistory(response.body().string(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拉取群聊消息记录
     * @param account 拉取者帐号
     * @param topicId 群ID
     * @param utcFromTime 开始时间
     * @param utcToTime 结束时间
     * 注意：utcFromTime和utcToTime的时间间隔不能超过24小时，查询状态为[utcFromTime,utcToTime)，单位毫秒，UTC时间
     */
    public void pullP2THistory(String account, String topicId, String utcFromTime, String utcToTime) {
        url = domain + "api/msg/p2t/query/";
        String json = "{\"account\":\"" + account + "\", \"topicId\":\""
                + topicId + "\", \"utcFromTime\":\"" + utcFromTime + "\", \"utcToTime\":\"" + utcToTime + "\"}";
        MediaType JSON = MediaType.parse("application/json;charset=UTF-8");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .addHeader("Accept", "application/json;charset=UTF-8")
                .addHeader("token", mUser.getToken())
                .post(RequestBody.create(JSON, json))
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandlePullP2THistory(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onHandleMIMCMsgListener.onHandlePullP2THistory(response.body().string(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询无限大群成员
     * @param topicId 群ID
     */
    public void queryUnlimitedGroupMembers(long topicId) {
        url = domain + "/api/uctopic/userlist";
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request
                .Builder()
                .url(url)
                .addHeader("token", mUser.getToken())
                .addHeader("topicId", String.valueOf(topicId))
                .get()
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandleQueryUnlimitedGroupMembers(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        onHandleMIMCMsgListener.onHandleQueryUnlimitedGroupMembers(json, true);
                    } else {
                        onHandleMIMCMsgListener.onHandleQueryUnlimitedGroupMembers(response.message(), false);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询无限大群
     */
    public void queryUnlimitedGroups() {
        String url = domain + "/api/uctopic/topics";
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request
                .Builder()
                .url(url)
                .addHeader("token", mUser.getToken())
                .get()
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandleQueryUnlimitedGroups(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        onHandleMIMCMsgListener.onHandleQueryUnlimitedGroups(json, true);
                    } else {
                        onHandleMIMCMsgListener.onHandleQueryUnlimitedGroups(response.message(), false);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询无限大群在线用户数
     * @param topicId
     */
    public void queryUnlimitedGroupOnlineUsers(long topicId) {
        url = domain + "/api/uctopic/onlineinfo";
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request
                .Builder()
                .url(url)
                .addHeader("token", mUser.getToken())
                .addHeader("topicId", String.valueOf(topicId))
                .get()
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onHandleMIMCMsgListener.onHandleQueryUnlimitedGroupOnlineUsers(e.getMessage(), false);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        onHandleMIMCMsgListener.onHandleQueryUnlimitedGroupOnlineUsers(json, true);
                    } else {
                        onHandleMIMCMsgListener.onHandleQueryUnlimitedGroupOnlineUsers(response.message(), false);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}