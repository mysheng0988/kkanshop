package com.mysheng.office.kkanshop.ImageViewer.listener;

/**
 * Created by liuting on 17/5/26.
 */

public interface OnPullCloseListener {

    void onClose();

    void onPull(float range);

    void onCancel();
}