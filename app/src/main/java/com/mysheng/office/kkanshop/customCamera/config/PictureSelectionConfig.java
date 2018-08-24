package com.mysheng.office.kkanshop.customCamera.config;

import android.support.annotation.StyleRes;


import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.customCamera.entity.LocalMedia;
import com.mysheng.office.kkanshop.customCamera.util.DebugUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib.config
 * email：893855882@qq.com
 * data：2017/5/24
 */

public final class PictureSelectionConfig implements Serializable {
    public int mimeType;
    public boolean camera;
    public String outputCameraPath;
    @StyleRes
    public int themeStyleId;
    public int selectionMode;
    public int maxSelectNum;
    public int minSelectNum;
    public int videoQuality;
    public int videoSecond;
    public int recordVideoSecond;
    public int compressMaxkB;
    public int compressGrade;
    public int minimumCompressSize;//add by tanhaiqin
    public int imageSpanCount;
    public int compressMode;
    public int compressWidth;
    public int compressHeight;
    public int overrideWidth;
    public int overrideHeight;
    public float sizeMultiplier;
    public boolean zoomAnim;
    public boolean isCompress;
    public boolean isCamera;
    public boolean isGif;
    public boolean enablePreview;
    public boolean enPreviewVideo;
    public boolean enablePreviewAudio;
    public boolean checkNumMode;
    public boolean openClickSound;
    public boolean previewEggs;

    public List<LocalMedia> selectionMedias;

    private void reset() {
        mimeType = PictureConfig.TYPE_IMAGE;
        camera = false;
        themeStyleId = R.style.AppTheme;
        selectionMode = PictureConfig.MULTIPLE;
        maxSelectNum = 9;
        minSelectNum = 0;
        videoQuality = 1;
        videoSecond = 0;
        recordVideoSecond = 60;
        compressMaxkB = PictureConfig.MAX_COMPRESS_SIZE;
        minimumCompressSize = PictureConfig.MAX_COMPRESS_SIZE;//add by tanhaiqin
        //compressGrade = Luban.THIRD_GEAR;
        imageSpanCount = 4;
        compressMode = PictureConfig.LUBAN_COMPRESS_MODE;
        compressWidth = 1080;
        compressHeight = 1920;
        overrideWidth = 1080;
        overrideHeight = 1920;
        sizeMultiplier = 0.5f;
        isCompress = false;
        enablePreviewAudio = true;
        isCamera = true;
        isGif = false;
        enablePreview = true;
        enPreviewVideo = true;
        checkNumMode = false;
        openClickSound = false;
        previewEggs = false;
        zoomAnim = true;
        outputCameraPath = "";
        selectionMedias = new ArrayList<>();
        DebugUtil.i("*******", "reset PictureSelectionConfig");
    }

    public static PictureSelectionConfig getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static PictureSelectionConfig getCleanInstance() {
        PictureSelectionConfig selectionSpec = getInstance();
        selectionSpec.reset();
        return selectionSpec;
    }

    private static final class InstanceHolder {
        private static final PictureSelectionConfig INSTANCE = new PictureSelectionConfig();
    }
}
