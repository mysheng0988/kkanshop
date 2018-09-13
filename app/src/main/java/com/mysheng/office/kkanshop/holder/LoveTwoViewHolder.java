package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.GoTitleModel;
import com.mysheng.office.kkanshop.entity.LoveModel;
import com.mysheng.office.kkanshop.view.ChangeTextView;

public class LoveTwoViewHolder extends IndexAbstractViewHolder<LoveModel> {
    public TextView loveTitle;
    public TextView discountTitle;
    public TextView labelTitle;
    public ImageView loveIcon;
    public LoveTwoViewHolder(View itemView) {
        super(itemView);
        loveTitle=itemView.findViewById(R.id.loveTitle);
        discountTitle=itemView.findViewById(R.id.discountTitle);
        labelTitle=itemView.findViewById(R.id.labelTitle);
        loveIcon=itemView.findViewById(R.id.loveIcon);
    }
    public void bindHolder(LoveModel model){
        loveTitle.setText(model.getLoveTitle());
        discountTitle.setText(model.getDiscountTitle());
        labelTitle.setText(model.getLabelTitle());
        Glide.with(loveIcon.getContext()).load(model.getLovePath()).into(loveIcon);
    }
}
