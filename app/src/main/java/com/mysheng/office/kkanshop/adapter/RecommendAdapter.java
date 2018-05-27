package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.entity.RecommendBean;

import java.util.List;

public class RecommendAdapter extends BaseAdapter {
    private List<RecommendBean> mList;
    private LayoutInflater layoutInflater;
    public RecommendAdapter(Context context, List<RecommendBean> data){
        mList=data;
        layoutInflater=LayoutInflater.from(context);
//        mImageLoader=new ImageLoader();
//        IMGPATH=new ReadLoadPathProperties().getPropertiesURL(context,"dataPath")+"/images/";
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.item_recommend,null);
            viewHolder.imageView=convertView.findViewById(R.id.image_logo);
            viewHolder.textView=convertView.findViewById(R.id.name);
            viewHolder.items_goneText=convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        //String url=IMGPATH+mList.get(position).IMG_ALT;
        //viewHolder.imageView.setTag(url);
        // new ImageLoader().showImageByThread(viewHolder.imageView,url);//利用线程加载
        //mImageLoader.showImageByAsyncTask(viewHolder.imageView,url);//li利用AsyncTask加载
        viewHolder.textView.setText(mList.get(position).getName());
        viewHolder.items_goneText.setText(mList.get(position).price);
        return convertView;
    }
    class ViewHolder{
        public TextView textView;
        public ImageView imageView;
        public TextView items_goneText;
    }
}
