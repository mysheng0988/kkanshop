package com.mysheng.office.kkanshop.util;
import android.app.Activity;
import android.content.Intent;
import com.mysheng.office.kkanshop.zxing.android.CaptureActivity;
import com.mysheng.office.kkanshop.zxing.bean.ZxingConfig;
import com.mysheng.office.kkanshop.zxing.common.Constant;

public class CommonUtil {
    public static int IMAGE=0x001;
    public static int CAMERA=0x002;
    public static int AUDIO=0x003;
    public static int SCAN_CODE=0x110;
    public static int SCAN_RESULT=0x110;
    public static void StartScanCode(Activity activity){
        Intent intent = new Intent(activity, CaptureActivity.class);
        /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
         * 也可以不传这个参数
         * 不传的话  默认都为默认不震动  其他都为true
         * */
        ZxingConfig config = new ZxingConfig();
        config.setPlayBeep(true);
        config.setShake(true);
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);

        activity.startActivityForResult(intent, SCAN_RESULT);
    }
}
