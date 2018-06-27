package com.mysheng.office.kkanshop.util;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by myaheng on 2018/5/25.
 */

public class CustomToast {
    private static Toast toast = null;
    public static void show(Context context,String theText){
        if (toast == null) {
            toast = Toast.makeText(context, theText, Toast.LENGTH_SHORT);
        } else {
            toast.setText(theText);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
