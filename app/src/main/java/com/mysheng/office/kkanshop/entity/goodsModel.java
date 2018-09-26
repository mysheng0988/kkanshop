package com.mysheng.office.kkanshop.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by myaheng on 2018/9/26.
 */

public class goodsModel implements Serializable {
    private String goodsId;
    private String goodsCode;
    private String goodsName;
    private String shopId;
    private String shopName;
    private List<String> goodsPath;
    private int modelType=IndexTools.Recommend;
    private String goodsPrice;
    private String goodsOldPrice;
    private boolean isReduce;//是否满减
    private boolean isVoucher;//是否含优惠卷
    private String CreateDate;
    private String updateDate;
}
