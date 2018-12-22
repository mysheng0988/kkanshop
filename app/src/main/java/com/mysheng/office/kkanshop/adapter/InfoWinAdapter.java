package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.mysheng.office.kkanshop.KkanApplication;
import com.mysheng.office.kkanshop.R;

public class InfoWinAdapter implements AMap.InfoWindowAdapter {
        private Context mContext = KkanApplication.mContext;
        private LatLng latLng;
        private TextView strTitle;
        private TextView content;
        private String title;
        private String snippet;

        @Override
        public View getInfoWindow(Marker marker) {
            initData(marker);
            View view = initView();
            return view;
        }
        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

        private void initData(Marker marker) {
            latLng = marker.getPosition();
            snippet = marker.getSnippet();
            title = marker.getTitle();
        }

        @NonNull
        private View initView() {
            View view = LayoutInflater.from(mContext).inflate(R.layout.info_win_layout, null);
            strTitle = (TextView) view.findViewById(R.id.strTitle);
            content = (TextView) view.findViewById(R.id.content);

            strTitle.setText(title);
            content.setText(snippet);

            return view;
        }


}
