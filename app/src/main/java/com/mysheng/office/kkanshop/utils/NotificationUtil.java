package com.mysheng.office.kkanshop.utils;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;


import com.mysheng.office.kkanshop.ChatActivity;
import com.mysheng.office.kkanshop.MainActivity;
import com.mysheng.office.kkanshop.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by myaheng on 2018/5/22.
 */

public class NotificationUtil {
    public static String CHAT="100";
    public static String SUBSCRIBE="101";
    private static NotificationUtil notificationUtil;
    public static NotificationUtil getInstance(){

        if(notificationUtil==null){
            synchronized (NotificationUtil.class){
                if(notificationUtil==null){
                    notificationUtil=new NotificationUtil();

                }
            }
        }
        return  notificationUtil;

    }
    public void sendMsg(Context context,String sendUserId,String sendUserName,String msgTitle,String msgNote){
        int currentapiVersion=android.os.Build.VERSION.SDK_INT;
        if(currentapiVersion>=26){
            sendChatMsg(context,sendUserId,sendUserName,msgTitle,msgNote);
        }else{
            sentOutNotify(context,sendUserId,sendUserName,msgTitle,msgNote);
        }
    }
    public void sendSubscribeMsg(Context context,String strTitle,String content) {
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context, SUBSCRIBE)
                .setContentTitle(strTitle)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon))
                .setAutoCancel(true)
                .build();
        int msgID= (int) System.currentTimeMillis();
        manager.notify(msgID, notification);
    }
    public void sendChatMsg(Context context,String sendUserId,String sendUserName,String msgTitle,String msgNote) {
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHAT);
        mBuilder.setContentTitle(msgTitle);
        mBuilder .setContentText(msgNote);
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setSmallIcon(R.drawable.icon);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon));
        mBuilder.setAutoCancel(true);
        Intent notifyIntent = new Intent(context, ChatActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("sendUserId",sendUserId);
        bundle.putString("sendUserName",sendUserName );
        notifyIntent.putExtras(bundle);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pIntent);
        Notification notification=mBuilder.build();
        int msgID= (int) System.currentTimeMillis();
        manager.notify(msgID, notification);
    }
    public void sentOutNotify(Context context,String sendUserId,String sendUserName,String msgTitle,String msgNote){
        NotificationManager manager= (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setTicker(msgTitle);
        mBuilder.setContentTitle(msgTitle);
        mBuilder.setContentText(msgNote);
        mBuilder.setAutoCancel(true);
        mBuilder.setShowWhen(true);
        mBuilder.setSmallIcon(R.drawable.icon);
        Intent notifyIntent = new Intent(context, ChatActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("sendUserId",sendUserId);
        bundle.putString("sendUserName",sendUserName );
        notifyIntent.putExtras(bundle);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pIntent);
        Notification notification=mBuilder.build();
        notification.defaults = Notification.DEFAULT_ALL;
        int messageId= (int) System.currentTimeMillis();
        manager.notify(messageId, notification);
    }
    public static boolean isNotificationEnabled(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0手机以上
            if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).getImportance() == NotificationManager.IMPORTANCE_NONE) {
                return false;
            }
        }

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                appOpsClass = Class.forName(AppOpsManager.class.getName());
            }
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
