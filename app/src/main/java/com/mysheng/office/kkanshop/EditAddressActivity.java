package com.mysheng.office.kkanshop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mysheng.office.kkanshop.address.bean.City;
import com.mysheng.office.kkanshop.address.bean.County;
import com.mysheng.office.kkanshop.address.bean.Province;
import com.mysheng.office.kkanshop.address.bean.Street;
import com.mysheng.office.kkanshop.address.dbManager.AddressDictManager;
import com.mysheng.office.kkanshop.address.utils.LogUtil;
import com.mysheng.office.kkanshop.address.widget.AddressSelector;
import com.mysheng.office.kkanshop.address.widget.BottomDialog;
import com.mysheng.office.kkanshop.address.widget.OnAddressSelectedListener;
import com.mysheng.office.kkanshop.dialog.DatePickDialog;
import com.mysheng.office.kkanshop.view.SlideSwitch;

/**
 * Created by myaheng on 2018/10/12.
 */

public class EditAddressActivity extends Activity implements View.OnClickListener, OnAddressSelectedListener, AddressSelector.OnDialogCloseListener, AddressSelector.onSelectorAreaPositionListener {
    private ImageView comeBack;
    private TextView commonTitle;
    private EditText userName;
    private EditText telephone;
    private TextView area;
    private ImageView areaMore;
    private EditText addressName;
    private SlideSwitch slideSwitch;
    private ImageView dateTimeMore;
    private TextView dateTime;

    private AddressDictManager addressDictManager;
    private String provinceCode;
    private String cityCode;
    private String countyCode;
    private String streetCode;
    private int provincePosition;
    private int cityPosition;
    private int countyPosition;
    private int streetPosition;
    private BottomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_address_layout);
        AddressSelector selector = new AddressSelector(this);
        //获取地址管理数据库
        addressDictManager = selector.getAddressDictManager();

        selector.setTextSize(14);//设置字体的大小
//        selector.setIndicatorBackgroundColor("#00ff00");
        selector.setIndicatorBackgroundColor(android.R.color.holo_orange_light);//设置指示器的颜色
//        selector.setBackgroundColor(android.R.color.holo_red_light);//设置字体的背景

        selector.setTextSelectedColor(android.R.color.holo_orange_light);//设置字体获得焦点的颜色

        selector.setTextUnSelectedColor(android.R.color.holo_blue_light);//设置字体没有获得焦点的颜色

        initView();
        initEvent();

    }

    private void initView() {
        comeBack=findViewById(R.id.comeBack);
        commonTitle=findViewById(R.id.commonTitle);
        userName=findViewById(R.id.userName);
        telephone=findViewById(R.id.telephone);
        area=findViewById(R.id.area);
        areaMore=findViewById(R.id.areaMore);
        dateTimeMore=findViewById(R.id.dateTimeMore);
        dateTime=findViewById(R.id.dateTime);
        addressName=findViewById(R.id.addressName);
        slideSwitch=findViewById(R.id.slideSwitch);
    }
    private void initEvent() {
        comeBack.setOnClickListener(this);
        area.setOnClickListener(this);
        areaMore.setOnClickListener(this);
        dateTime.setOnClickListener(this);
        dateTimeMore.setOnClickListener(this);
        slideSwitch.setOnMbClickListener(new SlideSwitch.OnMClickListener() {
            @Override
            public void onClick(boolean isRight) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.comeBack:
                finish();
              break;
            case R.id.area:
            case R.id.areaMore:
                if (dialog != null) {
                    dialog.show();
                } else {
                    dialog = new BottomDialog(this);
                    dialog.setOnAddressSelectedListener(this);
                    dialog.setDialogDismisListener(this);
                    dialog.setTextSize(14);//设置字体的大小
                    dialog.setIndicatorBackgroundColor(android.R.color.holo_orange_light);//设置指示器的颜色
                    dialog.setTextSelectedColor(android.R.color.holo_orange_light);//设置字体获得焦点的颜色
                    dialog.setTextUnSelectedColor(android.R.color.holo_blue_light);//设置字体没有获得焦点的颜色
//            dialog.setDisplaySelectorArea("31",1,"2704",1,"2711",0,"15582",1);//设置已选中的地区
                    dialog.setSelectorAreaPositionListener(this);
                    dialog.show();
                }
                break;
            case R.id.dateTime:
            case R.id.dateTimeMore:
                DatePickDialog pickDialog=new DatePickDialog(this);
                pickDialog.setListener(new DatePickDialog.OnConfirmListener() {
                    @Override
                    public void onClick(String dateStr) {
                        dateTime.setText(dateStr);
                    }
                });
                pickDialog.show();
                break;
        }
    }
    /**
     * 根据code 来显示选择过的地区
     */
    private void getSelectedArea(){
        String province = addressDictManager.getProvince(provinceCode);
        String city = addressDictManager.getCity(cityCode);
        String county = addressDictManager.getCounty(countyCode);
        String street = addressDictManager.getStreet(streetCode);
        LogUtil.d("数据", "省份=" + province);
        LogUtil.d("数据", "城市=" + city);
        LogUtil.d("数据", "乡镇=" + county);
        LogUtil.d("数据", "街道=" + street);
       area.setText(province+city+county+street);
        dialogclose();
    }
    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        provinceCode = (province == null ? "" : province.code);
        cityCode = (city == null ? "" : city.code);
        countyCode = (county == null ? "" : county.code);
        streetCode = (street == null ? "" : street.code);
        LogUtil.d("数据", "省份id=" + provinceCode);
        LogUtil.d("数据", "城市id=" + cityCode);
        LogUtil.d("数据", "乡镇id=" + countyCode);
        LogUtil.d("数据", "街道id=" + streetCode);
        String s = (province == null ? "" : province.name) + (city == null ? "" : city.name) + (county == null ? "" : county.name) +
                (street == null ? "" : street.name);
        area.setText(s);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void dialogclose() {
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    public void selectorAreaPosition(int provincePosition, int cityPosition, int countyPosition, int streetPosition) {
        this.provincePosition = provincePosition;
        this.cityPosition = cityPosition;
        this.countyPosition = countyPosition;
        this.streetPosition = streetPosition;
        LogUtil.d("数据", "省份位置=" + provincePosition);
        LogUtil.d("数据", "城市位置=" + cityPosition);
        LogUtil.d("数据", "乡镇位置=" + countyPosition);
        LogUtil.d("数据", "街道位置=" + streetPosition);
        getSelectedArea();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null) {
                if (getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }


        return super.onTouchEvent(event);
    }
}
