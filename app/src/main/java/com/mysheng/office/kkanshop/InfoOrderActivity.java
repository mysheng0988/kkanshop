package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.mysheng.office.kkanshop.adapter.NearbyViewAdapter;
import com.mysheng.office.kkanshop.adapter.OrderAdapter;
import com.mysheng.office.kkanshop.entity.ShopModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myaheng on 2018/10/9.
 */

public class InfoOrderActivity extends Activity {
    private RecyclerView orderView;
    private OrderAdapter mAdapter;
    private List<OrderAdapter> modelslist=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_order_layout);
        orderView=findViewById(R.id.orderView);
    }
}
