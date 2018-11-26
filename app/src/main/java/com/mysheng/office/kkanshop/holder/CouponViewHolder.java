package com.mysheng.office.kkanshop.holder;

import android.view.View;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.VoucherModel;
import com.mysheng.office.kkanshop.voucher.SmallVoucherView;


/**
 * Created by myaheng on 2018/9/10.
 */

public class CouponViewHolder extends CommonAbstractViewHolder<VoucherModel> {
    public SmallVoucherView couponView;


    public CouponViewHolder(View itemView) {
        super(itemView);
        couponView=itemView.findViewById(R.id.couponView);
    }

    @Override
    public void bindHolder(VoucherModel model) {
       couponView.setViewDate(model);
    }
}
