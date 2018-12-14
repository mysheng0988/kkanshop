package com.mysheng.office.kkanshop.MIMC.listener;

import com.xiaomi.mimc.data.RtsDataType;

/**
 * Created by houminjiang on 18-5-24.
 */

public interface OnCallStateListener {
    void onLaunched(String fromAccount, String fromResource, long chatId, byte[] data);
    void onAnswered(long chatId, boolean accepted, String errMsg);
    void handleData(long chatId, RtsDataType dataType, byte[] data);
    void onClosed(long chatId, String errMsg);
}
