package com.mysheng.office.kkanshop.holder;

import android.view.View;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.VoucherModel;
import com.mysheng.office.kkanshop.voucher.SmallVoucherView;
import com.mysheng.office.kkanshop.voucher.VoucherView;


/**
 * Created by myaheng on 2018/9/10.
 */

public class VoucherViewHolder extends CommonAbstractViewHolder<VoucherModel> {
    public VoucherView voucherView;


    public VoucherViewHolder(View itemView) {
        super(itemView);
        voucherView=itemView.findViewById(R.id.voucherView);
    }

    @Override
    public void bindHolder(VoucherModel model) {
        voucherView.setViewDate(model);
    }
}
