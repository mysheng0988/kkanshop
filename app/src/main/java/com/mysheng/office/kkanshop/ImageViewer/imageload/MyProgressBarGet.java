package com.mysheng.office.kkanshop.ImageViewer.imageload;

import android.content.Context;
import android.view.ViewGroup;

import com.mysheng.office.kkanshop.ImageViewer.listener.ProgressViewGet;
import com.mysheng.office.kkanshop.ImageViewer.view.RingLoadingView;
import com.mysheng.office.kkanshop.KkanApplication;


/**
 * Created by liuting on 18/3/19.
 */

public class MyProgressBarGet implements ProgressViewGet<RingLoadingView> {
    @Override
    public RingLoadingView getProgress(Context context) {
        RingLoadingView view = new RingLoadingView(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(KkanApplication.dpToPx(50), KkanApplication.dpToPx(50)));
        return view;
    }

    @Override
    public void onProgressChange(RingLoadingView view, float progress) {
        view.setProgress(progress);
    }
}
