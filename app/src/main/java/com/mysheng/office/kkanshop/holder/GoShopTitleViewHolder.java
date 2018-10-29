package com.mysheng.office.kkanshop.holder;

import android.view.View;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.GoTitleModel;
import com.mysheng.office.kkanshop.view.ChangeTextView;

public class GoShopTitleViewHolder extends CommonAbstractViewHolder<GoTitleModel> {
    public ChangeTextView goTitle;
    private int[] color=  new int[] {  0xFF339999, 0xFF339966,0xFF339933 ,0xFF339900,0xFF339933 , 0xFF339966,0xFF339999};
    public GoShopTitleViewHolder(View itemView) {
        super(itemView);
        goTitle=itemView.findViewById(R.id.goTitle);
        goTitle.setColor(color);


    }
    public void bindHolder(GoTitleModel model){

       goTitle.setText(model.getGoTitle());
    }
}
