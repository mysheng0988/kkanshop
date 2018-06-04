package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.DataModel;

/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeOneViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private TextView mTextView;
    public TypeOneViewHolder(View itemView) {
        super(itemView);
        mTextView=itemView.findViewById(R.id.name);
    }
    @Override
    public void bindHolder(DataModel model){
        mTextView.setText(model.name);
    }
}
