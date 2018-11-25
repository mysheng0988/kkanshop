package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.NoticeModel;
import com.mysheng.office.kkanshop.entity.VoucherModel;
import com.mysheng.office.kkanshop.view.CouponView;
import com.mysheng.office.kkanshop.view.NoticeView;


/**
 * Created by myaheng on 2018/9/10.
 */

public class CouponViewHolder extends CommonAbstractViewHolder<VoucherModel> {
    public CouponView couponView;


    public CouponViewHolder(View itemView) {
        super(itemView);
        couponView=itemView.findViewById(R.id.couponView);
    }

    @Override
    public void bindHolder(VoucherModel model) {
       couponView.setViewDate(model);
    }
}
