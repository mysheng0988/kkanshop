package com.mysheng.office.kkanshop.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;


/**
 * Created by myaheng on 2018/1/10.
 */

public class LoadingDialog extends Dialog {
    private TextView tv;
    public String message=null;
    /**
     * style很关键
     */
    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);

    }
    public LoadingDialog(Context context, String mess) {
        super(context, R.style.loadingDialogStyle);
        this.message=mess;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        tv =  findViewById(R.id.tv);
        if(message==null){
            tv.setText("数据加载中.....");
        }else{
            tv.setText(message);
        }
        LinearLayout linearLayout =  this.findViewById(R.id.LinearLayout);
        linearLayout.getBackground().setAlpha(210);
    }
}
