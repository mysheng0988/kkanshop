package com.mysheng.office.kkanshop.service;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;


import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.message.FeedbackCmdMessage;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.mysheng.office.kkanshop.util.ReadLoginData;



/**
 * Created by myaheng on 2017/12/8.
 */

public class ReceiveIntentService extends GTIntentService {
    ReadLoginData loginData=new ReadLoginData();




    public ReceiveIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {

    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();
        String payload = new String(msg.getPayload());

    }


    public void startAlarmAndVibrator(final Context context){

        Vibrator mVibrator=(Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        mVibrator.vibrate(new long[]{1000,500},-1);
//        Handler  mHandler= new Handler(getMainLooper());
//        mHandler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Ringtone ringtone  = RingtoneManager.getRingtone(context, notification);
//                ringtone.play();
//            }
//        });

    }
    public void sentOutNotify(String msgTitle,String msgNote,String sendActivity,String sentName,String currentId,String userType,String clickChtId){
//        NotificationManager manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Notification.Builder mBuilder = new Notification.Builder(this);
//        mBuilder.setTicker(msgTitle);
//        mBuilder.setContentTitle(msgTitle);
//        mBuilder.setContentText(msgNote);
//        mBuilder.setAutoCancel(true);
//        mBuilder.setShowWhen(true);
//        mBuilder.setSmallIcon(R.drawable.favicon);
//        Intent notifyIntent=null;
//        if(sendActivity.equals("MainActivity")){
//            notifyIntent = new Intent(this, MainActivity.class);
//        }else if (sendActivity.equals("ChatPageActivity")){
//            notifyIntent = new Intent(this, ChatPageActivity.class);
//            Bundle bundle=new Bundle();
//            bundle.putString("sentName",sentName );
//            bundle.putString("pathUrl", "CHAT/chatPage.html?RevName="+sentName+"&RevUsrID="+currentId+"&usertype="+userType+"&ClickCHT_ID="+clickChtId);
//            notifyIntent.putExtras(bundle);
//        }
//        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pIntent);
//        Notification notification=mBuilder.build();
//        notification.defaults = Notification.DEFAULT_ALL;
//        int messageId= (int) System.currentTimeMillis();
//        manager.notify(messageId, notification);
    }
    public boolean checkedRunning(String send,String activity){
        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();//判断是否亮屏
        KeyguardManager mKeyguardManager = (KeyguardManager) this.getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
        if(!isScreenOn){
            return false;
        }else if(isScreenOn){
            if(flag){
                return false;
            }else if(send.equals(activity)){
                return true;
            }
        }

        return false;
    }
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }


    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        int action = cmdMessage.getAction();
        if ((action == PushConsts.THIRDPART_FEEDBACK)) {
            feedbackResult((FeedbackCmdMessage) cmdMessage);
        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    private void feedbackResult(FeedbackCmdMessage feedbackCmdMsg) {
        String appid = feedbackCmdMsg.getAppid();
        String taskid = feedbackCmdMsg.getTaskId();
        String actionid = feedbackCmdMsg.getActionId();
        String result = feedbackCmdMsg.getResult();
        long timestamp = feedbackCmdMsg.getTimeStamp();
        String cid = feedbackCmdMsg.getClientId();

        Log.d(TAG, "onReceiveCommandResult -> " + "appid = " + appid + "\n taskid = " + taskid + "\n actionid = " + actionid + "\n result = " + result
                + "\n cid = " + cid + "\n timestamp = " + timestamp);
    }
    private String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();

        return runningActivity;
    }

}
