package com.mysheng.office.kkanshop;

import android.app.Activity;

/**
 * Created by myaheng on 2018/10/17.
 */

public abstract class BaseActivity extends Activity {
    /**
     * 初始化View
     */
    public abstract void initView();
    /**
     * 初始化event
     */
    public abstract void initEvent();
}
