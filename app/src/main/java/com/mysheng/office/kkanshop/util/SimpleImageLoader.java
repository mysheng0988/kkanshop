package com.mysheng.office.kkanshop.util;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;


/**
 * Created by mysheng on 2018/1/10.
 */

public class SimpleImageLoader {
    private static SimpleImageLoader mImageLoader;
    private LruCache<String,Bitmap> mLruCache;
    public static SimpleImageLoader getInstance(){
        if(mImageLoader==null){
            synchronized (SimpleImageLoader.class){
                if(mImageLoader==null){
                    mImageLoader=new SimpleImageLoader();
                }
            }
        }
        return  mImageLoader;
    }

    /**
     * 用来初始化缓存对象
     */
    public SimpleImageLoader(){
        int maxSize= (int) (Runtime.getRuntime().maxMemory()/8);
        mLruCache=new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

    }

    /**
     * 用来加载网路图片
     * @param view
     * @param url
     */
    public void displayImage(ImageView view,String url){
            Bitmap bitmap=getBitmapFromCache(url);
            if(bitmap!=null){
                view.setImageBitmap(bitmap);
            }else{
               VolleyImage.loadImageByURL(url,view);
            }
    }



    /**
     * 从缓存中读取图片
     * @param url
     * @return
     */
    public Bitmap getBitmapFromCache(String url){
        Log.e("mys", "getBitmapFromCache: "+mLruCache.get(url));
        return mLruCache.get(url);

    }

    /**
     * 将下载下来的图片放到缓存中
     * @param bitmap
     * @param url
     */
    public void putBitmapToCache(Bitmap bitmap,String url){
        mLruCache.put(url,bitmap);

    }

    /**
     * 清除缓存
     * @param url
     */
    public void clearBitmapToCache(String url){
        mLruCache.remove(url);
    }
    /**
     * 清空内存缓存
     */
    public void clearAllBitmapToCache(){
        mLruCache.evictAll();
    }
//    private void downloadImage(final ImageView view, final String url) {
//
//        DownloadImageTask imageTask= new DownloadImageTask();
//        imageTask.setCallBackAsyncTask(new DownloadImageTask.CallBackAsyncTask() {
//            @Override
//            public void onCallBack(Bitmap result) {
//                if(result==null){
//                    view.setImageResource(R.drawable.default_header);
//                }else{
//                    view.setImageBitmap(result);
//                }
//
//            }
//        });
//        imageTask.execute(url);
//    }
//    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        private CallBackAsyncTask callBack;
//
//        protected Bitmap doInBackground(String... urls) {
//
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urls[0]).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//                mImageLoader.putBitmapToCache(mIcon11,urls[0]);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//
//            if(callBack!=null){
//                callBack.onCallBack(result);
//            }
//        }
//        public void setCallBackAsyncTask(CallBackAsyncTask callBackAsyncTask){
//            this.callBack = callBackAsyncTask;
//        }
//
//        //设置回调借口
//        public interface CallBackAsyncTask{
//            void onCallBack(Bitmap result);
//        }
//    }

}
