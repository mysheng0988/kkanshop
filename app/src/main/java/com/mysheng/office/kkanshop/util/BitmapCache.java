package com.mysheng.office.kkanshop.util;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by mysheng on 18/5/11.
 */

/**
 * 网络图片缓存
 */
public class BitmapCache implements ImageLoader.ImageCache
{
    public LruCache<String, Bitmap> cache;
    public int max = 10*1024*1024; // 10M图片大小

    public BitmapCache()
    {
        cache = new LruCache<String, Bitmap>(max){
            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap=cache.get(url);
        return bitmap;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }
}
