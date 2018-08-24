package com.mysheng.office.kkanshop.customCamera.compress;

import android.content.Context;
import android.util.Log;
import com.mysheng.office.kkanshop.customCamera.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * email：893855882@qq.com
 * data：16/12/31
 */

public class LuBanCompress implements CompressInterface {
    private List<LocalMedia> images;
    private CompressInterface.CompressListener listener;
    private Context context;
    private LubanOptions options;
    private ArrayList<File> files = new ArrayList<>();

    public LuBanCompress(Context context, CompressConfig config, List<LocalMedia> images,
                         CompressListener listener) {
        options = config.getLubanOptions();
        this.images = images;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void compress() {
        if (images == null || images.isEmpty()) {
            listener.onCompressError(images, " images is null");
            return;
        }
        for (LocalMedia image : images) {
            if (image == null) {
                listener.onCompressError(images, " There are pictures of compress  is null.");
                return;
            }

            files.add(new File(image.getPath()));
        }
        if (images.size() == 1) {
            compressOne();
        } else {
            compressMulti();
        }
    }

    private void compressOne() {
        Log.i("压缩档次::", options.getGrade() + "");
        Luban.compress(context, files.get(0))
                .putGear(options.getGrade())
                .setMaxHeight(options.getMaxHeight())
                .setMaxWidth(options.getMaxWidth())
                .setMaxSize(options.getMaxSize() / 1000)
                .launch(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        LocalMedia image = images.get(0);
                        image.setCompressPath(file.getPath());
                        image.setCompressed(true);
                        listener.onCompressSuccess(images);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onCompressError(images, e.getMessage() + " is compress failures");
                    }
                });
    }

    private void compressMulti() {
        Log.i("压缩档次::", options.getGrade() + "");
        Luban.compress(context, files)
                .putGear(options.getGrade())
                .setMaxSize(
                        options.getMaxSize() / 1000)                // limit the final image size（unit：Kb）
                .setMaxHeight(options.getMaxHeight())             // limit image height
                .setMaxWidth(options.getMaxWidth())
                .launch(new OnMultiCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(List<File> fileList) {
                        handleCompressCallBack(fileList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onCompressError(images, e.getMessage() + " is compress failures");
                    }
                });
    }

    private void handleCompressCallBack(List<File> files) {
        for (int i = 0, j = images.size(); i < j; i++) {
            String path = files.get(i).getPath();// 压缩成功后的地址
            LocalMedia image = images.get(i);
            // 如果是网络图片则不压缩
            if (path != null && path.startsWith("http")) {
                image.setCompressPath("");
            } else {
                image.setCompressed(true);
                image.setCompressPath(path);
            }
        }
        listener.onCompressSuccess(images);
    }
}
