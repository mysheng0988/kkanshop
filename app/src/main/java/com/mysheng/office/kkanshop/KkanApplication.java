package com.mysheng.office.kkanshop;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

/**
 * Created by myaheng on 2018/5/14.
 */

public class KkanApplication extends Application {
    // user your appid the key.
    private static final String APP_ID = "2882303761517808316";
    // user your appid the key.
    private static final String APP_KEY = "5491780810316";

    // 此TAG在adb logcat中检索自己所需要的信息， 只需在命令行终端输入 adb logcat | grep
    public static final String TAG = "com.mysheng.office.kkanshop";

    public static RequestQueue queues;
    @Override
    public void onCreate() {
        super.onCreate();
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }
            @SuppressLint("LongLogTag")
            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
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
