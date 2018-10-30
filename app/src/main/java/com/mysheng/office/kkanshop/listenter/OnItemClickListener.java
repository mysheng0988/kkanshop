package com.mysheng.office.kkanshop.listenter;

import android.view.View;

import java.util.List;

/**
 * Created by myaheng on 2018/9/10.
 */

public interface OnItemClickListener<T> {
    void onItemClick(View view, T model);
}
