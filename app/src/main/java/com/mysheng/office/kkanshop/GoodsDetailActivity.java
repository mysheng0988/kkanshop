package com.mysheng.office.kkanshop;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mysheng.office.kkanshop.adapter.MinePagerAdapter;
import com.mysheng.office.kkanshop.listenter.ChangePage;
import com.mysheng.office.kkanshop.page.Page;
import com.mysheng.office.kkanshop.page.PageBehavior;
import com.mysheng.office.kkanshop.page.ViewPagerSlide;

/**
 * Created by myaheng on 2018/9/13.
 */

public class GoodsDetailActivity extends AppCompatActivity implements ChangePage {


    private TabLayout tabs;
    private ViewPagerSlide viewpager;
    private Toolbar toolbar;
    private MinePagerAdapter minePagerAdapter;
    private TextView detailImgText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.goods_detail_layout);
        tabs =findViewById(R.id.tabs);
        toolbar =  findViewById(R.id.toolbar);
        viewpager = findViewById(R.id.viewpager);
        detailImgText = findViewById(R.id.detailImgText);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.btn_back);


        minePagerAdapter = new MinePagerAdapter(getSupportFragmentManager());
        viewpager.setOffscreenPageLimit(4);
        viewpager.setAdapter(minePagerAdapter);
        tabs.setupWithViewPager(viewpager);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showTabPage(int page) {
        switch (page){
            case 1:
                viewpager.setScanScroll(true);
                tabs.setVisibility(View.VISIBLE);
                detailImgText.setVisibility(View.GONE);
                break;
            case 2:
                viewpager.setScanScroll(false);
                tabs.setVisibility(View.GONE);
                detailImgText.setVisibility(View.VISIBLE);
                break;
            case 3:
                tabs.getTabAt(1).select();
                break;
        }
    }
}
