package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.DataModel;

/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeLeftTextViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private TextView mTextView;
    public TypeLeftTextViewHolder(View itemView) {
        super(itemView);
        mTextView=itemView.findViewById(R.id.id_content_text);
        mImageView=itemView.findViewById(R.id.id_useIcon);
    }
    @Override
    public void bindHolder(DataModel model){
        mTextView.setText(model.name);
        mImageView.setImageResource(R.drawable.ynn);
    }
}
