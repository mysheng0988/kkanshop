package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.SelectModel;

public class SelectViewHolder extends IndexAbstractViewHolder<SelectModel> {
    public LinearLayout select;
    public TextView selectText;
    public ImageView selectImg;

    public SelectViewHolder(View itemView) {
        super(itemView);
        select=itemView.findViewById(R.id.select);
        selectText=itemView.findViewById(R.id.selectText);
        selectImg=itemView.findViewById(R.id.selectImg);
    }
    public void bindHolder(SelectModel model){
        if (model.isSelect()){
            selectImg.setVisibility(View.VISIBLE);
            select.setBackground(select.getContext().getResources().getDrawable(R.drawable.bg_red_radius));
        }else {
            selectImg.setVisibility(View.GONE);
            select.setBackground(select.getContext().getResources().getDrawable(R.drawable.bg_layout_shape));
        }
        selectText.setText(model.getSelectName());

    }
}
