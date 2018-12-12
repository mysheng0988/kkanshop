package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mysheng.office.kkanshop.permissions.RxPermissions;

/**
 * Created by myaheng on 2018/10/17.
 */

public abstract class BaseActivity extends Activity  implements View.OnClickListener{
    public RxPermissions rxPermissions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        rxPermissions = new RxPermissions(this);
        super.onCreate(savedInstanceState);

    }

    /**
     * 初始化View
     */
    protected abstract void initView();
    /**
     * 初始化event
     */
    protected abstract void initEvent();
}
