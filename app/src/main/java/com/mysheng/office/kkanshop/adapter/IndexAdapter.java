package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.mysheng.office.kkanshop.entity.TitleShopModel;
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
import com.mysheng.office.kkanshop.holder.TitleShopViewHolder;
import com.mysheng.office.kkanshop.holder.TitleViewHolder;
import com.mysheng.office.kkanshop.listenter.OnItemClickListener;
import com.mysheng.office.kkanshop.view.NoticeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by myaheng on 2018/9/10.
 */

public class IndexAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;
    private OnBannerClickListener mOnBannerClickListener;
    private List<Integer> types=new ArrayList<>();
    private Map<Integer,Integer> mPosition=new HashMap<>();//存储list的初始位置

    private List<BannerModel> bannerModels=new ArrayList<>();
    private List<NoticeModel> noticeModels=new ArrayList<>();
    private List<NavModel> navModels=new ArrayList<>();
    private List<TitleModel> titleModels=new ArrayList<>();
    private List<TitleShopModel> titleShopModels=new ArrayList<>();
    private List<GoTitleModel> goTitleModels=new ArrayList<>();
    private List<GoTitleModel> goShopTitleModels=new ArrayList<>();
    private List<GoTitleModel> goReTitleModels=new ArrayList<>();
    private List<KillModel> killModels=new ArrayList<>();
    private List<ShopModel> shopModels=new ArrayList<>();
    private List<LoveModel> loveTwoModels=new ArrayList<>();
    private List<LoveModel> loveFourModels=new ArrayList<>();
    private List<LoveModel> shopTwoModels=new ArrayList<>();
    private List<LoveModel> shopFourModels=new ArrayList<>();
    private List<RecommendModel> recommendModels=new ArrayList<>();
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
                return new TitleShopViewHolder(titleView2);
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
    public void setBanner(List<BannerModel> bannerModels){
        addListByType(IndexTools.BANNER,bannerModels);
        this.bannerModels.addAll(bannerModels);

    }
    public void setNav(List<NavModel> navModels){
        addListByType(IndexTools.NAV,navModels);
        this.navModels.addAll(navModels);

    }
    public void setNotice(List<NoticeModel> noticeModels){
        addListByType(IndexTools.NOTICE,noticeModels);
        this.noticeModels.addAll(noticeModels);

    }
    public void setTitleModels(List<TitleModel> titleModels){
        addListByType(IndexTools.KILLTITLE,titleModels);
        this.titleModels.addAll(titleModels);

    }
    public void setTitleShopModels(List<TitleShopModel> titleShopModels){
        addListByType(IndexTools.SHOPTITLE,titleShopModels);
        this.titleShopModels.addAll(titleShopModels);

    }
    public void setKillModels(List<KillModel> killModels){
        addListByType(IndexTools.KILL,killModels);
        this.killModels.addAll(killModels);

    }
    public void setShopModels(List<ShopModel> shopModels){
        addListByType(IndexTools.GOODSSHOP,shopModels);
        this.shopModels.addAll(shopModels);

    }
    public void setGoTitleModels(List<GoTitleModel> goTitleModels){
        addListByType(IndexTools.GOTitle,goTitleModels);
        this.goTitleModels.addAll(goTitleModels);

    }
    public void setGoShopTitleModels(List<GoTitleModel> goTitleModels){
        addListByType(IndexTools.GOShopTitle,goTitleModels);
        this.goShopTitleModels.addAll(goTitleModels);

    }
    public void setGoReTitleModels(List<GoTitleModel> goTitleModels){
        addListByType(IndexTools.GOReTitle,goTitleModels);
        this.goReTitleModels.addAll(goTitleModels);

    }
    public void setLoveTwoModels(List<LoveModel> loveModels){
        addListByType(IndexTools.LOVE_TWO,loveModels);
        this.loveTwoModels.addAll(loveModels);

    }
    public void setLoveFourModels(List<LoveModel> loveModels){
        addListByType(IndexTools.LOVE_FOUR,loveModels);
        this.loveFourModels.addAll(loveModels);

    }
    public void setShopTwoModels(List<LoveModel> loveModels){
        addListByType(IndexTools.GOSHOPTWO,loveModels);
        this.shopTwoModels.addAll(loveModels);

    }
    public void setShopFourModels(List<LoveModel> loveModels){
        addListByType(IndexTools.GOSHOPFOUR,loveModels);
        this.shopFourModels.addAll(loveModels);

    }
    public void setRecommendModels(List<RecommendModel> recommendModels){
        addListByType(IndexTools.Recommend,recommendModels);
        this.recommendModels.addAll(recommendModels);

    }
    public void addRecommendModels(List<RecommendModel> recommendModels){
        reListByType(IndexTools.Recommend,recommendModels);
        this.recommendModels.addAll(recommendModels);

    }

    private void addListByType(int type,List list){
        mPosition.put(type,types.size());
        for(int i=0;i<list.size();i++){
            types.add(type);
        }
    }
    private void reListByType(int type,List list){
        for(int i=0;i<list.size();i++){
            types.add(type);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final int viewType=getItemViewType(position);

        final int realPosition=position-mPosition.get(viewType);
        switch (viewType){
            case IndexTools.BANNER:
                ((BannerViewHolder)holder).bindHolder(bannerModels.get(realPosition));
                ((BannerViewHolder)holder).banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        if(mOnBannerClickListener!=null){
                            mOnBannerClickListener.onBannerListener(position);

                        }
                    }
                });
                break;
            case IndexTools.NAV:
                ((NavViewHolder)holder).bindHolder(navModels.get(realPosition));
                ((NavViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,navModels,realPosition);
                        }
                    }
                });

                break;
            case IndexTools.NOTICE:
                ((NoticeViewHolder)holder).bindHolder(noticeModels.get(realPosition));
                final NoticeView noticeView= ((NoticeViewHolder)holder).noticeView;
                final LinearLayout moreList = ((NoticeViewHolder)holder).moreList;
                noticeView.setOnNoticeClickListener(new NoticeView.OnNoticeClickListener() {
                    @Override
                    public void onNoticeClick(int index, String notice) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(noticeView,viewType,noticeModels,index);
                        }
                    }
                });
                moreList.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,noticeModels,realPosition);
                        }
                    }
                });
                break;
            case IndexTools.KILLTITLE:
                ((TitleViewHolder)holder).bindHolder(titleModels.get(realPosition));

                ((TitleViewHolder)holder).moreList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,killModels,realPosition);
                        }
                    }
                });
                break;
            case IndexTools.SHOPTITLE:
                ((TitleShopViewHolder)holder).bindHolder(titleShopModels.get(realPosition));
                ((TitleShopViewHolder)holder).moreList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,titleShopModels,realPosition);
                        }
                    }
                });
                break;
            case IndexTools.KILL:
                ((KillViewHolder)holder).bindHolder(killModels.get(realPosition));
                ((KillViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,killModels,realPosition);
                        }
                    }
                });
                break;
            case IndexTools.GOODSSHOP:
                ((GoodShopViewHolder)holder).bindHolder(shopModels.get(realPosition));
                ((GoodShopViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,shopModels,realPosition);
                        }
                    }
                });
                break;
            case IndexTools.GOTitle:
                ((GoTitleViewHolder)holder).bindHolder(goTitleModels.get(realPosition));
                break;
            case IndexTools.GOShopTitle:
                ((GoShopTitleViewHolder)holder).bindHolder(goShopTitleModels.get(realPosition));
                break;
            case IndexTools.GOReTitle:
                ((GoTitleViewHolder)holder).bindHolder(goReTitleModels.get(realPosition));
                break;
            case IndexTools.LOVE_TWO:
                ((LoveTwoViewHolder)holder).bindHolder(loveTwoModels.get(realPosition));
                ((LoveTwoViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,loveTwoModels,realPosition);
                        }
                    }
                });
                break;
            case IndexTools.LOVE_FOUR:
                ((LoveFourViewHolder)holder).bindHolder(loveFourModels.get(realPosition));
                ((LoveFourViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,loveFourModels,realPosition);
                        }
                    }
                });
                break;
            case IndexTools.GOSHOPTWO:
                ((LoveTwoViewHolder)holder).bindHolder(shopTwoModels.get(realPosition));
                ((LoveTwoViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,shopTwoModels,realPosition);
                        }
                    }
                });
                break;
            case IndexTools.GOSHOPFOUR:
                ((LoveFourViewHolder)holder).bindHolder(shopFourModels.get(realPosition));
                ((LoveFourViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,shopFourModels,realPosition);
                        }
                    }
                });
                break;
            case IndexTools.Recommend:
                ((RecommendViewHolder)holder).bindHolder(recommendModels.get(realPosition));
                ((RecommendViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,recommendModels,realPosition);
                        }
                    }
                });
                ((RecommendViewHolder)holder).resemble.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,viewType,recommendModels,realPosition);
                        }
                    }
                });
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        return types.get(position);
    }

    @Override
    public int getItemCount() {
        return types.size();
    }
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }
    public void setOnBannerClickListener(OnBannerClickListener clickListener) {
        this.mOnBannerClickListener = clickListener;
    }
    public interface OnBannerClickListener{
        void onBannerListener(int index);
    }
}
