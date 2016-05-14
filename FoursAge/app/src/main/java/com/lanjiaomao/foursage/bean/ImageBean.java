package com.lanjiaomao.foursage.bean;

import java.util.List;

/**
 * Created by root on 2016/5/14.
 */
public class ImageBean {
    private String topImagePath;
    private String folderName;
    private int imageCount;
    private List<String> images;
    public ImageBean(){

    }

    public ImageBean(String topImagePath, String folderName, int imageCount) {
        this.topImagePath = topImagePath;
        this.folderName = folderName;
        this.imageCount = imageCount;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "topImagePath='" + topImagePath + '\'' +
                ", folderName='" + folderName + '\'' +
                ", imageCount=" + imageCount +
                ", images=" + images +
                '}';
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTopImagePath() {
        return topImagePath;
    }

    public void setTopImagePath(String topImagePath) {
        this.topImagePath = topImagePath;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }
}
