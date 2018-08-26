package com.mysheng.office.kkanshop.RxTool;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageViewLoadAndSetSizeAndCircle {

	private PicHandler pic_hdl;
	private ImageView imgView;
	private String url;


	private  int widht;

	private  int height;

	private int CircleSize;

	/**
	 * 通过图片url路径获取图片并显示到对应控件上
	 *
	 * @param imgView
	 * @param url
	 */
	public void setImage(ImageView imgView, String url,int widht,int height,int CircleSize) {
		this.url = url;
		this.imgView = imgView;
		this.widht=widht;
		this.height=height;
		this.CircleSize=CircleSize;
		pic_hdl = new PicHandler();

		Thread t = new LoadPicThread();

		t.start();
	}


	class LoadPicThread extends Thread {
		@Override
		public void run() {
			Bitmap img = getUrlImage(url);
			System.out.println(img + "---");
			Message msg = pic_hdl.obtainMessage();
			msg.what = 0;
			msg.obj = img;
			pic_hdl.sendMessage(msg);
		}
	}

	class PicHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			Bitmap myimg = (Bitmap) msg.obj;
			imgView.setImageBitmap(myimg);
		}

	}

	public Bitmap getUrlImage(String url) {
		Bitmap img = null;
		try {
			URL picurl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) picurl
					.openConnection();
			conn.setConnectTimeout(6000);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.connect();
			InputStream is = conn.getInputStream();
			img = BitmapFactory.decodeStream(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

    	img=zoomImage(img,this.widht,this.height);
		return img;
	}


	public   Bitmap zoomImage(Bitmap bgimage, double newWidth,
								   double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);

		if (CircleSize>0) //需要圆角
		{
			bitmap=bitmapRound(bitmap,CircleSize);
		}
		else  //不需要圆角
		{

		}

		return bitmap;
	}



	private Bitmap bitmapRound(Bitmap mBitmap,float CircleSize){
		Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Config.ARGB_4444);


		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		//设置矩形大小
		Rect rect = new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight());
		RectF rectf = new RectF(rect);

		// 相当于清屏
		canvas.drawARGB(0, 0, 0, 0);
		//画圆角
		canvas.drawRoundRect(rectf, CircleSize, CircleSize, paint);
		// 取两层绘制，显示上层
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

		// 把原生的图片放到这个画布上，使之带有画布的效果
		canvas.drawBitmap(mBitmap, rect, rect, paint);
		return bitmap;

	}
}
