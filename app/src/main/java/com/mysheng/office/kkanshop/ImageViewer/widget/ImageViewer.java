package com.mysheng.office.kkanshop.ImageViewer.widget;

import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.chrisbanes.photoview.PhotoView;
import com.mysheng.office.kkanshop.ImageViewer.Utils;
import com.mysheng.office.kkanshop.ImageViewer.data.ViewData;
import com.mysheng.office.kkanshop.ImageViewer.factory.ImageDragger;
import com.mysheng.office.kkanshop.ImageViewer.factory.ImageLoader;
import com.mysheng.office.kkanshop.ImageViewer.factory.ImageViewerState;
import com.mysheng.office.kkanshop.ImageViewer.listener.OnImageChangedListener;
import com.mysheng.office.kkanshop.ImageViewer.listener.OnViewClickListener;
import com.mysheng.office.kkanshop.ImageViewer.listener.OnViewLongClickListener;
import com.mysheng.office.kkanshop.ImageViewer.listener.OnWatchStatusListener;
import com.mysheng.office.kkanshop.ImageViewer.widget.viewpager.ImageAdapter;
import com.mysheng.office.kkanshop.ImageViewer.widget.viewpager.ImagePager;
import com.mysheng.office.kkanshop.R;
import com.github.chrisbanes.photoview.OnViewTapListener;
import java.util.List;

/**
 * 图片浏览器
 */
public class ImageViewer extends FrameLayout implements IImageViewer {
    private final LayoutParams FRAME_LAYOUT_PARAMS_MATCH = new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);
    // 默认的动画执行时间
    private final int DEF_ANIM_DURATION = 240;

    // 背景 View
    private View view_background;
    // 自定义 ViewPager
    private ImagePager viewPager;
    // 图片序号
    private TextView tv_index;
    // 当前的 PhotoView
    private PhotoView photoView_current;
    // ViewPager 的适配器
    private ImageAdapter mImageAdapter;

    /**
     * 内部数据
     */
    // 图片拖拽处理类
    private ImageDragHandler mImageDragHandler;
    // 屏幕的尺寸
    private Point mScreenSize;
    // 判断图片是否可以点击
    private boolean isPhotoClickalbe;
    // 判断图片的动画是否正在执行
    private boolean isPhotoAnimRunning;
    // 判断图片是否可缩放
    private boolean isImageZoomable;
    // 控件当前的状态
    private int mViewState;


    /**
     * 外部传递进来的数据
     */
    // 当前的 View 的位置
    private int mStartPosition;
    // 图片资源列表
    private List mImageList;
    // View 的数据列表
    private List<ViewData> mViewDataList;
    // 图片加载类
    private ImageLoader mImageLoader;
    // 是否显示图片序号
    private boolean showIndex;
    // 是否允许图片被拖拽
    private boolean doDragAction;
    // 是否执行启动动画
    private boolean doEnterAnim;
    // 是否执行关闭动画
    private boolean doExitAnim;
    // 动画执行时间
    private int mAnimDuration;
    // 图片切换监听
    private OnImageChangedListener mImageChangedListener;
    // 图片 View 的点击监听
    private OnViewClickListener mViewClickListener;
    // 图片 View 的长按点击监听
    private OnViewLongClickListener mViewLongClickListener;
    // 图片浏览状态监听
    private OnWatchStatusListener mWatchStatusListener;


    public ImageViewer(@NonNull Context context) {
        super(context);
        init(null);
    }

    public ImageViewer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ImageViewer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initAttr(attrs);
        initView();
    }

    private void initAttr(AttributeSet attrs) {
        showIndex = true;
        doDragAction = true;
        doEnterAnim = true;
        doExitAnim = true;
        mAnimDuration = DEF_ANIM_DURATION;
        isImageZoomable = true;
        mViewState = ImageViewerState.STATE_CLOSED;
        mScreenSize = Utils.getScreenSize(getContext());

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ImageViewer);
            if (a != null) {
                showIndex = a.getBoolean(R.styleable.ImageViewer_ivr_show_index, true);
                doDragAction = a.getBoolean(R.styleable.ImageViewer_ivr_drag_enable, true);
                doEnterAnim = a.getBoolean(R.styleable.ImageViewer_ivr_enter_anim, true);
                doExitAnim = a.getBoolean(R.styleable.ImageViewer_ivr_exit_anim, true);
                mAnimDuration = a.getInteger(R.styleable.ImageViewer_ivr_anim_duration, DEF_ANIM_DURATION);
                a.recycle();
            }
        }
    }

    private void initView() {
        // 添加背景 View 和 ViewPager
        view_background = new View(getContext());
        view_background.setBackgroundColor(Color.BLACK);
        view_background.setAlpha(0f);
        viewPager = new ImagePager(getContext());
        viewPager.setVisibility(GONE);
        addView(view_background, FRAME_LAYOUT_PARAMS_MATCH);
        addView(viewPager, FRAME_LAYOUT_PARAMS_MATCH);

        // 添加图片序号
        tv_index = new TextView(getContext());
        LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.setMargins(0, Utils.dp2px(getContext(), 8), 0, 0);
        textParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        tv_index.setLayoutParams(textParams);
        tv_index.setIncludeFontPadding(false);
        tv_index.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv_index.setTextColor(Color.WHITE);
        tv_index.setVisibility(GONE);
        addView(tv_index);

        // 配置 ViewPager
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                if (tv_index.getVisibility() == VISIBLE) {
                    tv_index.setText((position + 1) + "/" + mImageList.size());
                }
                final PhotoView photoView = mImageAdapter.getPhotoViewByPosition(position);
                photoView.setScale(1f, true);
                photoView_current = photoView;
                if (mImageChangedListener != null) {
                    mImageChangedListener.onImageSelected(position, photoView);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 获取焦点，否则 onKeyDown() 方法不执行
//        setFocusable(true);
//        setFocusableInTouchMode(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setImageBackground(Drawable drawable) {
        view_background.setBackground(drawable);
    }

    @Override
    public void setImageBackgroundResource(@DrawableRes int resid) {
        view_background.setBackgroundResource(resid);
    }

    @Override
    public void setImageBackgroundColor(@ColorInt int color) {
        view_background.setBackgroundColor(color);
    }

    @Override
    public void setStartPosition(int position) {
        this.mStartPosition = position;
    }

    @Override
    public <T> void setImageData(List<T> list) {
        mImageList = list;
    }

    @Override
    public void setViewData(List<ViewData> list) {
        this.mViewDataList = list;
    }

    @Override
    public void setImageLoader(ImageLoader loader) {
        mImageLoader = loader;
    }

    @Override
    public void showIndex(boolean show) {
        this.showIndex = show;
    }

    @Override
    public void doDragAction(boolean isDo) {
        this.doDragAction = isDo;
    }

    @Override
    public void doEnterAnim(boolean isDo) {
        this.doEnterAnim = isDo;
    }

    @Override
    public void doExitAnim(boolean isDo) {
        this.doExitAnim = isDo;
    }

    @Override
    public void setAnimDuration(int duration) {
        this.mAnimDuration = duration;
    }

    @Override
    public void setOnImageChangedListener(OnImageChangedListener listener) {
        this.mImageChangedListener = listener;
    }

    @Override
    public void setOnViewClickListener(OnViewClickListener listener) {
        this.mViewClickListener = listener;
    }

    @Override
    public void setOnViewLongClickListener(OnViewLongClickListener listener) {
        this.mViewLongClickListener = listener;
    }

    @Override
    public void setOnWatchStatusListener(OnWatchStatusListener listener) {
        this.mWatchStatusListener = listener;
    }

    @Override
    public void excuteEnterAnim() {
        isPhotoAnimRunning = true;
        // 动画执行时，设置 ViewPager 不可滑动
        viewPager.setScrollable(false);
        // 获取当前 View 的数据
        final ViewData viewData = mViewDataList.get(mStartPosition);
        // 缩放前的 View 的宽度
        final float beforeScale_width = viewData.getWidth();
        // 缩放前的 View 的高度
        final float beforeScale_height = viewData.getHeight();
        // 缩放后的 View 的宽度
        final float afterScale_width;
        // 缩放后的 View 的高度
        final float afterScale_heigt;
        // 缩放前的 View  的 X 轴，Y 轴的坐标
        final float from_x = viewData.getX();
        final float from_y = viewData.getY();
        // 是否定义了图片尺寸
        final boolean hasImageSize;
        // ImageViewer 的宽度和高度
        float parentW = getWidth(), parentH = getHeight();
        // 如果 ImageViewer 的宽度和高度为 0，即还未测量完成，则取屏幕宽高
        float previewW = parentW != 0 ? parentW : mScreenSize.x;
        float previewH = parentH != 0 ? parentH : mScreenSize.y;
        // 如果自定义了图片的尺寸，则使用图片的尺寸
        if (viewData.getImageWidth() != 0 && viewData.getImageHeight() != 0) {
            final float scale = Math.min(previewW / viewData.getImageWidth(), previewH / viewData.getImageHeight());
            afterScale_width = viewData.getImageWidth() * scale;
            afterScale_heigt = viewData.getImageHeight() * scale;
            photoView_current.setScaleType(ImageView.ScaleType.CENTER_CROP);
            hasImageSize = true;
        } else {
            afterScale_width = previewW;
            afterScale_heigt = previewH;
            hasImageSize = false;
        }
        // View 缩放后的 X 轴，Y 轴的坐标
        final float to_x = (previewW - afterScale_width) / 2;
        final float to_y = (previewH - afterScale_heigt) / 2;
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            FloatEvaluator evaluator = new FloatEvaluator();
            LayoutParams layoutParams = (LayoutParams) photoView_current.getLayoutParams();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float currentValue = (float) animation.getAnimatedValue();
                final float x = evaluator.evaluate(currentValue, from_x, to_x);
                final float y = evaluator.evaluate(currentValue, from_y, to_y);
                final float width = evaluator.evaluate(currentValue, beforeScale_width, afterScale_width);
                final float height = evaluator.evaluate(currentValue, beforeScale_height, afterScale_heigt);

                photoView_current.setX(x);
                photoView_current.setY(y);
                layoutParams.width = (int) width;
                layoutParams.height = (int) height;
                photoView_current.setLayoutParams(layoutParams);
                view_background.setAlpha(currentValue);
                if (currentValue == 1) {
                    handleImageIndex();
                    // 如果自定义了图片尺寸，在动画完成后，将 PhotoView 恢复到全屏状态
                    if (hasImageSize) {
                        photoView_current.setX(0);
                        photoView_current.setY(0);
                        layoutParams.width = getWidth() != 0 ? getWidth() : mScreenSize.x;
                        layoutParams.height = getHeight() != 0 ? getHeight() : mScreenSize.y;
                        photoView_current.setLayoutParams(layoutParams);
                        photoView_current.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    }
                    isPhotoClickalbe = true;
                    isPhotoAnimRunning = false;
                    viewPager.setScrollable(true);
                    if (mWatchStatusListener != null) {
                        mWatchStatusListener.onWatchStart(OnWatchStatusListener.State.STATE_COMPLETE_WATCH, mStartPosition, photoView_current);
                    }
                    mViewState = ImageViewerState.STATE_WATCHING;
                }
            }
        });
        animator.setDuration(mAnimDuration);
        animator.start();
    }

    @Override
    public void excuteExitAnim() {
        // 设置图片动画正在执行中
        isPhotoAnimRunning = true;
        // 退出动画执行时设置 ViewPager 不可滑动
        viewPager.setScrollable(false);
        // 隐藏图片索引号
        tv_index.setVisibility(GONE);
        // 设置当前的图片点击事件为空，防止在执行退出动画时，快速点击，多次执行退出本方法
        photoView_current.setOnViewTapListener(null);
        photoView_current.setOnLongClickListener(null);
        final int position = viewPager.getCurrentItem();
        final ViewData viewData = mViewDataList.get(position);
        final PhotoView photoView = mImageAdapter.getPhotoViewByPosition(position);
        photoView_current = photoView;
        // 如果图片处于放大状态，则先将图片恢复原样，再执行退出动画
        if (photoView_current != null && photoView_current.getScale() > 1f) {
            // 将图片恢复原样
            photoView_current.setScale(1, false);
        }

        // 获取 PhotoView 的图片
        final Drawable drawable = photoView_current.getDrawable();
        // 图片原始的宽度和高度
        float oriImg_width = 0, oriImg_height = 0;
        // 图片当前的宽度和高度
        float curImg_width = 0, curImg_height = 0;
        if (drawable != null) {
            oriImg_width = drawable.getIntrinsicWidth();
            oriImg_height = drawable.getIntrinsicHeight();
        } else if (viewData.getImageWidth() != 0 && viewData.getImageHeight() != 0) {
            oriImg_width = viewData.getImageWidth();
            oriImg_height = viewData.getImageHeight();
        }
        // ImageViewer 的宽度和高度
        float parentW = getWidth(), parentH = getHeight();
        // 如果图片原始的宽度和高度不等于 0，则先将 PhotoView 的宽高设置为图片当前的宽高，使动画看起来更流畅
        if (oriImg_width != 0 && oriImg_height != 0) {
            final float scale = Math.min(parentW / oriImg_width, parentH / oriImg_height);
            // 图片当前的宽度与高度
            curImg_width = oriImg_width * scale;
            curImg_height = oriImg_height * scale;
            // PhotoView 改变宽高后的坐标
            final float x = (parentW - curImg_width) / 2;
            final float y = (parentH - curImg_height) / 2;

            LayoutParams lp = (LayoutParams) photoView_current.getLayoutParams();
            lp.width = (int) curImg_width;
            lp.height = (int) curImg_height;
            photoView_current.setLayoutParams(lp);
            photoView_current.setX(x);
            photoView_current.setY(y);
            photoView_current.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        // 缩放前的 View 的宽度和高度
        final float beforeScale_width = curImg_width != 0 ? curImg_width : photoView_current.getWidth();
        final float beforeScale_height = curImg_height != 0 ? curImg_height : photoView_current.getHeight();
        // 缩放后的 View 的宽度和高度
        final float afterScale_width = viewData.getWidth();
        final float afterScale_heigt = viewData.getHeight();
        // 缩放前的 View 的 X 轴，Y 轴的坐标
        final float from_x = (parentW - beforeScale_width) / 2;
        final float from_y = (parentH - beforeScale_height) / 2;
        // 缩放后的 View 的 X 轴，Y 轴的坐标
        final float to_x = viewData.getX();
        final float to_y = viewData.getY();
        // 图片由自适应转为 CENTER_CROP 时的动画
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            FloatEvaluator evaluator = new FloatEvaluator();
            LayoutParams layoutParams = (LayoutParams) photoView_current.getLayoutParams();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float currentValue = (float) animation.getAnimatedValue();
                float x = evaluator.evaluate(currentValue, from_x, to_x);
                float y = evaluator.evaluate(currentValue, from_y, to_y);
                float width = evaluator.evaluate(currentValue, beforeScale_width, afterScale_width);
                float height = evaluator.evaluate(currentValue, beforeScale_height, afterScale_heigt);

                photoView_current.setX(x);
                photoView_current.setY(y);
                layoutParams.width = (int) width;
                layoutParams.height = (int) height;
                photoView_current.setLayoutParams(layoutParams);
                view_background.setAlpha(1 - currentValue);
                if (currentValue == 1) {
                    viewPager.setVisibility(GONE);
                    viewPager.setScrollable(true);
                    releaseMemory();
                    if (mWatchStatusListener != null) {
                        mWatchStatusListener.onWatchEnd(OnWatchStatusListener.State.STATE_COMPLETE_CLOSE);
                    }
                    mViewState = ImageViewerState.STATE_CLOSED;
                }
            }
        });
        animator.setDuration(mAnimDuration);
        animator.start();
    }

    @Override
    public void watch() {
        isPhotoClickalbe = false;
        isPhotoAnimRunning = false;
        viewPager.setMaxTranslateY(getHeight() != 0 ? getHeight() : mScreenSize.y);
        mImageAdapter = new ImageAdapter(this);
        mImageAdapter.setStartPosition(mStartPosition);
        mImageAdapter.setImageRes(mImageList);
        viewPager.setAdapter(mImageAdapter);
        // 设置图片是否可拖拽
        if (doDragAction) {
            if (mImageDragHandler == null) mImageDragHandler = new ImageDragHandler();
            viewPager.setImageDragger(mImageDragHandler);
        } else {
            viewPager.setImageDragger(null);
        }
        viewPager.setCurrentItem(mStartPosition, false);
        final PhotoView photoView = mImageAdapter.getPhotoViewByPosition(mStartPosition);
        photoView_current = photoView;
        if (mWatchStatusListener != null) {
            mWatchStatusListener.onWatchStart(OnWatchStatusListener.State.STATE_READY_WATCH, mStartPosition, photoView_current);
        }
        viewPager.setVisibility(VISIBLE);
        // 是否有启动动画
        if (doEnterAnim) {
            excuteEnterAnim();
        } else {
            view_background.setAlpha(1f);
            handleImageIndex();
            isPhotoClickalbe = true;
            isPhotoAnimRunning = false;
            if (mWatchStatusListener != null) {
                mWatchStatusListener.onWatchStart(OnWatchStatusListener.State.STATE_COMPLETE_WATCH, mStartPosition, photoView_current);
            }
            mViewState = ImageViewerState.STATE_WATCHING;
        }
    }

    /**
     * 创建 Item 视图
     *
     * @return
     */
    public View createItemView(int position) {
        final FrameLayout container = new FrameLayout(getContext());
        final PhotoView photoView = new PhotoView(getContext());
        photoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        photoView.setX(0);
        photoView.setY(0);
        photoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        photoView.setOnViewTapListener(new ViewTabListener(position));
        photoView.setOnLongClickListener(new ViewTabListener(position));
        photoView.setZoomable(isImageZoomable);
        photoView.setScale(1f, false);
        photoView.setId(position);
        // 自定义的图片加载方式
        if (mImageLoader != null) {
            mImageLoader.displayImage(position, mImageList.get(position), photoView);
        }
        container.addView(photoView);
        return container;
    }

    /**
     * 初始化 Item 视图的配置
     *
     * @param position
     * @param itemView
     */
    public void initItemViewConfig(int position, View itemView) {
        final PhotoView photoView = (PhotoView) ((FrameLayout) itemView).getChildAt(0);
        photoView.setX(0);
        photoView.setY(0);
        photoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        photoView.setOnViewTapListener(new ViewTabListener(position));
        photoView.setOnLongClickListener(new ViewTabListener(position));
        photoView.setZoomable(isImageZoomable);
        photoView.setScale(1f, true);
        photoView.setId(position);
        // 自定义的图片加载方式
        if (mImageLoader != null) {
            mImageLoader.displayImage(position, mImageList.get(position), photoView);
        }
    }

    /**
     * 图片序号显示处理
     */
    private void handleImageIndex() {
        if (showIndex) {
            if (mImageList != null && mImageList.size() > 1) {
                tv_index.setText((mStartPosition + 1) + "/" + mImageList.size());
                tv_index.setVisibility(View.VISIBLE);
            } else {
                tv_index.setVisibility(View.GONE);
            }
        } else {
            tv_index.setVisibility(View.GONE);
        }
    }

    @Override
    public void close() {
        isPhotoClickalbe = false;
        isPhotoAnimRunning = false;
        if (mWatchStatusListener != null) {
            mWatchStatusListener.onWatchEnd(OnWatchStatusListener.State.STATE_READY_CLOSE);
        }
        if (doExitAnim) {
            excuteExitAnim();
        } else {
            view_background.setAlpha(0);
            viewPager.setVisibility(GONE);
            tv_index.setVisibility(GONE);
            releaseMemory();
            if (mWatchStatusListener != null) {
                mWatchStatusListener.onWatchEnd(OnWatchStatusListener.State.STATE_COMPLETE_CLOSE);
            }
            mViewState = ImageViewerState.STATE_CLOSED;
        }
    }

    @Override
    public void clear() {
        if (mImageList != null && mImageList.size() > 0) {
            mImageList.clear();
        }
        if (mViewDataList != null && mViewDataList.size() > 0) {
            mViewDataList.clear();
        }
        releaseMemory();
        mImageLoader = null;
        mImageChangedListener = null;
        mViewClickListener = null;
        mWatchStatusListener = null;
        mImageDragHandler = null;
    }

    /**
     * 释放内存
     */
    private void releaseMemory() {
        if (mImageAdapter != null) {
            mImageAdapter.clear();
            mImageAdapter = null;
        }
        Utils.recycleImage(photoView_current);
        photoView_current = null;
    }

    @Override
    public void setImageZoomable(boolean zoomable) {
        isImageZoomable = zoomable;
    }

    @Override
    public boolean isImageZoomable() {
        return isImageZoomable;
    }

    @Override
    public float getImageScale() {
        return photoView_current != null ? photoView_current.getScale() : 1;
    }

    @Override
    public int getCurrentPosition() {
        if (viewPager != null) {
            return viewPager.getCurrentItem();
        }
        return 0;
    }

    @Override
    public View getCurrentView() {
        if (mImageAdapter != null) {
            return mImageAdapter.getViewByPosition(getCurrentPosition());
        }
        return null;
    }

    @Override
    public int getViewState() {
        return mViewState;
    }

    /**
     * 如果方法不执行，则是因为控件没有获取到焦点
     * 建议放到外部，手动调用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Back 键监听
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isPhotoAnimRunning) {
                if (mViewState == ImageViewerState.STATE_WATCHING) {
                    // 关闭浏览
                    close();
                }
                // 消耗掉 Back 事件，不向外传递
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clear();
    }

    /**
     * =============================================================================================
     * ==== PhotoView 的点击事件
     * =============================================================================================
     */
    private class ViewTabListener implements OnViewTapListener, PhotoView.OnLongClickListener {
        private int position;

        public ViewTabListener(int position) {
            this.position = position;
        }

        /**
         * 单击事件
         *
         * @param view
         * @param x
         * @param y
         */
        @Override
        public void onViewTap(View view, float x, float y) {
            if (isPhotoClickalbe && !isPhotoAnimRunning) {
                if (mViewClickListener != null) {
                    final boolean b = mViewClickListener.onViewClick(position, view, x, y);
                    // 判断是否消费点击事件，若消费，则后续方法不执行
                    if (b) {
                        return;
                    }
                }
                close();
            }
        }

        /**
         * 长按事件
         *
         * @param v
         * @return
         */
        @Override
        public boolean onLongClick(View v) {
            if (isPhotoClickalbe && !isPhotoAnimRunning) {
                if (mViewLongClickListener != null) {
                    return mViewLongClickListener.onViewLongClick(position, v);
                }
            }
            return false;
        }
    }

    /**
     * =============================================================================================
     * ==== 图片拖动处理类
     * =============================================================================================
     */
    private class ImageDragHandler implements ImageDragger {

        @Override
        public float getImageScale() {
            return ImageViewer.this.getImageScale();
        }

        @Override
        public void dragImage(final float y1, final float y2, final float alphaBase) {
            if (photoView_current != null && !isPhotoAnimRunning) {
                mViewState = ImageViewerState.STATE_DRAGGING;
                isPhotoClickalbe = false;
                // 计算本次拖拽时的 Y 轴坐标与上次拖拽时的 Y 轴坐标的差值
                final float diff = y2 - y1;
                // 计算 PhotoView 的当前 Y 轴坐标
                final float curY = photoView_current.getY() + diff;
                // 计算当前背景透明度
                float value = Math.abs(curY) / alphaBase;
                float alpha = value < 1 ? 1 - value : 0;
                photoView_current.setY(curY);
                view_background.setAlpha(alpha);
                if (mWatchStatusListener != null) {
                    mWatchStatusListener.onWatchDragging(photoView_current);
                }
            }
        }

        @Override
        public void releaseImage(final float maxTranslateY) {
            if (photoView_current != null && !isPhotoAnimRunning) {
                // 当 PhotoView 复位时，ViewPager 不可滑动
                viewPager.setScrollable(false);
                isPhotoAnimRunning = true;
                final float curY = photoView_current.getY();
                // 如果当前 Y 轴坐标的绝对值，小于等于最大极限值，则将图片恢复原位，否则移除屏幕
                if (Math.abs(curY) <= maxTranslateY) {
                    if (mWatchStatusListener != null) {
                        mWatchStatusListener.onWatchReset(OnWatchStatusListener.State.STATE_READY_RESET, photoView_current);
                    }
                    ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
                    animator.setDuration(150);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        FloatEvaluator evaluator = new FloatEvaluator();

                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            mViewState = ImageViewerState.STATE_RESETTING;
                            final float currentValue = (float) animation.getAnimatedValue();
                            final float y = evaluator.evaluate(currentValue, curY, 0);
                            final float alpha = evaluator.evaluate(currentValue, view_background.getAlpha(), 1);
                            photoView_current.setY(y);
                            view_background.setAlpha(alpha);
                            if (currentValue == 1) {
                                isPhotoClickalbe = true;
                                isPhotoAnimRunning = false;
                                viewPager.setScrollable(true);
                                if (mWatchStatusListener != null) {
                                    mWatchStatusListener.onWatchReset(OnWatchStatusListener.State.STATE_COMPLETE_RESET, photoView_current);
                                }
                                mViewState = ImageViewerState.STATE_WATCHING;
                            }
                        }
                    });
                    animator.start();
                } else {
                    if (mWatchStatusListener != null) {
                        mWatchStatusListener.onWatchEnd(OnWatchStatusListener.State.STATE_READY_CLOSE);
                    }
                    photoView_current.setOnViewTapListener(null);
                    photoView_current.setOnLongClickListener(null);
                    final int position = viewPager.getCurrentItem();
                    final ViewData viewData = mViewDataList.get(position);
                    final Drawable drawable = photoView_current.getDrawable();
                    float curImg_height = 0;
                    float oriImg_width = 0, oriImg_height = 0;
                    // 图片在 PhotoView 中的 Y 轴坐标
                    float imgY = 0;
                    if (drawable != null) {
                        // 图片原始的宽度和高度
                        oriImg_width = drawable.getIntrinsicWidth();
                        oriImg_height = drawable.getIntrinsicHeight();
                    } else if (viewData.getImageWidth() != 0 && viewData.getImageHeight() != 0) {
                        oriImg_width = viewData.getImageWidth();
                        oriImg_height = viewData.getImageHeight();
                    }
                    if (oriImg_width != 0 && oriImg_height != 0) {
                        final float scale = Math.min(mScreenSize.x / oriImg_width, mScreenSize.y / oriImg_height);
                        // 图片当前的高度
                        curImg_height = oriImg_height * scale;
                        // 图片在 PhotoView 中的 Y 轴坐标
                        imgY = (mScreenSize.y - curImg_height) / 2;
                    }
                    // 图片当前在屏幕中的 Y 轴坐标
                    float cutImgY = curY + imgY;
                    // 此处加 10 ,是为了减少误差，影响动画美观
                    final float toY = cutImgY > imgY ? curY + (mScreenSize.y - cutImgY + 10) : curY - (cutImgY + curImg_height + 10);
                    ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
                    animator.setDuration(200);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        FloatEvaluator evaluator = new FloatEvaluator();

                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            final float currentValue = (float) animation.getAnimatedValue();
                            final float y = evaluator.evaluate(currentValue, curY, toY);
                            final float alpha = evaluator.evaluate(currentValue, view_background.getAlpha(), 0);
                            photoView_current.setY(y);
                            view_background.setAlpha(alpha);
                            if (currentValue == 1) {
                                viewPager.setVisibility(GONE);
                                viewPager.setScrollable(true);
                                releaseMemory();
                                if (mWatchStatusListener != null) {
                                    mWatchStatusListener.onWatchEnd(OnWatchStatusListener.State.STATE_COMPLETE_CLOSE);
                                }
                                mViewState = ImageViewerState.STATE_CLOSED;
                            }
                        }
                    });
                    animator.start();
                }
            }
        }
    }
}
