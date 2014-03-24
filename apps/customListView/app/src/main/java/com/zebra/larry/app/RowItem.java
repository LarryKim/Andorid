package com.zebra.larry.app;

public class RowItem {
    private int mImageId;
    private String mTitle;
    private String mDesc;

    public RowItem(int imageId, String title, String desc) {
        this.mImageId = imageId;
        this.mTitle = title;
        this.mDesc = desc;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        this.mImageId = imageId;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        this.mDesc = desc;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    @Override
    public String toString() {
        return mTitle + "\n" + mDesc;
    }
}