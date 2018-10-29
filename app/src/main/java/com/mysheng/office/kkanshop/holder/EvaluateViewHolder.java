package com.mysheng.office.kkanshop.holder;
import android.view.View;
import android.widget.TextView;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.EvaluateModel;
import com.mysheng.office.kkanshop.view.MessagePicturesLayout;
import com.mysheng.office.kkanshop.view.RoundImageView;
import com.mysheng.office.kkanshop.view.Star;
public class EvaluateViewHolder extends CommonAbstractViewHolder<EvaluateModel> {

    public RoundImageView userIcon;
    public TextView userName;
    public TextView evaluate;
    public TextView goodsType;
    public TextView evaluateDate;
    public Star starView;
    public MessagePicturesLayout msgImg;

    public EvaluateViewHolder(View itemView) {
        super(itemView);
        userIcon=itemView.findViewById(R.id.userIcon);
        userName=itemView.findViewById(R.id.userName);
        evaluate=itemView.findViewById(R.id.evaluate);
        goodsType=itemView.findViewById(R.id.goodsType);
        evaluateDate=itemView.findViewById(R.id.evaluateDate);
        msgImg=itemView.findViewById(R.id.msgImg);
        starView=itemView.findViewById(R.id.star);


    }
    public void bindHolder(EvaluateModel model){
       // userIcon.setImageBitmap();
        userName.setText(model.getUserName());
        evaluate.setText(model.getComment());
        goodsType.setText(model.getGoodsType());
        evaluateDate.setText(model.getStrData());
        starView.setMark(model.getScore());
        msgImg.set(model.getGoodsImgPath(),model.getGoodsImgPath());
    }
}
