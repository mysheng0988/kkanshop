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
import com.mysheng.office.kkanshop.MIMC.bean.ChatMsg;
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
    public MapView mapView;


    public TypeLocationViewHolder(View itemView) {
        super(itemView);
        mTitle=itemView.findViewById(R.id.id_location_title);
        mImageView=itemView.findViewById(R.id.id_useIcon);
        mAddress=itemView.findViewById(R.id.id_location_address);
        mapView=itemView.findViewById(R.id.mapView);

    }

    @Override
    public void bindHolder(ChatMsg model){
            String content=new String(model.getMsg().getContent());
            mTitle.setText(model.getMsg().getTabTitle());
            //mImageView.setImageResource(R.drawable.ynn);
           mAddress.setText(content);
        //绘制marker
        AMap aMap = mapView.getMap();
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.showMyLocation(false);
        aMap.setMyLocationEnabled(false);
        LatLng latLng=new LatLng(model.getMsg().getLongitude(),model.getMsg().getLatitude());
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setAllGesturesEnabled(false);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        aMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(mapView.getContext().getResources(),R.drawable.position)))
                .draggable(true));
    }

}
