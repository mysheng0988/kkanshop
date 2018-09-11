package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.banner.listener.OnBannerListener;
import com.mysheng.office.kkanshop.entity.BannerModel;
import com.mysheng.office.kkanshop.entity.IndexToos;
import com.mysheng.office.kkanshop.entity.KillModel;
import com.mysheng.office.kkanshop.entity.NavModel;
import com.mysheng.office.kkanshop.entity.NoticeModel;
import com.mysheng.office.kkanshop.entity.ShopModel;
import com.mysheng.office.kkanshop.entity.TitleModel;
import com.mysheng.office.kkanshop.entity.TitleShopModel;
import com.mysheng.office.kkanshop.holder.BannerViewHolder;
import com.mysheng.office.kkanshop.holder.GoodShopViewHolder;
import com.mysheng.office.kkanshop.holder.KillViewHolder;
import com.mysheng.office.kkanshop.holder.NavViewHolder;
import com.mysheng.office.kkanshop.holder.NoticeViewHolder;
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
    private List<NoticeModel> newsModels=new ArrayList<>();
    private List<NavModel> navModels=new ArrayList<>();
    private List<TitleModel> titleModels=new ArrayList<>();
    private List<TitleShopModel> titleShopModels=new ArrayList<>();
    private List<KillModel> killModels=new ArrayList<>();
    private List<ShopModel> shopModels=new ArrayList<>();
    public IndexAdapter(Context context) {
        bannerModels.clear();
        navModels.clear();
        newsModels.clear();
        this.mLayoutInflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case IndexToos.BANNER:
               View bannerView=  mLayoutInflater.inflate(R.layout.item_banner_layout,parent,false);
               return new BannerViewHolder(bannerView);
            case IndexToos.NAV:
                View navView=  mLayoutInflater.inflate(R.layout.item_nav_layout,parent,false);
                return new NavViewHolder(navView);
            case IndexToos.NOTICE:
                View newsView=  mLayoutInflater.inflate(R.layout.item_news_layout,parent,false);
                return new NoticeViewHolder(newsView);
            case IndexToos.KILLTITLE:
                View titleView=  mLayoutInflater.inflate(R.layout.item_title_layout,parent,false);
                return new TitleViewHolder(titleView);
            case IndexToos.SHOPTITLE:
                View titleView2=  mLayoutInflater.inflate(R.layout.item_title_layout,parent,false);
                return new TitleShopViewHolder(titleView2);
            case IndexToos.KILL:
                View killView=  mLayoutInflater.inflate(R.layout.item_kill_layout,parent,false);
                return new KillViewHolder(killView);
            case IndexToos.GOODSSHOP:
                View shopView=  mLayoutInflater.inflate(R.layout.item_good_shop_layout,parent,false);
                return new GoodShopViewHolder(shopView);

        }
        return null;
    }
    public void addBanner(List<BannerModel> bannerModels){
        addListByType(IndexToos.BANNER,bannerModels);
        this.bannerModels.addAll(bannerModels);

    }
    public void addNav(List<NavModel> navModels){
        addListByType(IndexToos.NAV,navModels);
        this.navModels.addAll(navModels);

    }
    public void addNotice(List<NoticeModel> newsModels){
        addListByType(IndexToos.NOTICE,newsModels);
        this.newsModels.addAll(newsModels);

    }
    public void addTitleModels(List<TitleModel> titleModels){
        addListByType(IndexToos.KILLTITLE,titleModels);
        this.titleModels.addAll(titleModels);

    }
    public void addTitleShopModels(List<TitleShopModel> titleShopModels){
        addListByType(IndexToos.SHOPTITLE,titleShopModels);
        this.titleShopModels.addAll(titleShopModels);

    }
    public void addKillModels(List<KillModel> killModels){
        addListByType(IndexToos.KILL,killModels);
        this.killModels.addAll(killModels);

    }
    public void addShopModels(List<ShopModel> shopModels){
        addListByType(IndexToos.GOODSSHOP,shopModels);
        this.shopModels.addAll(shopModels);

    }
    private void addListByType(int type,List list){
        mPosition.put(type,types.size());
        for(int i=0;i<list.size();i++){
            types.add(type);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int viewType=getItemViewType(position);
        final int realPosition=position-mPosition.get(viewType);
        switch (viewType){
            case IndexToos.BANNER:
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
            case IndexToos.NAV:
                ((NavViewHolder)holder).bindHolder(navModels.get(realPosition));
                ((NavViewHolder)holder).index1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            NavModel navModel=navModels.get(realPosition);
                            navModel.setIndex(1);
                            mOnItemClickListener.onItemClick(v,navModel,realPosition);
                        }
                    }
                });
                ((NavViewHolder)holder).index2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            NavModel navModel=navModels.get(realPosition);
                            navModel.setIndex(2);
                            mOnItemClickListener.onItemClick(v,navModel,realPosition);
                        }
                    }
                });
                ((NavViewHolder)holder).index3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            NavModel navModel=navModels.get(realPosition);
                            navModel.setIndex(3);
                            mOnItemClickListener.onItemClick(v,navModel,realPosition);
                        }
                    }
                });
                ((NavViewHolder)holder).index4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            NavModel navModel=navModels.get(realPosition);
                            navModel.setIndex(4);
                            mOnItemClickListener.onItemClick(v,navModel,realPosition);
                        }
                    }
                });
                ((NavViewHolder)holder).index5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            NavModel navModel=navModels.get(realPosition);
                            navModel.setIndex(5);
                            mOnItemClickListener.onItemClick(v,navModel,realPosition);
                        }
                    }
                });
                ((NavViewHolder)holder).index6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            NavModel navModel=navModels.get(realPosition);
                            navModel.setIndex(6);
                            mOnItemClickListener.onItemClick(v,navModel,realPosition);
                        }
                    }
                });
                ((NavViewHolder)holder).index7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            NavModel navModel=navModels.get(realPosition);
                            navModel.setIndex(7);
                            mOnItemClickListener.onItemClick(v,navModel,realPosition);
                        }
                    }
                });
                ((NavViewHolder)holder).index8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClickListener!=null){
                            NavModel navModel=navModels.get(realPosition);
                            navModel.setIndex(8);
                            mOnItemClickListener.onItemClick(v,navModel,realPosition);
                        }
                    }
                });
             break;
            case IndexToos.NOTICE:
                ((NoticeViewHolder)holder).bindHolder(newsModels.get(realPosition));
                ((NoticeViewHolder)holder).noticeView.setOnNoticeClickListener(new NoticeView.OnNoticeClickListener() {
                    @Override
                    public void onNoticeClick(int position, String notice) {

                    }
                });
                ((NoticeViewHolder)holder).moreList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mOnItemClickListener!=null){
                            mOnItemClickListener.onItemClick(v,newsModels.get(realPosition),position);
                        }
                    }
                });
                break;
            case IndexToos.KILLTITLE:
                ((TitleViewHolder)holder).bindHolder(titleModels.get(realPosition));

                break;
            case IndexToos.SHOPTITLE:
                ((TitleShopViewHolder)holder).bindHolder(titleShopModels.get(realPosition));

                break;
            case IndexToos.KILL:
                ((KillViewHolder)holder).bindHolder(killModels.get(realPosition));

                break;
            case IndexToos.GOODSSHOP:
                ((GoodShopViewHolder)holder).bindHolder(shopModels.get(realPosition));
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
