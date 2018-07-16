package com.mysheng.office.kkanshop;


import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.igexin.sdk.PushManager;
import com.mysheng.office.kkanshop.util.CrashHandler;

import java.util.List;

/**
 * Created by myaheng on 2018/5/14.
 */

public class KkanApplication extends Application {
    // user your appid the key.
//    private static final String APP_ID = "2882303761517808316";
//    // user your appid the key.
//    private static final String APP_KEY = "5491780810316";
//
//    // 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
//    public static final String TAG = "com.mysheng.office.kkanshop";

    public static RequestQueue queues;
    @Override
    public void onCreate() {
        super.onCreate();
        PushManager.getInstance().initialize(this.getApplicationContext(), com.mysheng.office.kkanshop.service.AppPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), com.mysheng.office.kkanshop.service.ReceiveIntentService.class);
        CrashHandler.getInstance().init(this);//异常信息记录
        queues = Volley.newRequestQueue(getApplicationContext());
    }
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
    public static RequestQueue getHttpQueues()
    {
        return queues;
    }

}
