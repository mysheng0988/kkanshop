package com.mysheng.office.kkanshop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.KkanApplication;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.adapter.GoodsListAdapter;
import com.mysheng.office.kkanshop.adapter.SecKillAdapter;
import com.mysheng.office.kkanshop.entity.GoodsModel;
import com.mysheng.office.kkanshop.entity.LabelModel;
import com.mysheng.office.kkanshop.view.AmountView;
import com.mysheng.office.kkanshop.view.LabelsView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by myaheng on 2018/10/11.
 */

public class ListDialog extends Dialog implements View.OnClickListener{

    private Context mContext;
    private OnCloseListener listener;
    private ImageView closed;
    private RecyclerView dialogView;
    private GoodsListAdapter listAdapter;
    private List<GoodsModel> goodsModels=new ArrayList<>();


    public ListDialog(Context context, List<GoodsModel> goodsModels) {
        super(context, R.style.bottom_dialog);
        this.mContext = context;
        this.goodsModels=goodsModels;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_dialog_layout);
        initView();
    }

    private void initView(){
        closed=findViewById(R.id.closed);
        dialogView=findViewById(R.id.dialogView);
        closed.setOnClickListener(this);
        if(listAdapter==null){
            listAdapter=new GoodsListAdapter(mContext);
        }else{
            listAdapter.notifyDataSetChanged();
        }
        listAdapter.setData(goodsModels);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dialogView.setLayoutManager(linearLayoutManager);
        dialogView.setAdapter(listAdapter);

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

