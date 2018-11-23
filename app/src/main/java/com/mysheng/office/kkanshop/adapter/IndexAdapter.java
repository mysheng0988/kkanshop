package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.banner.listener.OnBannerListener;
import com.mysheng.office.kkanshop.entity.BannerModel;
import com.mysheng.office.kkanshop.entity.GoTitleModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.KillModel;
import com.mysheng.office.kkanshop.entity.LoveModel;
import com.mysheng.office.kkanshop.entity.NavModel;
import com.mysheng.office.kkanshop.entity.NoticeModel;
import com.mysheng.office.kkanshop.entity.RecommendModel;
import com.mysheng.office.kkanshop.entity.ShopModel;
import com.mysheng.office.kkanshop.entity.TitleModel;
import com.mysheng.office.kkanshop.entity.TypeModel;
import com.mysheng.office.kkanshop.holder.BannerViewHolder;
import com.mysheng.office.kkanshop.holder.GoShopTitleViewHolder;
import com.mysheng.office.kkanshop.holder.GoTitleViewHolder;
import com.mysheng.office.kkanshop.holder.GoodShopViewHolder;
import com.mysheng.office.kkanshop.holder.KillViewHolder;
import com.mysheng.office.kkanshop.holder.LoveFourViewHolder;
import com.mysheng.office.kkanshop.holder.LoveTwoViewHolder;
import com.mysheng.office.kkanshop.holder.NavViewHolder;
import com.mysheng.office.kkanshop.holder.NoticeViewHolder;
import com.mysheng.office.kkanshop.holder.RecommendViewHolder;
import com.mysheng.office.kkanshop.holder.TitleViewHolder;
import com.mysheng.office.kkanshop.listenter.OnItemClickListener;
import com.mysheng.office.kkanshop.view.NoticeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/9/10.
 */

public class IndexAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;
    private OnBannerClickListener mOnBannerClickListener;
    private OnNoticeClickListener mOnNoticeClickListener;


    private List<TypeModel> mList=new ArrayList<>();

    public IndexAdapter(Context context) {
        this.mLayoutInflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case IndexTools.BANNER:
               View bannerView=  mLayoutInflater.inflate(R.layout.item_banner_layout,parent,false);
               return new BannerViewHolder(bannerView);
            case IndexTools.NAV:
                View navView=  mLayoutInflater.inflate(R.layout.item_nav_layout,parent,false);
                return new NavViewHolder(navView);
            case IndexTools.NOTICE:
                View newsView=  mLayoutInflater.inflate(R.layout.item_news_layout,parent,false);
                return new NoticeViewHolder(newsView);
            case IndexTools.KILLTITLE:
                View titleView=  mLayoutInflater.inflate(R.layout.item_title_layout,parent,false);
                return new TitleViewHolder(titleView);
            case IndexTools.SHOPTITLE:
                View titleView2=  mLayoutInflater.inflate(R.layout.item_title_layout,parent,false);
                return new TitleViewHolder(titleView2);
            case IndexTools.GOTitle:
                View goTitle=  mLayoutInflater.inflate(R.layout.item_common_title,parent,false);
                return new GoTitleViewHolder(goTitle);
            case IndexTools.GOShopTitle:
                View goShopTitle=  mLayoutInflater.inflate(R.layout.item_common_title,parent,false);
                return new GoShopTitleViewHolder(goShopTitle);
            case IndexTools.GOReTitle:
                View GOReTitle=  mLayoutInflater.inflate(R.layout.item_common_title,parent,false);
                return new GoTitleViewHolder(GOReTitle);
            case IndexTools.KILL:
                View killView=  mLayoutInflater.inflate(R.layout.item_kill_layout,parent,false);
                return new KillViewHolder(killView);
            case IndexTools.GOODSSHOP:
                View shopView=  mLayoutInflater.inflate(R.layout.item_good_shop_layout,parent,false);
                return new GoodShopViewHolder(shopView);
            case IndexTools.LOVE_TWO:
            case IndexTools.GOSHOPTWO:
                View twoView=  mLayoutInflater.inflate(R.layout.item_live_two,parent,false);
                return new LoveTwoViewHolder(twoView);
            case IndexTools.LOVE_FOUR:
            case IndexTools.GOSHOPFOUR:
                View fourView=  mLayoutInflater.inflate(R.layout.item_live_four,parent,false);
                return new LoveFourViewHolder(fourView);
            case IndexTools.Recommend:
                View reView=  mLayoutInflater.inflate(R.layout.item_re_layout,parent,false);
                return new RecommendViewHolder(reView);

        }
        return null;
    }
    public void setModelList(List<TypeModel> lists){

        mList.addAll(lists);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final int viewType=getItemViewType(position);

        switch (viewType){
            case IndexTools.BANNER:
                final BannerModel bannerModel= (BannerModel) mList.get(position);
                ((BannerViewHolder)holder).bindHolder(bannerModel);
                ((BannerViewHolder)holder).banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        if(mOnBannerClickListener!=null){
                            mOnBannerClickListener.onBannerListener(bannerModel,position);

                        }
                    }
                });
                break;
            case IndexTools.NAV:
                final NavModel navModel= (NavModel) mList.get(position);
                ((NavViewHolder)holder).bindHolder(navModel);
                ((NavViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,navModel);
                        }
                    }
                });

                break;
            case IndexTools.NOTICE:
                final NoticeModel noticeModel=(NoticeModel) mList.get(position);
                ((NoticeViewHolder)holder).bindHolder(noticeModel);
                final NoticeView noticeView= ((NoticeViewHolder)holder).noticeView;
                final LinearLayout moreList = ((NoticeViewHolder)holder).moreList;
                noticeView.setOnNoticeClickListener(new NoticeView.OnNoticeClickListener() {
                    @Override
                    public void onNoticeClick(int index, String notice) {
                        if (mOnNoticeClickListener!=null){
                            mOnNoticeClickListener.onNoticeListener(noticeView,noticeModel,index);
                        }
                    }
                });
                moreList.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,noticeModel);
                        }
                    }
                });
                break;
            case IndexTools.KILLTITLE:
                final TitleModel model= (TitleModel) mList.get(position);
                ((TitleViewHolder)holder).bindHolder(model);
                ((TitleViewHolder)holder).moreList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,model);
                        }
                    }
                });
                break;
            case IndexTools.SHOPTITLE:
                final TitleModel model2= (TitleModel) mList.get(position);
                ((TitleViewHolder)holder).bindHolder(model2);
                ((TitleViewHolder)holder).moreList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,model2);
                        }
                    }
                });
                break;
            case IndexTools.KILL:
                final KillModel killModel= (KillModel) mList.get(position);
                ((KillViewHolder)holder).bindHolder(killModel);
                ((KillViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,killModel);
                        }
                    }
                });
                break;
            case IndexTools.GOODSSHOP:
                final ShopModel shopModel= (ShopModel) mList.get(position);
                ((GoodShopViewHolder)holder).bindHolder(shopModel);
                ((GoodShopViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,shopModel);
                        }
                    }
                });
                break;
            case IndexTools.GOTitle:
                GoTitleModel goTitleModel= (GoTitleModel) mList.get(position);
                ((GoTitleViewHolder)holder).bindHolder(goTitleModel);
                break;
            case IndexTools.GOShopTitle:
                GoTitleModel goTitleModel2= (GoTitleModel) mList.get(position);
                ((GoShopTitleViewHolder)holder).bindHolder(goTitleModel2);
                break;
            case IndexTools.GOReTitle:
                GoTitleModel goTitleModel3= (GoTitleModel) mList.get(position);
                ((GoTitleViewHolder)holder).bindHolder(goTitleModel3);
                break;
            case IndexTools.LOVE_TWO:
                final LoveModel loveModel= (LoveModel) mList.get(position);
                ((LoveTwoViewHolder)holder).bindHolder(loveModel);
                ((LoveTwoViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,loveModel);
                        }
                    }
                });
                break;
            case IndexTools.LOVE_FOUR:
                final LoveModel loveModel2= (LoveModel) mList.get(position);
                ((LoveFourViewHolder)holder).bindHolder(loveModel2);
                ((LoveFourViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,loveModel2);
                        }
                    }
                });
                break;
            case IndexTools.GOSHOPTWO:
                final LoveModel loveModel3= (LoveModel) mList.get(position);
                ((LoveTwoViewHolder)holder).bindHolder(loveModel3);
                ((LoveTwoViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,loveModel3);
                        }
                    }
                });
                break;
            case IndexTools.GOSHOPFOUR:
                final LoveModel loveModel4= (LoveModel) mList.get(position);
                ((LoveFourViewHolder)holder).bindHolder(loveModel4);
                ((LoveFourViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,loveModel4);
                        }
                    }
                });
                break;
            case IndexTools.Recommend:
                final RecommendModel recommendModel= (RecommendModel) mList.get(position);
                ((RecommendViewHolder)holder).bindHolder(recommendModel);
                ((RecommendViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,recommendModel);
                        }
                    }
                });
                ((RecommendViewHolder)holder).resemble.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,recommendModel);
                        }
                    }
                });
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getTypeParam();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if(holder instanceof TitleViewHolder){
            TitleViewHolder titleViewHolder= (TitleViewHolder) holder;
            titleViewHolder.centerTitle.cancelTimer();
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }
    public void setOnBannerClickListener(OnBannerClickListener clickListener) {
        this.mOnBannerClickListener = clickListener;
    }
    public void setNoticeClickListener(OnNoticeClickListener listener) {
        this.mOnNoticeClickListener = listener;
    }
    public interface OnBannerClickListener{
        void onBannerListener(BannerModel model,int index);
    }
    public interface OnNoticeClickListener{
        void onNoticeListener(View v,NoticeModel noticeModel,int index);
    }
}
