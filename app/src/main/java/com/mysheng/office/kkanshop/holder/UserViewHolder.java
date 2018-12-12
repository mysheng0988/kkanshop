package com.mysheng.office.kkanshop.holder;
import android.view.View;
import android.widget.TextView;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.User;


public class UserViewHolder extends CommonAbstractViewHolder<User> {
    public TextView userName;
    public TextView phoneNum;

    public UserViewHolder(View itemView) {
        super(itemView);
        userName=itemView.findViewById(R.id.userName);
        phoneNum=itemView.findViewById(R.id.phoneNum);

    }
    @Override
    public void bindHolder(User model){
       userName.setText(model.getUserName());
       phoneNum.setText(model.getPhone());

    }

}