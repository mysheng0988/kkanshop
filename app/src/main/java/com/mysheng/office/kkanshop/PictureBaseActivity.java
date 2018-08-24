package com.mysheng.office.kkanshop;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;
import com.mysheng.office.kkanshop.customCamera.compress.CompressConfig;
import com.mysheng.office.kkanshop.customCamera.compress.CompressImageOptions;
import com.mysheng.office.kkanshop.customCamera.compress.CompressInterface;
import com.mysheng.office.kkanshop.customCamera.compress.LubanOptions;
import com.mysheng.office.kkanshop.customCamera.config.PictureConfig;
import com.mysheng.office.kkanshop.customCamera.config.PictureMimeType;
import com.mysheng.office.kkanshop.customCamera.config.PictureSelectionConfig;
import com.mysheng.office.kkanshop.customCamera.config.PictureSelector;
import com.mysheng.office.kkanshop.customCamera.dialog.PictureDialog;
import com.mysheng.office.kkanshop.customCamera.entity.LocalMedia;
import com.mysheng.office.kkanshop.customCamera.util.AttrsUtils;
import com.mysheng.office.kkanshop.customCamera.util.DateUtils;
import com.mysheng.office.kkanshop.customCamera.util.DebugUtil;
import com.mysheng.office.kkanshop.customCamera.util.DoubleUtils;
import com.mysheng.office.kkanshop.customCamera.util.PictureFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PictureBaseActivity extends FragmentActivity {
    protected Context mContext;
    protected PictureSelectionConfig config;
    protected int spanCount, maxSelectNum, minSelectNum,
            selectionMode, mimeType, videoSecond, compressMaxKB, compressMode,
            compressGrade, compressWidth, compressHeight, miniCompressSize, //add by tanhaiqin
            recordVideoSecond, videoQuality;
    protected boolean isGif, isCamera, enablePreview, isCompress,
            enPreviewVideo, checkNumMode, openClickSound, numComplete, camera,
            previewEggs, statusFont, previewStatusFont;
    protected String cameraPath;
    protected String originalPath, outputCameraPath;
    protected PictureDialog dialog;
    protected PictureDialog compressDialog;
    protected List<LocalMedia> selectionMedias;
    protected List<LocalMedia> miniCompressMedias;//add by tanhaiqin 存储不需要压缩的视频List


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            config = (PictureSelectionConfig) savedInstanceState.getSerializable(PictureConfig.EXTRA_CONFIG);
            cameraPath = savedInstanceState.getString(PictureConfig.BUNDLE_CAMERA_PATH);
            originalPath = savedInstanceState.getString(PictureConfig.BUNDLE_ORIGINAL_PATH);

        } else {
            config = PictureSelectionConfig.getInstance();
        }

        int themeStyleId = config.themeStyleId;
        setTheme(themeStyleId);
        super.onCreate(savedInstanceState);
        mContext = this;
        initConfig();
    }

    /**
     * 获取配置参数
     */
    private void initConfig() {
        camera = config.camera;
        outputCameraPath = config.outputCameraPath;
        statusFont = AttrsUtils.getTypeValueBoolean
                (this, R.attr.picture_statusFontColor);
        previewStatusFont = AttrsUtils.getTypeValueBoolean
                (this, R.attr.picture_preview_statusFontColor);
        mimeType = config.mimeType;
        selectionMedias = config.selectionMedias;
        if (selectionMedias == null) {
            selectionMedias = new ArrayList<>();
        }
        //add by tanhaiqin
        if (miniCompressMedias == null) {
            miniCompressMedias = new ArrayList<>();
        }
        //end
        selectionMode = config.selectionMode;
        if (selectionMode == PictureConfig.SINGLE) {
            selectionMedias = new ArrayList<>();
        }
        spanCount = config.imageSpanCount;
        isGif = config.isGif;
        isCamera = config.isCamera;
        maxSelectNum = config.maxSelectNum;
        minSelectNum = config.minSelectNum;
        enablePreview = config.enablePreview;
        enPreviewVideo = config.enPreviewVideo;
        checkNumMode = config.checkNumMode = AttrsUtils.getTypeValueBoolean
                (this, R.attr.picture_style_checkNumMode);
        openClickSound = config.openClickSound;
        videoSecond = config.videoSecond;
        isCompress = config.isCompress;
        numComplete = AttrsUtils.getTypeValueBoolean(this, R.attr.picture_style_numComplete);
        compressMaxKB = config.compressMaxkB;
        miniCompressSize = config.minimumCompressSize;//add by tanhaiqin
        //compressMode = config.compressMode;
        compressMode = PictureConfig.LUBAN_COMPRESS_MODE;
        compressGrade = config.compressGrade;
        compressWidth = config.compressWidth;
        compressHeight = config.compressHeight;
        recordVideoSecond = config.recordVideoSecond;
        videoQuality = config.videoQuality;
        previewEggs = config.previewEggs;
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(PictureConfig.BUNDLE_CAMERA_PATH, cameraPath);
        outState.putString(PictureConfig.BUNDLE_ORIGINAL_PATH, originalPath);
        outState.putSerializable(PictureConfig.EXTRA_CONFIG, config);
    }

    protected void startActivity(Class clz, Bundle bundle) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(this, clz);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    protected void startActivity(Class clz, Bundle bundle, int requestCode) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(this, clz);
            intent.putExtras(bundle);
            startActivityForResult(intent, requestCode);
        }
    }

    protected void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * loading dialog
     */
    protected void showPleaseDialog(String s) {
        if (!isFinishing()) {
            dismissDialog();
            dialog = new PictureDialog(this);
            if (!TextUtils.isEmpty(s))
                dialog.setLoadText(s);
            dialog.show();
        }
    }

    /**
     * dismiss dialog
     */
    protected void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * compress loading dialog
     */
    protected void showCompressDialog(String text) {
        if (!isFinishing()) {
            dismissCompressDialog();
            compressDialog = new PictureDialog(this);
            if (!TextUtils.isEmpty(text))
                compressDialog.setLoadText(text);
            compressDialog.show();
        }
    }

    /**
     * dismiss compress dialog
     */
    protected void dismissCompressDialog() {
        try {
            if (!isFinishing()
                    && compressDialog != null
                    && compressDialog.isShowing()) {
                compressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * compressImage
     */
    protected void compressImage(final List<LocalMedia> result) {
        showCompressDialog(getString(R.string.msg_progressing));
        CompressConfig compress_config = CompressConfig.ofDefaultConfig();

        DebugUtil.e("TANHQ===> result.size = " + result.size());

        switch (compressMode) {
            case PictureConfig.SYSTEM_COMPRESS_MODE:
                // 系统自带压缩
                compress_config.enablePixelCompress(true);
                compress_config.enableQualityCompress(true);
                compress_config.setMaxSize(compressMaxKB);
                compress_config.setMiniCompressSize(miniCompressSize);//add by tanhaiqin
                break;

            case PictureConfig.LUBAN_COMPRESS_MODE:
                // luban压缩
                LubanOptions option = new LubanOptions.Builder()
                        .setMaxHeight(compressHeight)
                        .setMaxWidth(compressWidth)
                        .setMaxSize(compressMaxKB)
                        .setMiniCompressSize(miniCompressSize)
                        .setGrade(compressGrade)
                        .create();
                compress_config = CompressConfig.ofLuban(option);
                break;
        }

        CompressImageOptions.compress(this, compress_config, result,
                new CompressInterface.CompressListener() {
                    @Override
                    public void onCompressSuccess(List<LocalMedia> images) {
                        //add by tanhaiqin
                        for (int i = 0; i < images.size(); i++) {
                            LocalMedia localMedia = images.get(i);
                            File file = new File(localMedia.getPath());

                            if (file.exists() && (file.length() <= miniCompressSize)) {
                                DebugUtil.e("TANHQ===> file.size = " + file.length() + ",  不需要使用压缩的路径");
//                                DebugUtil.e("TANHQ===> media.path = " + localMedia.getPath()
//                                        + ", compress.path = " + localMedia.getCompressPath());

                                //压缩完成后， 若原图大小 <= miniCompressSize, 直接将图片压缩路径设置为""
                                images.get(i).setCompressPath("");
                            }
                        }
                        //end

                        //RxBus.getDefault().post(new EventEntity(PictureConfig.CLOSE_PREVIEW_FLAG));
                        onResult(images);
                    }

                    @Override
                    public void onCompressError(List<LocalMedia> images, String msg) {
                        //RxBus.getDefault().post(new EventEntity(PictureConfig.CLOSE_PREVIEW_FLAG));
                        onResult(result);
                    }
                }).compress();
    }

//    /**
//     * originalImage
//     * 原图发送
//     */
//    protected void originalImage(final List<LocalMedia> result) {
//        RxBus.getDefault().post(new EventEntity(PictureConfig.CLOSE_PREVIEW_FLAG));
//        onResult(result);
//    }

    /**
     * 判断拍照 图片是否旋转
     *
     * @param degree
     * @param file
     */
    protected void rotateImage(int degree, File file) {
        if (degree > 0) {
            // 针对相片有旋转问题的处理方式
            try {
                BitmapFactory.Options opts = new BitmapFactory.Options();//获取缩略图显示到屏幕上
                opts.inSampleSize = 2;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
                Bitmap bmp = PictureFileUtils.rotaingImageView(degree, bitmap);
                PictureFileUtils.saveBitmapFile(bmp, file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * compress or callback
     *
     * @param result
     */
    protected void handlerResult(List<LocalMedia> result) {
        if (isCompress) {
            compressImage(result);
        } else {
            onResult(result);
        }
    }


    /**
     * 如果没有任何相册，先创建一个最近相册出来
     *
     * @param folders
     */
//    protected void createNewFolder(List<LocalMediaFolder> folders) {
//        if (folders.size() == 0) {
//            // 没有相册 先创建一个最近相册出来
//            LocalMediaFolder newFolder = new LocalMediaFolder();
//            String folderName = mimeType == PictureMimeType.ofAudio() ?
//                    getString(R.string.picture_all_audio) : getString(R.string.picture_camera_roll);
//            newFolder.setName(folderName);
//            newFolder.setPath("");
//            newFolder.setFirstImagePath("");
//            folders.add(newFolder);
//        }
//    }

    /**
     * 将图片插入到相机文件夹中
     *
     * @param path
     * @param imageFolders
     * @return
     */
//    protected LocalMediaFolder getImageFolder(String path, List<LocalMediaFolder> imageFolders) {
//        File imageFile = new File(path);
//        File folderFile = imageFile.getParentFile();
//
//        for (LocalMediaFolder folder : imageFolders) {
//            if (folder.getName().equals(folderFile.getName())) {
//                return folder;
//            }
//        }
//        LocalMediaFolder newFolder = new LocalMediaFolder();
//        newFolder.setName(folderFile.getName());
//        newFolder.setPath(folderFile.getAbsolutePath());
//        newFolder.setFirstImagePath(path);
//        imageFolders.add(newFolder);
//        return newFolder;
//    }

    /**
     * return image result
     *
     * @param images
     */
    protected void onResult(List<LocalMedia> images) {
        dismissCompressDialog();
        if (camera
                && selectionMode == PictureConfig.MULTIPLE
                && selectionMedias != null) {
            images.addAll(selectionMedias);
        }
        Intent intent = PictureSelector.putIntentResult(images);
        setResult(RESULT_OK, intent);
        closeActivity();
    }

    /**
     * Close Activity
     */
    protected void closeActivity() {
        finish();
        overridePendingTransition(0, R.anim.a3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissCompressDialog();
        dismissDialog();
    }


    /**
     * 获取DCIM文件下最新一条拍照记录
     *
     * @return
     */
    protected int getLastImageId(boolean eqVideo) {
        try {
            //selection: 指定查询条件
            String absolutePath = PictureFileUtils.getDCIMCameraPath();
            String ORDER_BY = MediaStore.Files.FileColumns._ID + " DESC";
            String selection = eqVideo ? MediaStore.Video.Media.DATA + " like ?" :
                    MediaStore.Images.Media.DATA + " like ?";
            //定义selectionArgs：
            String[] selectionArgs = {absolutePath + "%"};
            Cursor imageCursor = this.getContentResolver().query(eqVideo ?
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            : MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                    selection, selectionArgs, ORDER_BY);
            if (imageCursor.moveToFirst()) {
                int id = imageCursor.getInt(eqVideo ?
                        imageCursor.getColumnIndex(MediaStore.Video.Media._ID)
                        : imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
                long date = imageCursor.getLong(eqVideo ?
                        imageCursor.getColumnIndex(MediaStore.Video.Media.DURATION)
                        : imageCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                int duration = DateUtils.dateDiffer(date);
                imageCursor.close();
                // DCIM文件下最近时间30s以内的图片，可以判定是最新生成的重复照片
                return duration <= 30 ? id : -1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 删除部分手机 拍照在DCIM也生成一张的问题
     *
     * @param id
     * @param eqVideo
     */
    protected void removeImage(int id, boolean eqVideo) {
        try {
            ContentResolver cr = getContentResolver();
            Uri uri = eqVideo ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    : MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String selection = eqVideo ? MediaStore.Video.Media._ID + "=?"
                    : MediaStore.Images.Media._ID + "=?";
            cr.delete(uri,
                    selection,
                    new String[]{Long.toString(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 录音
     *
     * @param data
     */
    protected void isAudio(Intent data) {
        if (data != null && mimeType == PictureMimeType.ofAudio()) {
            try {
                Uri uri = data.getData();
                String audioPath;
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                    audioPath = uri.getPath();
                } else {
                    audioPath = getAudioFilePathFromUri(uri);
                }
                PictureFileUtils.copyAudioFile(audioPath, cameraPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取刚录取的音频文件
     *
     * @param uri
     * @return
     */
    protected String getAudioFilePathFromUri(Uri uri) {
        String path = "";
        try {
            Cursor cursor = getContentResolver()
                    .query(uri, null, null, null, null);
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA);
            path = cursor.getString(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
