package com.mysheng.office.kkanshop.holder;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.TitleModel;
import com.mysheng.office.kkanshop.util.UtilsDate;
import com.mysheng.office.kkanshop.view.CountDownTextView;

public class TitleViewHolder extends CommonAbstractViewHolder<TitleModel> {
    public TextView leftTitle;
    public TextView field;
    public CountDownTextView centerTitle;
    public LinearLayout moreList;
    public LinearLayout layout;
    public TitleViewHolder(View itemView) {
        super(itemView);
        leftTitle=itemView.findViewById(R.id.leftTitle);
        field=itemView.findViewById(R.id.field);
        centerTitle=itemView.findViewById(R.id.centerTitle);
        moreList=itemView.findViewById(R.id.moreList);
        layout=itemView.findViewById(R.id.layout);

    }
    @Override
    public void bindHolder(TitleModel model){
        leftTitle.setText(model.getLeftTitle());
        if(model.getCenterTitle()==null){
            layout.setVisibility(View.INVISIBLE);
        }else {
            String hour=model.getCenterTitle();
            int hourNum=Integer.parseInt(hour);
           if(hourNum%2!=0){
               hourNum=hourNum-1;

               hour= hourNum>9?hourNum+"":"0"+hourNum;
           }
            int nextHour=hourNum>22?0:hourNum+2;
            field.setText(hour+":00场");
            String nowDate= UtilsDate.getTodayDate("yyyy-MM-dd HH:mm:ss");
            String endDate=UtilsDate.getTodayDate()+" "+nextHour+":00:00";
            long date=UtilsDate.getDifferDate(endDate,nowDate);
            centerTitle.initData(date);

            layout.setVisibility(View.VISIBLE);
        }

    }

}
