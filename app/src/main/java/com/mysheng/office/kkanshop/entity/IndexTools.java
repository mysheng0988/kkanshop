package com.mysheng.office.kkanshop.entity;

import com.mysheng.office.kkanshop.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by myaheng on 2018/9/10.
 */

public class IndexTools {
    public static final  int BANNER=0X001;
    public static final  int NAV=0X002;
    public static final  int NOTICE=0X003;
    public static final  int KILL=0X004;
    public static final  int GOODSSHOP=0X005;
    public static final  int KILLTITLE=0X006;
    public static final  int SHOPTITLE=0X007;
    public static final  int LOVE_TWO=0X008;
    public static final  int LOVE_FOUR=0X009;
    public static final  int GOTitle=0X010;
    public static final  int GOShopTitle=0X011;
    public static final  int GOSHOPTWO=0X012;
    public static final  int GOSHOPFOUR=0X013;
    public static final  int GOReTitle=0X014;
    public static final  int Recommend=0X015;
    public static final  int OTHER=0X016;
    public static final  int SELECT=0X100;
    public static final  int EVALUATE=0X101;

    public static int[] navIcon={R.drawable.supermarket,R.drawable.trappings,R.drawable.travel,R.drawable.catering,R.drawable.fresh,R.drawable.delicatessen,R.drawable.grain_and_oil,R.drawable.icon_msg_service};
    public static String[] navTitle={"看看超市","看看服饰","看看出行","看看餐饮","生鲜水果","卤味熟食","粮油副食","信息服务"};
    public static String title="月莲2017新品大码女装风衣外套5876 3xl 新春上新品，优惠孔雀";

    public static  String[] list={
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3175/148/8125248346/324991/3a60c04b/58bfcea9Ne6a3b000.jpg!q70.jpg",
            "https://i1.mifile.cn/a1/pms_1528719476.67789934!220x220.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3340/213/1817228991/343685/1708b1bd/5832c627Na8aa2835.jpg!q70.jpg",
            "https://i1.mifile.cn/a1/pms_1527144859.25489991!220x220.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2698/337/3771040856/332790/e9bda7ef/579b3314Nb8ba841b.jpg!q70.jpg",
            "https://i1.mifile.cn/a4/xmad_14972549116226_tZpod.png",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2017/168/2929502301/199084/26bc6ea2/56f89acdNf309305b.jpg!q70.jpg",
            "https://i1.mifile.cn/a4/xmad_14972549116226_tZpod.png",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3634/155/1628172232/278042/abd6224d/582cf50dN71c37b18.jpg!q70.jpg",
            "http://i1.mifile.cn/a4/xmad_15281678020877_ZAHgw.jpg",
            "https://i1.mifile.cn/a1/pms_1492999959.43955760!220x220.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t4519/358/1436762985/248234/7dd51673/58df3545N999b9fb9.jpg!q70.jpg",
            "https://i1.mifile.cn/a4/xmad_14972549116226_tZpod.png",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2755/170/3941478123/227837/23139b91/57a4413bNaeae69fa.jpg!q70.jpg",
            "http://i1.mifile.cn/a4/xmad_15293127351522_gPtTj.jpg",
            "https://i1.mifile.cn/a1/pms_1519609640.9267740!140x140.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t4708/216/1115907562/188634/47c49299/58d87d50N766af4d9.jpg!q70.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2344/288/413741291/135184/c502311f/5608b901N7f95121d.jpg!q70.jpg",
            "http://i1.mifile.cn/a4/xmad_152940243093_EgRIT.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3136/252/5996020304/837203/e83544bf/58981df7Nf0877849.jpg!q70.jpg",
            "http://i1.mifile.cn/f/i/2018/mix2s/summary/infor-1.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3340/213/1817228991/343685/1708b1bd/5832c627Na8aa2835.jpg!q70.jpg",
            "https://i1.mifile.cn/a1/pms_1528092587.49664451!220x220.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2026/200/1690254391/9973/21194468/56694e7cNc7a03102.jpg!q70.jpg"
    };
    public static String[] Describe={
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t4519/358/1436762985/248234/7dd51673/58df3545N999b9fb9.jpg!q70.jpg",
            "https://i1.mifile.cn/a4/xmad_14972549116226_tZpod.png",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2755/170/3941478123/227837/23139b91/57a4413bNaeae69fa.jpg!q70.jpg",
            "http://i1.mifile.cn/a4/xmad_15293127351522_gPtTj.jpg",
            "https://i1.mifile.cn/a1/pms_1519609640.9267740!140x140.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t4708/216/1115907562/188634/47c49299/58d87d50N766af4d9.jpg!q70.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2344/288/413741291/135184/c502311f/5608b901N7f95121d.jpg!q70.jpg",
            "http://i1.mifile.cn/a4/xmad_152940243093_EgRIT.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3136/252/5996020304/837203/e83544bf/58981df7Nf0877849.jpg!q70.jpg",
            "http://i1.mifile.cn/f/i/2018/mix2s/summary/infor-1.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3340/213/1817228991/343685/1708b1bd/5832c627Na8aa2835.jpg!q70.jpg",
            "https://i1.mifile.cn/a1/pms_1528092587.49664451!220x220.jpg",
            "http://img10.360buyimg.com/n1/s450x450_jfs/t13459/165/1849162356/71608/94425578/5a2a2ea3Nc30d9428.jpg",
            "http://img10.360buyimg.com/n1/s450x450_jfs/t11845/73/694278454/68120/a4eb4468/59f69650Ndb06c709.jpg",
            "http://img11.360buyimg.com/n1/s450x450_jfs/t11680/317/723006781/63418/f644d838/59f69653N15893d32.jpg",
            "http://i1.mifile.cn/f/i/2018/mix2s/summary/infor-1.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3340/213/1817228991/343685/1708b1bd/5832c627Na8aa2835.jpg!q70.jpg",
            "https://i1.mifile.cn/a1/pms_1528092587.49664451!220x220.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2026/200/1690254391/9973/21194468/56694e7cNc7a03102.jpg!q70.jpg"
    };
    public static String[] shopIndex={
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t4519/358/1436762985/248234/7dd51673/58df3545N999b9fb9.jpg!q70.jpg",
            "https://i1.mifile.cn/a4/xmad_14972549116226_tZpod.png",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2755/170/3941478123/227837/23139b91/57a4413bNaeae69fa.jpg!q70.jpg",
            "http://i1.mifile.cn/a4/xmad_15293127351522_gPtTj.jpg",
            "https://i1.mifile.cn/a1/pms_1519609640.9267740!140x140.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t4708/216/1115907562/188634/47c49299/58d87d50N766af4d9.jpg!q70.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2344/288/413741291/135184/c502311f/5608b901N7f95121d.jpg!q70.jpg",
            "http://i1.mifile.cn/a4/xmad_152940243093_EgRIT.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3136/252/5996020304/837203/e83544bf/58981df7Nf0877849.jpg!q70.jpg",
            "http://i1.mifile.cn/f/i/2018/mix2s/summary/infor-1.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3340/213/1817228991/343685/1708b1bd/5832c627Na8aa2835.jpg!q70.jpg",
            "https://i1.mifile.cn/a1/pms_1528092587.49664451!220x220.jpg",
            "http://img10.360buyimg.com/n1/s450x450_jfs/t13459/165/1849162356/71608/94425578/5a2a2ea3Nc30d9428.jpg",
            "http://img10.360buyimg.com/n1/s450x450_jfs/t11845/73/694278454/68120/a4eb4468/59f69650Ndb06c709.jpg",
            "http://img11.360buyimg.com/n1/s450x450_jfs/t11680/317/723006781/63418/f644d838/59f69653N15893d32.jpg",
            "http://i1.mifile.cn/f/i/2018/mix2s/summary/infor-1.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3340/213/1817228991/343685/1708b1bd/5832c627Na8aa2835.jpg!q70.jpg",
            "https://i1.mifile.cn/a1/pms_1528092587.49664451!220x220.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2026/200/1690254391/9973/21194468/56694e7cNc7a03102.jpg!q70.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3175/148/8125248346/324991/3a60c04b/58bfcea9Ne6a3b000.jpg!q70.jpg",
            "https://i1.mifile.cn/a1/pms_1528719476.67789934!220x220.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3340/213/1817228991/343685/1708b1bd/5832c627Na8aa2835.jpg!q70.jpg",
            "https://i1.mifile.cn/a1/pms_1527144859.25489991!220x220.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2698/337/3771040856/332790/e9bda7ef/579b3314Nb8ba841b.jpg!q70.jpg",
            "https://i1.mifile.cn/a4/xmad_14972549116226_tZpod.png",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2017/168/2929502301/199084/26bc6ea2/56f89acdNf309305b.jpg!q70.jpg",
            "https://i1.mifile.cn/a4/xmad_14972549116226_tZpod.png",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3634/155/1628172232/278042/abd6224d/582cf50dN71c37b18.jpg!q70.jpg",
            "http://i1.mifile.cn/a4/xmad_15281678020877_ZAHgw.jpg",
            "https://i1.mifile.cn/a1/pms_1492999959.43955760!220x220.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t4519/358/1436762985/248234/7dd51673/58df3545N999b9fb9.jpg!q70.jpg",
            "https://i1.mifile.cn/a4/xmad_14972549116226_tZpod.png",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2755/170/3941478123/227837/23139b91/57a4413bNaeae69fa.jpg!q70.jpg",
            "http://i1.mifile.cn/a4/xmad_15293127351522_gPtTj.jpg",
            "https://i1.mifile.cn/a1/pms_1519609640.9267740!140x140.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t4708/216/1115907562/188634/47c49299/58d87d50N766af4d9.jpg!q70.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2344/288/413741291/135184/c502311f/5608b901N7f95121d.jpg!q70.jpg",
            "http://i1.mifile.cn/a4/xmad_152940243093_EgRIT.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3136/252/5996020304/837203/e83544bf/58981df7Nf0877849.jpg!q70.jpg",
            "http://i1.mifile.cn/f/i/2018/mix2s/summary/infor-1.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t3340/213/1817228991/343685/1708b1bd/5832c627Na8aa2835.jpg!q70.jpg",
            "https://i1.mifile.cn/a1/pms_1528092587.49664451!220x220.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2026/200/1690254391/9973/21194468/56694e7cNc7a03102.jpg!q70.jpg"
    };
    public static List<String> pictureList = Arrays.asList(
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t4519/358/1436762985/248234/7dd51673/58df3545N999b9fb9.jpg!q70.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2026/200/1690254391/9973/21194468/56694e7cNc7a03102.jpg!q70.jpg",
            "https://m.360buyimg.com//mobilecms/s276x276_jfs/t2755/170/3941478123/227837/23139b91/57a4413bNaeae69fa.jpg!q70.jpg",
            "http://i1.mifile.cn/a4/xmad_15293127351522_gPtTj.jpg",
            "https://i1.mifile.cn/a1/pms_1519609640.9267740!140x140.jpg",
            "http://img.my.csdn.net/uploads/201701/06/1483664940_9893.jpg",
            "http://img.my.csdn.net/uploads/201701/06/1483664940_3308.jpg",
            "http://img.my.csdn.net/uploads/201701/06/1483664927_3920.png",
            "http://img.my.csdn.net/uploads/201701/06/1483664926_8360.png",
            "http://img.my.csdn.net/uploads/201701/06/1483664926_6184.png",
            "http://img.my.csdn.net/uploads/201701/06/1483664925_8382.png",
            "https://i1.mifile.cn/a1/pms_1528092587.49664451!220x220.jpg",
            "http://img.my.csdn.net/uploads/201701/06/1483664925_2087.jpg",
            "http://img.my.csdn.net/uploads/201701/06/1483664777_5730.png",
            "http://img.my.csdn.net/uploads/201701/06/1483664741_1378.jpg",
            "http://img.my.csdn.net/uploads/201701/06/1483671689_9534.png",
            "http://img.my.csdn.net/uploads/201701/06/1483671689_2126.png",
            "http://img.my.csdn.net/uploads/201701/06/1483671703_7890.png"
            );
    public static List<String> nearby = Arrays.asList(
            "http://img14.360buyimg.com//babel/jfs/t1/4563/13/7112/141762/5ba4a238E390891d1/ff8d4ca0ed77d989.jpg",
            "http://img10.360buyimg.com//babel/jfs/t1/870/32/7739/183547/5ba5e321Efef3cd13/d4f256d781f51ce0.jpg",
            "http://img12.360buyimg.com//babel/jfs/t1/4284/20/7090/60769/5ba488b5E12048993/a10c77195a065b5c.jpg",
            "http://img11.360buyimg.com//babel/jfs/t1/3967/39/6943/135619/5ba48805Ef64b1c65/04188935c93b6f21.jpg",
            "http://img10.360buyimg.com/babel/jfs/t1/4057/39/6747/147974/5ba376b8Ec50a33c4/a0bd21904118e0b8.jpg",
            "http://img12.360buyimg.com/babel/jfs/t1/1845/7/7080/126905/5ba4b1ceEd82092ae/729b54b3c72afdfe.jpg",
            "http://img14.360buyimg.com/babel/jfs/t1/4515/20/8630/100459/5ba9fa60Ec4349b06/dcab7e1fc7f1258a.jpg",
            "http://img12.360buyimg.com/cms/jfs/t1/5351/38/8346/119263/5ba99ab2E160258b2/df50ae6b6f246880.jpg",
            "http://img11.360buyimg.com/cms/jfs/t1/2681/28/5450/105342/5b9f9ce8Ed5f3dddb/5d54dbda756e2f0c.jpg",
            "http://img10.360buyimg.com//babel/jfs/t1/3271/36/5893/50303/5ba1085aE9792c90c/9f3d5567742ed7c8.jpg",
            "http://m.360buyimg.com/babel/jfs/t1/1336/28/5481/123627/5b9fb331E7e3bf419/7449a731dfbe071a.jpg",
            "http://m.360buyimg.com/babel/jfs/t1/2079/2/5751/215295/5ba0abb2E3357b6bb/69a4671af9db669a.jpg"
    );
    public static List<String> market=Arrays.asList(
            "http://i1.mifile.cn/f/i/2018/mix2s/summary/infor-1.jpg",
		"http://i1.mifile.cn/a4/xmad_152940243093_EgRIT.jpg",
		"http://i1.mifile.cn/a4/xmad_1529377715473_xVjDr.jpg",
		"http://i1.mifile.cn/a4/xmad_15293127351522_gPtTj.jpg",
		"http://i1.mifile.cn/a4/xmad_15248221330196_tvCXl.jpg",
		"http://i1.mifile.cn/a4/xmad_15281678020877_ZAHgw.jpg",
		"https://res1.vmallres.com/shopdc/pic/3737747d-ca26-4070-99eb-c64eb469e101.png"
    );
    public static List<String> ONLYONE=Arrays.asList(
            "http://openfile.meizu.com/group1/M00/06/BC/Cgbj0FvINLWAZXL4AAz1Mo4DEhM073.png680x680.jpg"
    );
    public static List<String> TWO=Arrays.asList(
            "http://img.my.csdn.net/uploads/201701/06/1483664940_3308.jpg"
    );
    public static List<String> HUAWEI=Arrays.asList(
            "https://res1.vmallres.com/shopdc/pic/3737747d-ca26-4070-99eb-c64eb469e101.png"
    );
    public static List<String> BANNER_IMAGE=Arrays.asList(
           "http://i1.mifile.cn/f/i/2018/mix2s/summary/infor-1.jpg",
		"http://i1.mifile.cn/a4/xmad_152940243093_EgRIT.jpg",
		"http://i1.mifile.cn/a4/xmad_1529377715473_xVjDr.jpg",
		"http://i1.mifile.cn/a4/xmad_15293127351522_gPtTj.jpg",
		"http://i1.mifile.cn/a4/xmad_15248221330196_tvCXl.jpg",
		"http://i1.mifile.cn/a4/xmad_15281678020877_ZAHgw.jpg",
		"https://res1.vmallres.com/shopdc/pic/3737747d-ca26-4070-99eb-c64eb469e101.png"
    );
    public static List<String> BANNER_TITLE=Arrays.asList(
           "小米mix2s 3299起",
		"小米8-小米八周年纪念版",
		"小米6x",
		"小米游戏本",
		"小米九号平衡车",
		"红米5最高立减200元",
		"华为P20pro"
    );


    public static List<String> listShopId=Arrays.asList(
            "2015879501",
            "2015879502",
            "2015879503",
            "2015879504",
            "2015879505",
            "2015879506",
            "2015879507"
    );
    public static List<String> listGoodsId=Arrays.asList(
            "201587950101",
            "201587950202",
            "201587950303",
            "201587950404",
            "201587950505",
            "201587950606",
            "201587950707"
    );


}
