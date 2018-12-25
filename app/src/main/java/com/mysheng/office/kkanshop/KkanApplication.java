package com.mysheng.office.kkanshop;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.igexin.sdk.PushManager;




import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * Created by myaheng on 2018/5/14.
 */

public class KkanApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static final String HASH_ALGORITHM = "MD5";
    private static final int RADIX = 10 + 26; // 10 digits + 26 letters
    public static DisplayMetrics mDisplayMetrics;
    public static ExecutorService cThreadPool;
    private static String IMAGE_CACHE_PATH;
    public static RequestQueue queues;
    public static Context mContext;

    private static int mCount = 0;
    private static final String TAG = "com.xiaomi.mimcdemo";
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        PushManager.getInstance().initialize(this.getApplicationContext(), com.mysheng.office.kkanshop.service.AppPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), com.mysheng.office.kkanshop.service.ReceiveIntentService.class);
        //CrashHandler.getInstance().init(this);//异常信息记录
        registerActivityLifecycleCallbacks(this);
        mDisplayMetrics = getResources().getDisplayMetrics();
        cThreadPool = Executors.newFixedThreadPool(5);
        IMAGE_CACHE_PATH = getExternalCacheDir().getPath();
        queues = Volley.newRequestQueue(getApplicationContext());
        queues = Volley.newRequestQueue(getApplicationContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "100";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "101";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.canBypassDnd();//绕过打扰模式
        channel.enableLights(true);

        channel.setLightColor(Color.GRAY);
        channel.shouldShowLights();
        notificationManager.createNotificationChannel(channel);
    }
    public static RequestQueue getHttpQueues()
    {
        return queues;
    }
    public static int dpToPx(int dp) {
        return (int) (mDisplayMetrics.density * dp);
    }

    public static int getScreenWidth() {
        return mDisplayMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        return mDisplayMetrics.heightPixels;
    }

    public static void deleteFiles(String path) {
        deleteFiles(new File(path));
    }

    public static void deleteFiles(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        //文件夹递归删除
        File[] files = file.listFiles();
        if (null == files) {
            return;
        }
        for (File subFile : files) {
            deleteFiles(subFile);
        }
        file.delete();
    }

    public static String getImageCachePath() {
        return IMAGE_CACHE_PATH;
    }

    public static int getMaxSizeOfBitMap(String path) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, op);
        return Math.max(op.outWidth, op.outHeight);
    }

    private static byte[] getMD5(byte[] data) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(data);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
        }
        return hash;
    }

    public static String generate(String imageUri) {
        byte[] md5 = getMD5(imageUri.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        if (imageUri.endsWith(".gif") || imageUri.endsWith(".GIF")) {
            return bi.toString(RADIX) + ".itgif";
        }
        return bi.toString(RADIX) + ".it";
    }

    public static void clearCache(final Context context) {
        cThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                File file = new File(IMAGE_CACHE_PATH);
                File[] files = file.listFiles();
                for (File item : files) {
                    item.delete();
                }
                Glide.get(context).clearDiskCache();
            }
        });

    }

    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCachedPath(String url) {
        String key = generate(url);
        String destUrl = getImageCachePath() + "/" + key;
        return destUrl;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG,"onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG,"onActivityStarted");
        mCount++;

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG,"onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {

        Log.d(TAG,"onActivityPaused");

    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG,"onActivityStopped");
        mCount--;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d(TAG,"onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG,"onActivityDestroyed");
    }

    public static int getActivityCount(){
        return  mCount;
    }

}
