package com.mysheng.office.kkanshop.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by mysheng on 2017/12/1.
 */

public class ReadLoginData {
    SharedPreferences spf;
    SharedPreferences.Editor editor;

    public String getLoginDate(Context context, String string){
        spf=context.getSharedPreferences("loginXml", Context.MODE_PRIVATE);
        editor=spf.edit();
        return spf.getString(string,"");
    }
    public void  setLoginData(Context context, String name, String value){
        spf=context.getSharedPreferences("loginXml", Context.MODE_PRIVATE);
        editor=spf.edit();
        editor.putString(name,value);
        editor.commit();
    }
    public void exitLoginDate(Context context){
        spf=context.getSharedPreferences("loginXml", Context.MODE_PRIVATE);
        spf.edit().clear().commit();

    }
}
