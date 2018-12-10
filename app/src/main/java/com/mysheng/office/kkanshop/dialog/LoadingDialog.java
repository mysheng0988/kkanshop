package com.mysheng.office.kkanshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mysheng.office.kkanshop.R;


/**
 * Created by myaheng on 2018/1/10.
 */

public class LoadingDialog extends Dialog {
    private TextView tipsMsg;
    private ImageView loadDialog;
    private Context mContext;
    public String message=null;
    /**
     * style很关键
     */
    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
        this.mContext=context;

    }
    public LoadingDialog(Context context, String mess) {
        super(context, R.style.loadingDialogStyle);
        this.mContext=context;
        this.message=mess;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        tipsMsg =  findViewById(R.id.tipsMsg);
        loadDialog =findViewById(R.id.loadDialog);
//        Glide.with(mContext).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).
//                into(loadDialog);

        if(message==null){
            tipsMsg.setText("加载中...");

        }else{
            tipsMsg.setText(message);
        }
    }
}
