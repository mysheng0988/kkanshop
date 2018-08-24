package com.mysheng.office.kkanshop.customCamera.compress;

import java.io.Serializable;

/**
 * author：luck
 * project：PictureSelector
 * email：893855882@qq.com
 * data：16/12/31
 */

public class LubanOptions implements Serializable {
    /**
     * 压缩到的最大大小，单位B
     */
    private int maxSize;
    private int maxHeight;
    private int maxWidth;
    private int grade;

    /**
     * 小于 miniCompressSize 大小的图片不予以压缩, 单位KB.
     */
    private int miniCompressSize;

    private LubanOptions() {
    }

    public int getGrade() {
        if (grade == 0) {
            return Luban.THIRD_GEAR;
        }
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMiniCompressSize() {
        return miniCompressSize;
    }

    public void setMiniCompressSize(int miniCompressSize) {
        this.miniCompressSize = miniCompressSize;
    }


    public static class Builder {
        private LubanOptions options;

        public Builder() {
            options = new LubanOptions();
        }

        public Builder setMaxSize(int maxSize) {
            options.setMaxSize(maxSize);
            return this;
        }

        public Builder setGrade(int grade) {
            options.setGrade(grade);
            return this;
        }

        public Builder setMaxHeight(int maxHeight) {
            options.setMaxHeight(maxHeight);
            return this;
        }

        public Builder setMaxWidth(int maxWidth) {
            options.setMaxWidth(maxWidth);
            return this;
        }

        public Builder setMiniCompressSize(int miniCompressSize) {
            options.setMiniCompressSize(miniCompressSize);
            return this;
        }

        public LubanOptions create() {
            return options;
        }
    }
}
