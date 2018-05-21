package com.mysheng.office.kkanshop.util;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.mysheng.office.kkanshop.KkanApplication;
import com.mysheng.office.kkanshop.R;

/**
 * Created by mysheng on 2018/5/15.
 * 应用VolleyImage加载网络资源
 */

public class VolleyImage {
    /**
     * 不带缓存的图片加载
     * @param imageUrl
     * @param imageView
     */
//    public static int maxMemory = (int) Runtime.getRuntime().maxMemory();
//    public static int maxSize = maxMemory / 8;
//    private static LruCache<String, Bitmap> lruCache;
//    public VolleyImage(){
//        lruCache = new LruCache<String, Bitmap>(maxSize) {
//
//            @Override
//            protected int sizeOf(String key, Bitmap value) {
//                // 测量Bitmap的大小 默认返回图片数量
//                return value.getRowBytes() * value.getHeight();
//            }
//
//        };
//    }
    public static void loadImageByURL(final String imageUrl,final ImageView imageView){

        //imageView.setImageBitmap(null);
        //Bitmap bitmap=lruCache.get(imageUrl);
        //if(bitmap==null){
            ImageRequest request = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    //lruCache.put(imageUrl,response);
                    imageView.setImageBitmap(response);
                }
            }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //imageView.setImageBitmap(null);
                }
            });

            KkanApplication.getHttpQueues().add(request);
//        }else{
//            imageView.setImageBitmap(bitmap);
//        }

    }




    /**
     * 带缓存的图片加载
     * @param imageUrl
     * @param imageView
     */
    public static void loadImageCacheByURL(String imageUrl,ImageView imageView){
        ImageLoader loader = new ImageLoader(KkanApplication.getHttpQueues(), new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,1,1);

        loader.get(imageUrl, listener);
    }

    /**
     * 应用NetworkImageView组件加载图片
     * @param imageUrl
     * @param networkImageView
     */
    public static void NetworkImageViewByURL(String imageUrl,NetworkImageView networkImageView){
        ImageLoader loader = new ImageLoader(KkanApplication.getHttpQueues(), new BitmapCache());
        networkImageView.setErrorImageResId(R.drawable.default_header);
        networkImageView.setDefaultImageResId(R.drawable.default_header);
        networkImageView.setImageUrl(imageUrl, loader);
    }
}
