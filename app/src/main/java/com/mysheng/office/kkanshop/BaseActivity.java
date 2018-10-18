package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by myaheng on 2018/10/17.
 */

public abstract class BaseActivity extends Activity  implements View.OnClickListener{
    

    /**
     * 初始化View
     */
    protected abstract void initView();
    /**
     * 初始化event
     */
    protected abstract void initEvent();
}
