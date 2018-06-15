package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
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
import com.mysheng.office.kkanshop.adapter.GridImageViewAdapter;
import com.mysheng.office.kkanshop.okHttptils.OkHttpUtils;
import com.mysheng.office.kkanshop.okHttptils.UploadProgressListener;
import com.mysheng.office.kkanshop.util.LoadingDialog;
import com.mysheng.office.kkanshop.util.ReadLoginData;
import com.mysheng.office.kkanshop.view.GridImageView;
import com.squareup.picasso.Picasso;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Response;


public class UploadImageActivity extends Activity implements UploadProgressListener,View.OnClickListener {


    private static final int IMAGE_PHOTO=0x001;
    private static final int CAMERA_PHOTO=0x002;
    private GridImageView<String> mGridImageView;
    private ReadLoginData readLoginData = new ReadLoginData();
    private View inflate;
    private Button button;
    private Dialog dialog;
    private TextView choosePhoto;
    private TextView takePhoto;
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

        uploadFile.setOnClickListener(this);
        title.setText("病历上传");
        button.setOnClickListener(this);
        mGridImageView.setAdapter(new GridImageViewAdapter<String>() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String path) {
                Picasso.with(context).load("file://" + path).centerCrop().resize(400, 400).into(imageView);
            }

            @Override
            public void onAddClick(Context context, List<String> list) {
//                Intent intent=new Intent(UploadImageActivity.this,SelectorActivity.class);
//                startActivityForResult(intent,1234);
                show();
            }

            @Override
            public int getShowStyle() {
                return GridImageView.STYLE_GRID;
            }

            @Override
            public void onItemImageClick(Context context, int index, List<String> list) {
                super.onItemImageClick(context, index, list);
                Toast.makeText(getApplicationContext(), "--->" + index, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void startCameraActivity(){
        PackageManager pm =UploadImageActivity.this.getPackageManager();
        if(! (pm.checkPermission("android.permission.CAMERA", "com.example.hwxnemr.hwxnemr_app")==PackageManager.PERMISSION_GRANTED ) ) {
            ActivityCompat.requestPermissions(UploadImageActivity.this,
                    new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CAMERA_PHOTO);

        }else{
            dir= Environment.getExternalStorageDirectory()+"/mysheng";
            File file=new File(dir);
            if(!file.exists()){
                file.mkdir();
            }
            int fileName= (int) System.currentTimeMillis();
            imagePath=new File(dir,fileName+".png");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(UploadImageActivity.this,imagePath));
            startActivityForResult(intent,CAMERA_PHOTO);
        }

    }
    private Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.example.hwxnemr.hwxnemr_app.fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
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

            String filePath=list1.get(i);
            String fileName=filePath.substring(filePath.lastIndexOf("/"));
            fileMap.put(fileName, filePath);
        }

        OkHttpUtils.getInstance().fileUpload(UploadImageActivity.this, url, fileMap,null);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                uploadFile();
                break;
            case R.id.choosePhoto:
                Intent intent=new Intent(UploadImageActivity.this,SelectorActivity.class);
                startActivityForResult(intent,IMAGE_PHOTO);
                dialog.dismiss();
                break;
            case R.id.takePhoto:
                startCameraActivity();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }

        switch (requestCode) {
            case IMAGE_PHOTO:
                List<String> list = data.getStringArrayListExtra("list");
                //  PictureUtil.cropPhoto(this, Uri.parse("file://"+list.get(0)));
                mGridImageView.setImageData(list,false);
                break;
            case CAMERA_PHOTO:
                File file=new File(imagePath.getPath());
                String str =file.getPath();
                List<String> list2 =new ArrayList<>();
                list2.add(str);
                mGridImageView.setImageData(list2,false);
                break;

        }
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
