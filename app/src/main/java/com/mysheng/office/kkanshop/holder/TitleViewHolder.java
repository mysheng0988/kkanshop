package com.mysheng.office.kkanshop.holder;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.TitleModel;
import com.mysheng.office.kkanshop.util.AppCountDownTimer;
import com.mysheng.office.kkanshop.util.DisplayUtils;
import com.mysheng.office.kkanshop.view.MyCountDownTimer;

public class TitleViewHolder extends CommonAbstractViewHolder<TitleModel> {
    public TextView leftTitle;
    public TextView centerTitle;
    public LinearLayout moreList;
    public LinearLayout layout;

    public TitleViewHolder(View itemView) {
        super(itemView);
        leftTitle=itemView.findViewById(R.id.leftTitle);
        centerTitle=itemView.findViewById(R.id.centerTitle);
        moreList=itemView.findViewById(R.id.moreList);
        layout=itemView.findViewById(R.id.layout);

    }
    public void bindHolder(TitleModel model){
        leftTitle.setText(model.getLeftTitle());
        if(model.getCenterTitle()==null){
            layout.setVisibility(View.INVISIBLE);
        }else {
           // centerTitle.setText(model.getCenterTitle());
            layout.setVisibility(View.VISIBLE);
//            new MyCountDownTimer(centerTitle.getContext(),1000*60*60,1000,"HH:mm:ss",
//                    R.drawable.bg_time_shape, centerTitle)
//                    .setTimerPadding(10,10,10,10)
//                    .setTimerTextColor(Color.RED)
//                    .setTimerTextSize(DisplayUtils.dpToPx(16))
//                    .setTimerGapColor(Color.BLACK).startTimer();
            new AppCountDownTimer(1000*60*60,100,centerTitle)
            .start();
            Log.d("mys", "bindHolder: "+centerTitle.getText().toString());
        }

    }
}
