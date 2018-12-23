package com.mysheng.office.kkanshop.holder;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.PolylineOptions;
import com.bumptech.glide.Glide;
import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
import com.mysheng.office.kkanshop.MIMC.bean.Msg;
import com.mysheng.office.kkanshop.R;
import com.mysheng.office.kkanshop.adapter.InfoWinAdapter;

import java.util.Map;


/**
 * Created by myaheng on 2018/5/11.
 */

public class TypeLocationViewHolder extends TypeAbstractViewHolder{
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mAddress;
    public ImageView mapView;


    public TypeLocationViewHolder(View itemView) {
        super(itemView);
        mTitle=itemView.findViewById(R.id.id_location_title);
        mImageView=itemView.findViewById(R.id.id_useIcon);
        mAddress=itemView.findViewById(R.id.id_location_address);
        mapView=itemView.findViewById(R.id.mapView);

    }

    @Override
    public void bindHolder(ChatMsg model){
           Msg msg=model.getMsg();
            String content=new String(msg.getContent());
            mTitle.setText(msg.getTabTitle());
            //mImageView.setImageResource(R.drawable.ynn);
           mAddress.setText(content);
           String location=msg.getLatitude()+","+msg.getLongitude();
           String URL="https://restapi.amap.com/v3/staticmap?location="+location+"&zoom=15&size=250*150&markers=mid,,A:"+location+"&key=65571e1055158f6795eb90a264885e4b";
        Glide.with(mapView.getContext()).load(URL).into(mapView);
    }

}
