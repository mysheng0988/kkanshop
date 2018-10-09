package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.OrderModel;

public class OrderFooterViewHolder extends IndexAbstractViewHolder<OrderModel> {
    private TextView payType;
    private ImageView payMore;
    private TextView invoiceType;
    private ImageView invoiceMore;
    private TextView orderDiscount;
    private ImageView discountMore;
    private TextView totalAmount;
    private TextView orderFare;
    private ImageView explain;
    public OrderFooterViewHolder(View itemView) {
        super(itemView);
        payType=itemView.findViewById(R.id.payType);
        payMore=itemView.findViewById(R.id.payMore);
        invoiceType=itemView.findViewById(R.id.invoiceType);
        invoiceMore=itemView.findViewById(R.id.invoiceMore);
        orderDiscount=itemView.findViewById(R.id.orderDiscount);
        discountMore=itemView.findViewById(R.id.discountMore);
        totalAmount=itemView.findViewById(R.id.totalAmount);
        orderFare=itemView.findViewById(R.id.orderFare);
        explain=itemView.findViewById(R.id.explain);

    }
    public void bindHolder(OrderModel model){

    }
}
