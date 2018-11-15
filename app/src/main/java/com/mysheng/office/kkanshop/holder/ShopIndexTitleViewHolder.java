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
    private String fontPath[]={"font/ana.ttf",
        "font/banners.ttf", "font/bubbling.ttf",
            "font/cartoon.ttf","font/chinese.ttf",
            "font/formal.ttf","font/fz_cartoon.ttf",
            "font/fzlxt.ttf","font/fzpyt.ttf",
            "font/hk_snt.ttf","font/hollow.ttf",
            "font/Iron_blue.ttf","font/ldy.ttf",
            "font/normal.ttf","font/qlin.ttf",
            "font/run.ttf","font/running.ttf",
            "font/running_entry.ttf","font/Spray.ttf",
            "font/wzybxs.ttf"
    };

    public TextView mTitle;
    public View titleBorder;
    public ShopIndexTitleViewHolder(View itemView) {
        super(itemView);
        titleBorder=itemView.findViewById(R.id.titleBorder);
        mTitle=itemView.findViewById(R.id.title);

    }
    public void bindHolder(ShopIndexTitleModel model){

        Random random=new Random();
        String str=fontPath[random.nextInt(20)];
        Typeface typeface=Typeface.createFromAsset(mTitle.getContext().getAssets(),str);
        mTitle.setTypeface(typeface);
        mTitle.setText(model.getTitle()+str);
        int nun=random.nextInt(10);
        mTitle.setTextColor(color[nun]);

        titleBorder.setBackgroundResource(color[nun]);

    }
}
