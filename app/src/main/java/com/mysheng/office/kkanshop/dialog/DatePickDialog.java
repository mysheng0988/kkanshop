package com.mysheng.office.kkanshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.util.UtilsDate;
import com.mysheng.office.kkanshop.view.DatePickView;

/**
 * Created by myaheng on 2018/10/11.
 */

public class DatePickDialog extends Dialog implements View.OnClickListener,DatePickView.ReturnTimeListener{

    private Context mContext;
    private OnConfirmListener listener;
    private TextView cancel;
    private TextView confirm;
    private TextView dateTime;
    private DatePickView datePickView;



    public DatePickDialog(Context context) {
        super(context, R.style.date_dialog);
        this.mContext = context;
    }





    protected DatePickDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_date_view);
        initView();
    }

    private void initView(){
        cancel=findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        confirm=findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        dateTime=findViewById(R.id.dateTime);
        datePickView=findViewById(R.id.datePickView);
        datePickView.setReturnTimeListener(this);
        dateTime.setText(datePickView.getDisplayTime());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                dismiss();
                break;
            case R.id.confirm:
                if (listener!=null){
                    listener.onClick(dateTime.getText().toString());
                }
                dismiss();
                break;
        }
    }

    public void setListener(OnConfirmListener listener) {
        this.listener = listener;
    }

    @Override
    public void returnTime(long time) {
        dateTime.setText(UtilsDate.getTime(time));
    }

    public interface OnConfirmListener{
        void onClick( String dateStr);
    }



    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
    }
    public int dp2px(Context context, float dp) {
        return (int) Math.ceil((double)(context.getResources().getDisplayMetrics().density * dp));
    }
}

