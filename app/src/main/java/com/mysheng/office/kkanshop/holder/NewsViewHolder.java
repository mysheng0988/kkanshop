package com.mysheng.office.kkanshop.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.banner.Banner;
import com.mysheng.office.kkanshop.entity.NewsModel;
import com.mysheng.office.kkanshop.view.NoticeView;


/**
 * Created by myaheng on 2018/9/10.
 */

public class NewsViewHolder extends IndexAbstractViewHolder<NewsModel>{
    private NoticeView noticeView;
    private LinearLayout moreList;

    public NewsViewHolder(View itemView) {
        super(itemView);
        noticeView=itemView.findViewById(R.id.notice_view);
        moreList=itemView.findViewById(R.id.moreList);
    }

    @Override
    public void bindHolder(NewsModel newsModel) {


    }
}
