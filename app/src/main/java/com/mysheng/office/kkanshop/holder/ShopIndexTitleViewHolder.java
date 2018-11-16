package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.TextView;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ShopIndexTitleModel;

import java.util.Random;

public class ShopIndexTitleViewHolder extends CommonAbstractViewHolder<ShopIndexTitleModel> {
    private int color[]={R.color.crimson,R.color.chocolate,R.color.medium_violet_red,R.color.forest_green,R.color.deep_sky_blue,R.color.sandy_brown,R.color.light_coral,R.color.goldenrod,R.color.aquamarine,R.color.turquoise};

    public TextView mTitle;
    public View titleBorder;
    public ShopIndexTitleViewHolder(View itemView) {
        super(itemView);
        titleBorder=itemView.findViewById(R.id.titleBorder);
        mTitle=itemView.findViewById(R.id.title);

    }
    public void bindHolder(ShopIndexTitleModel model){
        Random random=new Random();
        mTitle.setText(model.getTitle());
        int nun=random.nextInt(10);
        mTitle.setTextColor(mTitle.getContext().getResources().getColor(color[nun]));
        titleBorder.setBackgroundResource(color[nun]);

    }
}
