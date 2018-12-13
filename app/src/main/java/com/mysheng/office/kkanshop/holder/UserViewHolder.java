package com.mysheng.office.kkanshop.holder;
import android.view.View;
import android.widget.TextView;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.UserModel;


public class UserViewHolder extends CommonAbstractViewHolder<UserModel> {
    public TextView userName;
    public TextView phoneNum;

    public UserViewHolder(View itemView) {
        super(itemView);
        userName=itemView.findViewById(R.id.userName);
        phoneNum=itemView.findViewById(R.id.phoneNum);

    }
    @Override
    public void bindHolder(UserModel model){
       userName.setText(model.getUserName());
       phoneNum.setText(model.getPhone());

    }

}
