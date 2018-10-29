package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.NoticeModel;
import com.mysheng.office.kkanshop.view.NoticeView;


/**
 * Created by myaheng on 2018/9/10.
 */

public class NoticeViewHolder extends CommonAbstractViewHolder<NoticeModel> {
    public NoticeView noticeView;
    public LinearLayout moreList;

    public NoticeViewHolder(View itemView) {
        super(itemView);
        noticeView=itemView.findViewById(R.id.notice_view);
        moreList=itemView.findViewById(R.id.moreList);
    }

    @Override
    public void bindHolder(NoticeModel newsModel) {
        noticeView.addNotice(newsModel.getTextList());
        noticeView.startFlipping();
    }
}
