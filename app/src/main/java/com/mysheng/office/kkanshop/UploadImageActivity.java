package com.mysheng.office.kkanshop;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.mysheng.office.kkanshop.adapter.GridImageViewAdapter;
import com.mysheng.office.kkanshop.imagesWatcher.ImageWatcher;
import com.mysheng.office.kkanshop.okHttptils.OkHttpUtils;
import com.mysheng.office.kkanshop.okHttptils.UploadProgressListener;
import com.mysheng.office.kkanshop.util.LoadingDialog;
import com.mysheng.office.kkanshop.util.TakePhotoSetting;
import com.mysheng.office.kkanshop.util.Utils;
import com.mysheng.office.kkanshop.view.GridImageView;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;


public class UploadImageActivity extends TakePhotoActivity implements UploadProgressListener,View.OnClickListener {

    private GridImageView<String> mGridImageView;
    private View inflate;
    private Button button;
    private Dialog dialog;
    private Button choosePhoto;
    private Button takePhoto;
    private TextView title;
    private EditText remark;
    private RadioGroup radioGroup;
    private LinearLayout uploadFile;
    private File imagePath;
    private String dir;
    private String PATRURL="";
    private String loginUser_id;
    private String SessionKey;
    private LoadingDialog mDialog;
    private TakePhotoSetting takePhotoSetting;
    private TakePhoto mTakePhoto;
    boolean isTranslucentStatus = false;
    private ImageWatcher vImageWatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_layout);
        mGridImageView= findViewById(R.id.gridImageView);
        button=findViewById(R.id.button);
        title= findViewById(R.id.common_title);
        remark= findViewById(R.id.remark);
        radioGroup=findViewById(R.id.radioGroupID);
        uploadFile=findViewById(R.id.uploadFile);
        takePhotoSetting=new TakePhotoSetting();
        mTakePhoto=getTakePhoto();
        takePhotoSetting.initTakePhoto(mTakePhoto);
        uploadFile.setOnClickListener(this);
        //initImageWatcher();
        title.setText("商品评价");
        button.setOnClickListener(this);
        // 初始化方式一，在Activity对应布局文件加入<ImageWatcher>标签
        vImageWatcher = (ImageWatcher) findViewById(R.id.v_image_watcher); // 一般来讲， ImageWatcher 需要占据全屏的位置
        vImageWatcher.setTranslucentStatus(!isTranslucentStatus ? Utils.calcStatusBarHeight(this) : 0);  // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
        vImageWatcher.setErrorImageRes(R.mipmap.error_picture);  // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
        // vImageWatcher.setOnPictureLongPressListener(UploadImageActivity.this); // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
        vImageWatcher.setLoader(new ImageWatcher.Loader() {//调用show方法前，请先调用setLoader 给ImageWatcher提供加载图片的实现
            @Override
            public void load(Context context, String url, final ImageWatcher.LoadCallback lc) {
                Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        lc.onResourceReady(resource);
                    }
                });
            }
        });
        mGridImageView.setAdapter(new GridImageViewAdapter<String>() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String path) {
                Glide.with(context).load("file://"+path).centerCrop().override(400, 400).into(imageView);
            }

            @Override
            public void onAddClick(Context context, List<String> list) {
                show();
            }

            @Override
            public int getShowStyle() {
                return GridImageView.STYLE_GRID;
            }

            @Override
            public void onItemImageClick(ImageView imageView,List<ImageView> imageViews,int index, List<String> list) {
                super.onItemImageClick(imageView,imageViews,index, list);
                vImageWatcher.show(imageView,imageViews,list);
                Toast.makeText(getApplicationContext(), "--->" + index, Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void uploadFile(){
        mDialog=new LoadingDialog(this,"文件上传中...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        List<String> list1=mGridImageView.getImgDataList();
        int id = radioGroup.getCheckedRadioButtonId();
        // 通过id实例化选中的这个RadioButton
        RadioButton choise =findViewById(id);
        // 获取这个RadioButton的text内容
        String uploadType = choise.getText().toString();
        String strRemark=remark.getText().toString().trim();
        String url = PATRURL+"/UploadRec?loginUser_id="+loginUser_id+"&" +
                "SessionKey="+SessionKey+"&sent_type=image&" +
                "REC_TITLE="+uploadType+"&MESS_NOTE="+strRemark;
        Map<String, String> fileMap = new HashMap<String, String>();
        for (int i=0;i<list1.size();i++){

            String filePath= (String) list1.get(i);
            String fileName=filePath.substring(filePath.lastIndexOf("/"));
            fileMap.put(fileName, filePath);
        }

        OkHttpUtils.getInstance().fileUpload(UploadImageActivity.this, url, fileMap,null);

    }
    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        List<String>list=new ArrayList<>();
        ArrayList<TImage> images=result.getImages();

        for (int i = 0;i<images.size(); i ++) {
            Log.d("mmm", "takeSuccess: "+images.get(i).getCompressPath());
            list.add(images.get(i).getCompressPath());

        }

        Message msg =new Message();
        msg.obj = list;//可以是基本类型，可以是对象，可以是List、map等；
        mHandler.sendMessage(msg);
        //mGridImageView.setImageData(list,false);

    }
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.obj!=null){
                List<String> list= (List<String>) msg.obj;
                mGridImageView.setImageData(list,false);

            }


        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                uploadFile();
                break;
            case R.id.choosePhoto:
                 takePhotoSetting.pickBySelectImage(mTakePhoto);
                dialog.dismiss();
                break;
            case R.id.takePhoto:
                takePhotoSetting.pickByTakeImage(mTakePhoto);
                dialog.dismiss();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.uploadFile:
                editTextClearFocus();
                break;
            default:
                break;


        }
    }
    private void editTextClearFocus(){
        Log.d("ClearFocus", "onClick: "+2222);
        uploadFile.setFocusable(true);
        uploadFile.setFocusableInTouchMode(true);
        uploadFile.requestFocus();
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(uploadFile.getWindowToken(), 0);
    }
    public void show(){
        dialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_button_layout, null);
        //初始化控件
        choosePhoto =  inflate.findViewById(R.id.choosePhoto);
        takePhoto = inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.95
        lp.y = 20;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    /**
     * 上传成功
     *
     * @param response
     */
    @Override
    public void onUploadSuccess(Response response) {
        Log.d("mys", "onUploadSuccess: "+response);
        mGridImageView.clear();
        //CustomToast.getInstance(this,"上传成功");
        mDialog.dismiss();
    }

    /**
     * 上传失败
     *
     * @param e
     */
    @Override
    public void onUploadFailed(Exception e) {
        Log.d("mys", "onUploadFailed: "+e);
       // CustomToast.getInstance(this,"上传失败");
        mDialog.dismiss();
    }

    /**
     * 上传时进度
     *
     * @param bytesWritten  当前进度
     * @param contentLength 总大小
     * @param done          是否上传完成
     * @param fileName      上传文件的文件名
     */
    @Override
    public void onUploadProgress(long bytesWritten, long contentLength, boolean done, String fileName) {
        Log.d("mys", "bytesWritten: "+bytesWritten);
        Log.d("mys", "contentLength: "+contentLength);
        Log.d("mys", "done: "+done);
        Log.d("mys", "fileName: "+fileName);
    }

    public void onBtnClick(View view){
        finish();
    }

}
