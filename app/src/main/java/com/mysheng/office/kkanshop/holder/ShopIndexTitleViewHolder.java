package com.mysheng.office.kkanshop.holder;

import android.graphics.Typeface;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.ShopIndexTitleModel;
import com.mysheng.office.kkanshop.entity.TitleModel;

import java.util.Random;

public class ShopIndexTitleViewHolder extends CommonAbstractViewHolder<ShopIndexTitleModel> {
    private int color[]={R.color.crimson,R.color.chocolate,R.color.medium_violet_red,R.color.forest_green,R.color.deep_sky_blue,R.color.sandy_brown,R.color.light_coral,R.color.goldenrod,R.color.aquamarine,R.color.turquoise};
    private String fontPath[]={"font/normal.ttf","font/1001.ttf","font/1025.ttf","font/1026.ttf","font/1027.ttf",
           "font/1028.ttf","font/1029.ttf","font/1030.ttf","font/1031.ttf","font/1032.ttf","font/1033.ttf",
            "font/cartoon.ttf","font/Iron_blue.ttf","font/ldy.ttf","font/hollow.ttf"};
    public TextView mTitle;
    public View titleBorder;
    public ShopIndexTitleViewHolder(View itemView) {
        super(itemView);
        titleBorder=itemView.findViewById(R.id.titleBorder);
        mTitle=itemView.findViewById(R.id.title);

    }
    public void bindHolder(ShopIndexTitleModel model){

        Random random=new Random();
        Typeface typeface=Typeface.createFromAsset(mTitle.getContext().getAssets(),fontPath[random.nextInt(fontPath.length)]);
        mTitle.setTypeface(typeface);
        mTitle.setText(model.getTitle());
        mTitle.setTextColor(color[random.nextInt(10)]);

        titleBorder.setBackgroundResource(color[random.nextInt(10)]);

    }
}
