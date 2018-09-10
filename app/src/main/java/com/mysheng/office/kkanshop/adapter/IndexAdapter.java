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
import com.mysheng.office.kkanshop.entity.NavModel;
import com.mysheng.office.kkanshop.entity.NewsModel;
import com.mysheng.office.kkanshop.holder.BannerViewHolder;
import com.mysheng.office.kkanshop.holder.NavViewHolder;
import com.mysheng.office.kkanshop.listenter.OnItemClickListener;

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
    private List<NewsModel> newsModels=new ArrayList<>();
    private List<NavModel> navModels=new ArrayList<>();
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
    public void addNews(List<NewsModel> newsModels){
        addListByType(IndexToos.NEWS,newsModels);
        this.newsModels.addAll(newsModels);

    }
    private void addListByType(int type,List list){
        mPosition.put(type,types.size());
        for(int i=0;i<list.size();i++){
            types.add(type);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType=getItemViewType(position);
        int realPosition=position-mPosition.get(viewType);
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
        void onBannerListener(int poistion);
    }
}
