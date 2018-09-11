package com.mysheng.office.kkanshop.holder;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.KillModel;

public class KillViewHolder extends IndexAbstractViewHolder<KillModel> {
    private ImageView killImage;
    private TextView oldPrice;
    private  TextView price;
    public KillViewHolder(View itemView) {
        super(itemView);
        killImage=itemView.findViewById(R.id.kill_image);
        oldPrice=itemView.findViewById(R.id.old_price);
        oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        price=itemView.findViewById(R.id.kill_price);
    }
    public void bindHolder(KillModel model){
        Glide.with(killImage.getContext()).load(model.getImagePath()).into(killImage);
        oldPrice.setText(model.getOldPrice());
        price.setText(model.getPrice());
    }
}
