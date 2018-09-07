package com.mysheng.office.kkanshop.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.mysheng.office.kkanshop.banner.transformer.AccordionTransformer;
import com.mysheng.office.kkanshop.banner.transformer.BackgroundToForegroundTransformer;
import com.mysheng.office.kkanshop.banner.transformer.CubeInTransformer;
import com.mysheng.office.kkanshop.banner.transformer.CubeOutTransformer;
import com.mysheng.office.kkanshop.banner.transformer.DefaultTransformer;
import com.mysheng.office.kkanshop.banner.transformer.DepthPageTransformer;
import com.mysheng.office.kkanshop.banner.transformer.FlipHorizontalTransformer;
import com.mysheng.office.kkanshop.banner.transformer.FlipVerticalTransformer;
import com.mysheng.office.kkanshop.banner.transformer.ForegroundToBackgroundTransformer;
import com.mysheng.office.kkanshop.banner.transformer.RotateDownTransformer;
import com.mysheng.office.kkanshop.banner.transformer.RotateUpTransformer;
import com.mysheng.office.kkanshop.banner.transformer.ScaleInOutTransformer;
import com.mysheng.office.kkanshop.banner.transformer.StackTransformer;
import com.mysheng.office.kkanshop.banner.transformer.TabletTransformer;
import com.mysheng.office.kkanshop.banner.transformer.ZoomInTransformer;
import com.mysheng.office.kkanshop.banner.transformer.ZoomOutSlideTransformer;
import com.mysheng.office.kkanshop.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
