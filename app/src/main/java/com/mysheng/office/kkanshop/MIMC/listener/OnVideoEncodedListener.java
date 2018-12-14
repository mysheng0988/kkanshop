package com.mysheng.office.kkanshop.MIMC.listener;

/**
 * Created by houminjiang on 18-6-14.
 */

public interface OnVideoEncodedListener {
    void onVideoEncoded(byte[] data, int width, int height, long sequence);
}
