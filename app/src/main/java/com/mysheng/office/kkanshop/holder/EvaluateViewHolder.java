package com.mysheng.office.kkanshop.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.adapter.GridImageViewAdapter;
import com.mysheng.office.kkanshop.entity.EvaluateModel;
import com.mysheng.office.kkanshop.util.Tools;
import com.mysheng.office.kkanshop.view.GridImageView;
import com.mysheng.office.kkanshop.view.RoundImageView;
import com.mysheng.office.kkanshop.view.Star;

import java.util.List;

public class EvaluateViewHolder extends IndexAbstractViewHolder<EvaluateModel> {

    public RoundImageView userIcon;
    public TextView userName;
    public TextView evaluate;
    public TextView goodsType;
    public TextView evaluateDate;
    public Star starView;
    public GridImageView gridImageView;

    public EvaluateViewHolder(View itemView) {
        super(itemView);
        userIcon=itemView.findViewById(R.id.userIcon);
        userName=itemView.findViewById(R.id.userName);
        evaluate=itemView.findViewById(R.id.evaluate);
        goodsType=itemView.findViewById(R.id.goodsType);
        evaluateDate=itemView.findViewById(R.id.evaluateDate);
        starView=itemView.findViewById(R.id.star);
        gridImageView=itemView.findViewById(R.id.gridImageView);
        gridImageView.setAdapter(new GridImageViewAdapter<String>() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String path) {
                if(!Tools.isNetUri(path)){
                    path="file://"+path;
                }
                Glide.with(context).load(path).centerCrop().override(400, 400).into(imageView);
            }

            @Override
            public void onAddClick(Context context, List<String> list) {
                // show();
            }

            @Override
            public int getShowStyle() {
                return GridImageView.STYLE_GRID;
            }

            @Override
            public void onItemImageClick(ImageView imageView,List<ImageView> imageViews,int index, List<String> list) {
                super.onItemImageClick(imageView,imageViews,index, list);
//                vImageWatcher.show(imageView,imageViews,list);
//                Toast.makeText(getApplicationContext(), "--->" + index, Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void bindHolder(EvaluateModel model){
       // userIcon.setImageBitmap();
        userName.setText(model.getUserName());
        evaluate.setText(model.getComment());
        goodsType.setText(model.getGoodsType());
        evaluateDate.setText(model.getStrData());
        starView.setMark(model.getScore());
        gridImageView.setImageData(model.getGoodsImgPath(),true);
    }
}
