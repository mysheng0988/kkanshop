package com.mysheng.office.kkanshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.address.utils.Dev;
import com.mysheng.office.kkanshop.address.widget.AddressSelector;
import com.mysheng.office.kkanshop.entity.LabelModel;
import com.mysheng.office.kkanshop.view.AmountView;
import com.mysheng.office.kkanshop.view.LabelsView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by myaheng on 2018/10/11.
 */

public class GoodsParamDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private OnCloseListener listener;
    private LabelsView goodsTypeParam1;
    private LabelsView goodsTypeParam2;
    private ImageView closed;
    private AmountView amountView;

    private List<String> goodsColor= Arrays.asList("红色","黄色","绿色","蓝色","白色","橘黄色","淡绿色");
    private List<String>goodsContent=Arrays.asList("32G","64G","128G","256G");


    public GoodsParamDialog(Context context) {
        super(context, R.style.bottom_dialog);
        this.mContext = context;
    }

    public GoodsParamDialog(Context context, String content) {
        super(context, R.style.bottom_dialog);
        this.mContext = context;
    }




    protected GoodsParamDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_param_dialog);
        initView();
    }

    private void initView(){
        closed=findViewById(R.id.closed);
        closed.setOnClickListener(this);
        goodsTypeParam1=findViewById(R.id.goodsTypeParam1);
        goodsTypeParam2=findViewById(R.id.goodsTypeParam2);
        amountView=findViewById(R.id.amountView);
        amountView.setAmountNum(1,10,1);
        List<LabelModel> labelModels=new ArrayList<>();
            for (int i=0;i<goodsColor.size();i++){
                LabelModel labelModel=new LabelModel();
                labelModel.setLabelId(i);
                labelModel.setLabelTitle(goodsColor.get(i));
                labelModels.add(labelModel);
            }

            List<LabelModel> labelModels2=new ArrayList<>();
            for (int i=0;i<goodsContent.size();i++){
                LabelModel labelModel=new LabelModel();
                labelModel.setLabelId(i);
                labelModel.setLabelTitle(goodsContent.get(i));
                labelModels2.add(labelModel);
            }
        goodsTypeParam1.setLabels(labelModels, new LabelsView.LabelTextProvider<LabelModel>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, LabelModel data) {
                return data.getLabelTitle();
            }
        });
        goodsTypeParam2.setLabels(labelModels2, new LabelsView.LabelTextProvider<LabelModel>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, LabelModel data) {
                return data.getLabelTitle();
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.closed:
                dismiss();
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }



    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height =dp2px(mContext, 600);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
    }
    public int dp2px(Context context, float dp) {
        return (int) Math.ceil((double)(context.getResources().getDisplayMetrics().density * dp));
    }
}

