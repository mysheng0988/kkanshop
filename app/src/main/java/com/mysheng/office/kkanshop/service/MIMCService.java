package com.mysheng.office.kkanshop.service;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mysheng.office.kkanshop.KkanApplication;
import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.MIMC.common.UserManager;
import com.mysheng.office.kkanshop.MIMC.constant.Constant;
import com.mysheng.office.kkanshop.MIMC.listener.OnHandleMIMCMsgListener;
import com.mysheng.office.kkanshop.listenter.MIMCUpdateChatMsg;
import com.mysheng.office.kkanshop.util.SharedPreferencesUtils;
import com.mysheng.office.kkanshop.utils.NotificationUtil;
import com.xiaomi.mimc.MIMCGroupMessage;
import com.xiaomi.mimc.MIMCMessage;
import com.xiaomi.mimc.MIMCServerAck;
import com.xiaomi.mimc.MIMCUser;
import com.xiaomi.mimc.common.MIMCConstant;

/**
 * Created by myaheng on 2018/12/18.
 */

public class MIMCService extends Service implements OnHandleMIMCMsgListener {

    private String TAG="MIMCService";
    private BroadcastReceiver mBatInfoReceiver;
    private MIMCBinder mimcBinder=new MIMCBinder();
    private MIMCUpdateChatMsg updateChatMsg;

    private SharedPreferencesUtils shareData;
    private String userId;
    private MIMCUser mimcUser;
    private boolean isNotice=true;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                ChatMsg chatMsg= (ChatMsg) msg.obj;
                String runningActivity=getRunningActivityName();
                int activityCount= KkanApplication.getActivityCount();
                if(activityCount==1&&"ChatListViewActivity".equals(runningActivity)){
                    Vibrator mVibrator=(Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
                    mVibrator.vibrate(new long[]{200,100,500,300},-1);
                    if(updateChatMsg!=null){
                        updateChatMsg.noticeNewMsg(chatMsg);
                    }
                }else if(activityCount==1&&"ChatActivity".equals(runningActivity)){
                    if(updateChatMsg!=null){
                        updateChatMsg.noticeNewMsg(chatMsg);
                    }
                }else{
                    String content="";
                    int msgType=chatMsg.getMsg().getMsgType();
                    switch (msgType){
                        case Constant.MSG_TEXT:
                            content=new String(chatMsg.getMsg().getContent());
                            break;
                        case Constant.MSG_IMAGE:
                            content="[图片]";
                            break;
                        case Constant.MSG_AUDIO:
                            content="[语音]";
                            break;
                        case Constant.MSG_VIDEO:
                            content="[视频]";
                            break;
                        case Constant.MSG_LOCATION:
                            content="[地图]";
                            break;
                        case Constant.MSG_GOODS:
                            content="[商品]";
                            break;
                    }
                    NotificationUtil.getInstance().sendMsg(MIMCService.this,chatMsg.getFromAccount(),
                            chatMsg.getFromAccount(), chatMsg.getMsg().getFromName()  ,content);
                }
            }
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        shareData=new SharedPreferencesUtils(this);
        userId= (String) shareData.getParam("phone","");
        mimcUser = UserManager.getInstance().newUser(userId);

        UserManager.getInstance().setHandleMIMCMsgListener(this);
        initBroadcastReceiver();

    }


    public MIMCUser getMIMCUser(){
       return mimcUser;
    }

    /**
     * 注册广播
     */
    private void initBroadcastReceiver() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //关机广播
        filter.addAction(Intent.ACTION_SHUTDOWN);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.d(TAG, "屏幕亮屏");
                    isNotice=false;
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.d(TAG, "屏幕灭屏");
                    isNotice=false;
                }else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    Log.d(TAG, "屏幕解锁");
                    isNotice=true;
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    Log.i(TAG, "屏幕解锁");
                    isNotice=false;
                } else if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
                    isNotice=false;
                    Log.i(TAG, "关机");
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mimcBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;

    }
    public void setUpdateChatMsg(MIMCUpdateChatMsg updateChatMsg) {
        this.updateChatMsg = updateChatMsg;
    }

    private String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        int num=runningActivity.lastIndexOf(".")+1;
        String activity=runningActivity.substring(num);
        return activity;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatInfoReceiver);
    }

    @Override
    public void onHandleMessage(ChatMsg chatMsg) {
        Message message=new Message();
        message.what=100;
        message.obj=chatMsg;
        handler.sendMessage(message);

    }

    @Override
    public void onHandleGroupMessage(ChatMsg chatMsg) {

    }

    @Override
    public void onHandleStatusChanged(MIMCConstant.OnlineStatus status) {

    }

    @Override
    public void onHandleServerAck(MIMCServerAck serverAck) {

    }

    @Override
    public void onHandleCreateGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQueryGroupInfo(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQueryGroupsOfAccount(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleJoinGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQuitGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleKickGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleUpdateGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleDismissGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandlePullP2PHistory(String json, boolean isSuccess) {

    }

    @Override
    public void onHandlePullP2THistory(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleSendMessageTimeout(MIMCMessage message) {

    }

    @Override
    public void onHandleSendGroupMessageTimeout(MIMCGroupMessage groupMessage) {

    }

    @Override
    public void onHandleJoinUnlimitedGroup(long topicId, int code, String errMsg) {

    }

    @Override
    public void onHandleQuitUnlimitedGroup(long topicId, int code, String errMsg) {

    }

    @Override
    public void onHandleDismissUnlimitedGroup(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQueryUnlimitedGroupMembers(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQueryUnlimitedGroups(String json, boolean isSuccess) {

    }

    @Override
    public void onHandleQueryUnlimitedGroupOnlineUsers(String json, boolean isSuccess) {

    }


    /**
     * 向Activity传递数据的纽带
     */
    public class MIMCBinder extends Binder {

        /**
         * 获取当前service对象
         *
         * @return StepService
         */
        public MIMCService getService() {
            return MIMCService.this;
        }
    }
}
