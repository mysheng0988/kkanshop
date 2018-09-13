package com.mysheng.office.kkanshop.holder;

import android.view.View;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.GoTitleModel;
import com.mysheng.office.kkanshop.view.ChangeTextView;

public class GoTitleViewHolder extends IndexAbstractViewHolder<GoTitleModel> {
    public ChangeTextView goTitle;
    public GoTitleViewHolder(View itemView) {
        super(itemView);
        goTitle=itemView.findViewById(R.id.goTitle);


    }
    public void bindHolder(GoTitleModel model){

        goTitle.setText(model.getGoTitle());
    }
}
