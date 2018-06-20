package com.mysheng.office.kkanshop.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

public class TakePhotoSetting {
    /*剪切配置*/
   private boolean isCutting=false;//裁剪
   private  int cutWidth=800;
   private int cutHeight=800;
   private boolean cutTool=false;//默默为第三方，
    private  boolean imageType=true;//是否比例压缩
    /*压缩配置*/
    private  boolean compressTool=false;//默默为第三方
    private  boolean isCompress=true;//是否压缩
    private  boolean isShowComProgress=true;//是否显示压缩进度条
    private  int maxFileSize =307200;//文件最大102400B
    private int compressWidth=1920;
    private int compressHeight=1080;
    /*选择图片配置*/
    private  boolean photoTool=true;//默默TakePhone自己，limit>1时自动选择TakePhone自己
    private  int limit =10;//选择最多图片个数
    private boolean whereIsPhoto=true;//默认为从相册
    /*其他配置*/
    private boolean correctRotate=true;//纠正旋转角度
    private boolean isOldPhoto=true;//是否保留原图
    public void pickBySelectImage(TakePhoto takePhoto){
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        if (limit > 1) {
            if (isCutting) {
                takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
            } else {
                takePhoto.onPickMultiple(limit);
            }
            return;
        }
        if (!whereIsPhoto) {
            if (isCompress) {
                takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
            } else {
                takePhoto.onPickFromDocuments();
            }
            return;
        } else {
            if (isCompress) {
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
            } else {
                takePhoto.onPickFromGallery();
            }
        }
    }

    public void pickByTakeImage(TakePhoto takePhoto){
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        if (isCutting) {
            takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
        } else {
            takePhoto.onPickFromCapture(imageUri);
        }
    }
    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        if (photoTool) {
            builder.setWithOwnGallery(true);
        }
        if (correctRotate) {
            builder.setCorrectImage(true);
        }
        takePhoto.setTakePhotoOptions(builder.create());

    }
    private void configCompress(TakePhoto takePhoto){
        if (!isCompress) {
            takePhoto.onEnableCompress(null, false);
            return;
        }
        int maxSize = maxFileSize;
        int width = compressWidth;
        int height = compressHeight;
        CompressConfig config;

        if (compressTool) {
            config = new CompressConfig.Builder().setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(isOldPhoto)
                    .create();
        } else {
            LubanOptions option = new LubanOptions.Builder().setMaxHeight(height).setMaxWidth(width).setMaxSize(maxSize).create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(isOldPhoto);
        }
        takePhoto.onEnableCompress(config, isShowComProgress);

    }
    private CropOptions getCropOptions() {
        if (isCutting) {
            CropOptions.Builder builder = new CropOptions.Builder();
            if(imageType) {
                builder.setAspectX(cutWidth).setAspectY(cutHeight);
            } else {
                builder.setOutputX(cutWidth).setOutputY(cutHeight);
            }
            builder.setWithOwnCrop(cutTool);
            return builder.create();

        }
        return null;
    }

    public boolean isImageType() {
        return imageType;
    }

    public void setImageType(boolean imageType) {
        this.imageType = imageType;
    }

    public boolean isCutting() {
        return isCutting;
    }

    public void setCutting(boolean cutting) {
        isCutting = cutting;
    }

    public int getCutWidth() {
        return cutWidth;
    }

    public void setCutWidth(int cutWidth) {
        this.cutWidth = cutWidth;
    }

    public int getCutHeight() {
        return cutHeight;
    }

    public void setCutHeight(int cutHeight) {
        this.cutHeight = cutHeight;
    }

    public boolean isCutTool() {
        return cutTool;
    }

    public void setCutTool(boolean cutTool) {
        this.cutTool = cutTool;
    }

    public boolean isCompressTool() {
        return compressTool;
    }

    public void setCompressTool(boolean compressTool) {
        this.compressTool = compressTool;
    }

    public boolean isCompress() {
        return isCompress;
    }

    public void setCompress(boolean compress) {
        isCompress = compress;
    }

    public boolean isShowComProgress() {
        return isShowComProgress;
    }

    public void setShowComProgress(boolean showComProgress) {
        isShowComProgress = showComProgress;
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public int getCompressWidth() {
        return compressWidth;
    }

    public void setCompressWidth(int compressWidth) {
        this.compressWidth = compressWidth;
    }

    public int getCompressHeight() {
        return compressHeight;
    }

    public void setCompressHeight(int compressHeight) {
        this.compressHeight = compressHeight;
    }

    public boolean isPhotoTool() {
        return photoTool;
    }

    public void setPhotoTool(boolean photoTool) {
        this.photoTool = photoTool;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isWhereIsPhoto() {
        return whereIsPhoto;
    }

    public void setWhereIsPhoto(boolean whereIsPhoto) {
        this.whereIsPhoto = whereIsPhoto;
    }

    public boolean isCorrectRotate() {
        return correctRotate;
    }

    public void setCorrectRotate(boolean correctRotate) {
        this.correctRotate = correctRotate;
    }

    public boolean isOldPhoto() {
        return isOldPhoto;
    }

    public void setOldPhoto(boolean oldPhoto) {
        isOldPhoto = oldPhoto;
    }
}
