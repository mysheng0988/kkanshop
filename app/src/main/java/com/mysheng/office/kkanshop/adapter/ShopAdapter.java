package com.mysheng.office.kkanshop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mysheng.office.kkanshop.ClassifyFragment;
import com.mysheng.office.kkanshop.R;

/**
 * Created by myaheng on 2018/5/8.
 */

public class ShopAdapter extends BaseAdapter {
    private Context context;
    private String[] strings;
    public static int mPosition;

    public ShopAdapter(Context context, String[] strings){
        this.context =context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return strings.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return strings[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
        TextView tv = convertView.findViewById(R.id.tv);
        mPosition = position;
        tv.setText(strings[position]);
        if (position == ClassifyFragment.mPosition) {
           convertView.setBackgroundResource(R.drawable.bg_textview_borders);
        } else {
            convertView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        return convertView;
    }
}
