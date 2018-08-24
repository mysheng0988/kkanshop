package com.mysheng.office.kkanshop.ImageViewer;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;

;

import com.mysheng.office.kkanshop.ImageViewer.listener.ProgressViewGet;
import com.mysheng.office.kkanshop.ImageViewer.listener.SourceImageViewGet;

import java.util.List;


/**
 * Created by liuting on 17/5/27.
 */

public class ImageTrans implements DialogInterface.OnShowListener,
        DialogInterface.OnKeyListener, DialogInterface {
    private Dialog mDialog;
    private ImageTransBuild build;
    private DialogView dialogView;
    private Context mContext;

    public static ImageTrans with(Context context) {
        return new ImageTrans(context);
    }

    ImageTrans(Context context) {
        this.mContext = context;
        build = new ImageTransBuild();
    }

    public ImageTrans setNowIndex(int index) {
        build.clickIndex = index;
        build.nowIndex = index;
        return this;
    }

    public ImageTrans setImageList(List<String> imageList) {
        build.imageList = imageList;
        return this;
    }

    public ImageTrans setSourceImageView(SourceImageViewGet sourceImageView) {
        build.sourceImageViewGet = sourceImageView;
        return this;
    }

    public ImageTrans setAdapter(ImageTransAdapter adapter) {
        build.imageTransAdapter = adapter;
        return this;
    }

    public ImageTrans setImageLoad(ImageLoad imageLoad) {
        build.imageLoad = imageLoad;
        return this;
    }

    public ImageTrans setScaleType(ScaleType scaleType) {
        build.scaleType = scaleType;
        return this;
    }

    public ImageTrans setConfig(ITConfig itConfig) {
        build.itConfig = itConfig;
        return this;
    }

    public ImageTrans setProgressBar(ProgressViewGet progressViewGet) {
        build.progressViewGet = progressViewGet;
        return this;
    }

    private View createView() {
        dialogView = new DialogView(mContext, build);
        return dialogView;
    }

    private int getDialogStyle() {
        int dialogStyle;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            dialogStyle = android.R.style.Theme_Translucent_NoTitleBar_Fullscreen;
        } else {
            dialogStyle = android.R.style.Theme_Translucent_NoTitleBar;
        }
        return dialogStyle;
    }

    public void show() {
        build.checkParam();
        mDialog = new AlertDialog.Builder(mContext, getDialogStyle())
                .setView(createView())
                .create();
        build.dialog = mDialog;
        mDialog.setOnShowListener(this);
        mDialog.setOnKeyListener(this);
        mDialog.show();
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP &&
                !event.isCanceled()) {
            dialogView.onDismiss(mDialog);
            return true;
        }
        return false;
    }


    @Override
    public void onShow(DialogInterface dialog) {
        dialogView.onCreate(this);
    }

    @Override
    public void cancel() {
        dismiss();
    }

    @Override
    public void dismiss() {
        dialogView.onDismiss(mDialog);
    }

}
