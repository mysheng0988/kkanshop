package com.mysheng.office.kkanshop.customCamera.util;

import android.os.Environment;

/**
 * Created by yy on 2018/3/1.
 * 描述：
 */

public class Constant {
    public final static boolean IS_LOG_ENABLED = true;


    public static final int AutoVBRMode_CAMERA = 25;
    public static final int AutoVBRMode = 28;
    //帧率
    public static final int FRAMERATE_20 = 20;
    public static final int FRAMERATE_15 = 15;
    //缩放大小
    public static final float SCALE_10 = 1.0f;
    public static final float SCALE_15 = 1.5f;
    public static final float SCALE_20 = 2.0f;

    public static final String CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mio";

    //视频缓存目录
    public static final String VIDEO_CACHE = CACHE_DIR + "/cache/video";

    //图片缓存目录
    public static final String IMAGE_CACHE = CACHE_DIR + "/cache/image/";

    //文件下载目录
    public static final String DOWNLOAD_FILE = CACHE_DIR + "/download/";

    //视频裁剪文件
    public static final String VIDEO_CROP_TEMP_FILE = VIDEO_CACHE + "crop_";

    public static final String TYPE_CAMERA = "type";

    //筛选视频裁剪 大小 3M = 3 * 1024 * 1024 = 3145728
    public static final int VIDEO_LENGTH_BYTE = 3 * 1024 * 1024;


}
