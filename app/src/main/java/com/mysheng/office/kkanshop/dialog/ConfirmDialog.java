package com.mysheng.office.kkanshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;

/**
 * Created by myaheng on 2018/10/11.
 */

public class ConfirmDialog extends Dialog implements View.OnClickListener{
    private LinearLayout onlyBtn;
    private LinearLayout twoBtn;
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView submitOnlyTxt;
    private TextView cancelTxt;
    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;

    private boolean only=false;

    public ConfirmDialog(Context context) {
        super(context);
        this.mContext = context;
    }
    public ConfirmDialog(Context context, String content) {
        super(context, R.style.dialogCustom);
        this.only=only;
        this.mContext = context;
        this.content = content;
    }
    public ConfirmDialog(Context context,boolean only, String content) {
        super(context, R.style.dialogCustom);
        this.only=only;
        this.mContext = context;
        this.content = content;
    }
    public ConfirmDialog(Context context, String content, OnCloseListener listener) {
        super(context, R.style.dialogCustom);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    public ConfirmDialog(Context context,boolean only, String content, OnCloseListener listener) {
        super(context, R.style.dialogCustom);
        this.only=only;
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected ConfirmDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public ConfirmDialog setTitle(String title){
        this.title = title;
        return this;
    }

    public ConfirmDialog setPositiveButton(String name){
        this.positiveName = name;
        return this;
    }

    public ConfirmDialog setNegativeButton(String name){
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_dialog);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        contentTxt = findViewById(R.id.content);
        onlyBtn = findViewById(R.id.onlyBtn);
        twoBtn = findViewById(R.id.twoBtn);
        titleTxt = findViewById(R.id.title);
        submitTxt = findViewById(R.id.submit);
        submitOnlyTxt = findViewById(R.id.submitOnly);
        submitTxt.setOnClickListener(this);
        submitOnlyTxt.setOnClickListener(this);
        cancelTxt = findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);
        if(only){
            onlyBtn.setVisibility(View.VISIBLE);
            twoBtn.setVisibility(View.GONE);
        }else {
            onlyBtn.setVisibility(View.GONE);
            twoBtn.setVisibility(View.VISIBLE);
        }
        contentTxt.setText(content);
        if(!TextUtils.isEmpty(positiveName)){
            submitTxt.setText(positiveName);
            submitOnlyTxt.setText(positiveName);
        }

        if(!TextUtils.isEmpty(negativeName)){
            cancelTxt.setText(negativeName);
        }

        if(!TextUtils.isEmpty(title)){
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                if(listener != null){
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
            case R.id.submitOnly:
                if(listener != null){
                    listener.onClick(this, true);
                }
                this.dismiss();
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }
}

