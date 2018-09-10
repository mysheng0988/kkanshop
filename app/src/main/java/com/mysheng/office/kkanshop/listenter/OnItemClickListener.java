package com.mysheng.office.kkanshop.listenter;

import android.view.View;

/**
 * Created by myaheng on 2018/9/10.
 */

public interface OnItemClickListener<T> {
    void onItemClick(View view,T model,int poistion);
}
