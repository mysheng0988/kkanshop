package com.mysheng.office.kkanshop.holder;

import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.KillModel;

public class SecKillViewHolder extends CommonAbstractViewHolder<KillModel> {
    private ImageView killImage;
    private ImageView statusImage;
    private ImageView finished;
    private TextView goodsTitle;
    private TextView oldPrice;
    private  TextView price;
    private  TextView buyBtn;
    private  TextView percentage;
    private ProgressBar progressBar;
    public SecKillViewHolder(View itemView) {
        super(itemView);
        killImage=itemView.findViewById(R.id.kill_image);
        statusImage=itemView.findViewById(R.id.status_image);
        goodsTitle=itemView.findViewById(R.id.goodsTitle);
        oldPrice=itemView.findViewById(R.id.oldPrice);
        oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        price=itemView.findViewById(R.id.killPrice);
        buyBtn=itemView.findViewById(R.id.buyBtn);
        finished=itemView.findViewById(R.id.finished);
        percentage=itemView.findViewById(R.id.percentage);
        progressBar=itemView.findViewById(R.id.progressBar);
        progressBar.setMax(100);

    }
    public void bindHolder(KillModel model){
        Glide.with(killImage.getContext()).load(model.getImagePath()).into(killImage);
        goodsTitle.setText(model.getGoodsTitle());
        oldPrice.setText(model.getOldPrice()+"");
        price.setText(model.getPrice()+"");
        switch (model.getStatus()){
            case 0:
                statusImage.setImageResource(R.drawable.icon_finished);
                buyBtn.setVisibility(View.GONE);
                finished.setVisibility(View.VISIBLE);
                progressBar.setProgress(100);
                percentage.setText("已出售:100%");
                break;
            case 1:
                statusImage.setImageResource(R.drawable.icon_ongoing);
                buyBtn.setVisibility(View.VISIBLE);
                finished.setVisibility(View.GONE);
                int percentageNum= (int) ((model.getConfirmNum()*1.0f)/model.getAllNum()*100);
                percentage.setText("已出售:"+percentageNum+"%");
                progressBar.setProgress(percentageNum);
                break;
            case -1:
                statusImage.setImageResource(R.drawable.icon_not_begining);
                buyBtn.setVisibility(View.VISIBLE);
                buyBtn.setText("即将开始");
                buyBtn.setBackgroundResource(R.color.green);
                progressBar.setProgress(0);
                finished.setVisibility(View.GONE);
                percentage.setText("已出售:0%");
                break;
        }

    }
}
