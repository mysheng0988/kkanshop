package com.mysheng.office.kkanshop.address.widget;


import com.mysheng.office.kkanshop.address.bean.City;
import com.mysheng.office.kkanshop.address.bean.County;
import com.mysheng.office.kkanshop.address.bean.Province;
import com.mysheng.office.kkanshop.address.bean.Street;

public interface OnAddressSelectedListener {
    void onAddressSelected(Province province, City city, County county, Street street);
}
