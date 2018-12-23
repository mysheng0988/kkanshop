package com.mysheng.office.kkanshop.holder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.MIMC.bean.Msg;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.GoodsModel;


public class GoodsViewHolder extends TypeAbstractViewHolder {
    private ImageView userIcon;
    private ImageView goodsImage;
    private TextView goodsName;
    private TextView goodsPrice;

    public GoodsViewHolder(View itemView) {
        super(itemView);
        userIcon=itemView.findViewById(R.id.userIcon);
        goodsImage=itemView.findViewById(R.id.goodsImage);
        goodsName=itemView.findViewById(R.id.goodsName);
        goodsPrice=itemView.findViewById(R.id.goodsPrice);

    }
    public void bindHolder(ChatMsg model){
        Msg msg=model.getMsg();
        String imagePath=new String(msg.getContent());
        Glide.with(goodsImage.getContext()).load(imagePath).into(goodsImage);
        goodsName.setText(msg.getTabTitle());
        goodsPrice.setText(String.format("￥:%s", msg.getPrice()));
    }
}
