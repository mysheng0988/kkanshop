package com.mysheng.office.kkanshop;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mysheng.office.kkanshop.adapter.CommentAdapter;
import com.mysheng.office.kkanshop.entity.EvaluateModel;
import com.mysheng.office.kkanshop.entity.IndexTools;
import com.mysheng.office.kkanshop.entity.SelectModel;
import com.mysheng.office.kkanshop.entity.TypeModel;
import com.mysheng.office.kkanshop.imagesWatcher.ImageWatcher;
import com.mysheng.office.kkanshop.view.MessagePicturesLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 评论页面
 */
public class CommentFragment extends Fragment implements MessagePicturesLayout.Callback  {


    public CommentFragment() {
        // Required empty public constructor
    }


    private static CommentFragment fragment = null;

    public static CommentFragment newInstance() {
        if (fragment == null) {
            fragment = new CommentFragment();
        }
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private ImageWatcher vImageWatcher;
    private List<TypeModel> typeModes=new ArrayList<>();

    private List<String>  selectItems= Arrays.asList("全部123","最近25","好评101","中评20","差评2");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.common_recycler_view, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView=view.findViewById(R.id.commonRecycler);
        vImageWatcher = ImageWatcher.Helper.with(getActivity()) // 一般来讲， ImageWatcher 需要占据全屏的位置
               // .setTranslucentStatus(!isTranslucentStatus ? Utils.calcStatusBarHeight(this) : 0) // 如果是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
                .setErrorImageRes(R.mipmap.error_picture) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setHintMode(ImageWatcher.POINT)//设置指示器（默认小白点）
                .setHintColor(getResources().getColor(R.color.red), getResources().getColor(R.color.white))//设置指示器颜色
                //.setOnPictureLongPressListener(getActivity()) // 长按图片的回调，你可以显示一个框继续提供一些复制，发送等功能
                .setLoader(new ImageWatcher.Loader() {//调用show方法前，请先调用setLoader 给ImageWatcher提供加载图片的实现
                    @Override
                    public void load(Context context, String url, final ImageWatcher.LoadCallback lc) {
                        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {

                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                lc.onResourceReady(resource);
                            }
                        });
                    }
                })
                .create();
        typeModes.clear();

        SelectModel selectModel=new SelectModel();
        selectModel.setPraise(82);
        selectModel.setTypeParam(IndexTools.SELECT);
        selectModel.setSelectitem(selectItems);
        typeModes.add(selectModel);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
       // mRecyclerView.addItemDecoration(new ViewLineDivider(LinearLayoutManager.VERTICAL, 2, 0xFFCCCCCC));
      //  mRecyclerView.addItemDecoration(new DividerGridItemDecoration());
        Random random=new Random();
        int len=random.nextInt(50);
        for (int i=0;i<len;i++){
            EvaluateModel model=new EvaluateModel();
            model.setTypeParam(IndexTools.EVALUATE);
            model.setComment("十分仲意性价比超高好喜欢哦 听说客服小姐姐才华横溢 请小姐姐作一首诗《吾爱莉莉》十分感谢哦爱你哦");
            model.setGoodsType("红色,128G");
            List <String> images=IndexTools.pictureList;
            int index=random.nextInt(10);
            images=images.subList(index,index+7);
            model.setGoodsImgPath(images);
            model.setUserName("15****555");
            model.setStrData("2018-10-10");
            float num=random.nextFloat()*5;
            model.setScore(num);
            typeModes.add(model);
        }
        if(mAdapter==null){
            mAdapter=new CommentAdapter(KkanApplication.mContext,typeModes);
        }else{
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setPictureClickCallback(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onThumbPictureClick(ImageView i, List<ImageView> imageGroupList, List<String> urlList) {
        vImageWatcher.show(i, imageGroupList, urlList);
    }
}
