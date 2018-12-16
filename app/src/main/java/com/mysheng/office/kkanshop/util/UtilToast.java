package com.mysheng.office.kkanshop.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by myaheng on 2018/10/17.
 */

public class UtilToast {
    public static void showShort(Context context, CharSequence message) {
        Toast mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        mToast.setText(message);
        mToast.show();
    }
    public static void showLong(Context context, CharSequence message) {
        Toast mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
        mToast.setText(message);
        mToast.show();
    }
}
