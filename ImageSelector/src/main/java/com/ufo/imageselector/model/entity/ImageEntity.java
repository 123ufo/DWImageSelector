package com.ufo.imageselector.model.entity;

/**
 * 日期:2017/11/1
 * <p>
 * 作者:xudiwei
 * <p>
 * 描述:图片实体类
 */

public class ImageEntity {

    //图片的路径
    private String path;
    //图片存放的目录
    private String directory;
    //选中
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public ImageEntity() {
    }

    public ImageEntity(String path, String directory) {
        this.path = path;
        this.directory = directory;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "path='" + path + '\'' +
                ", directory='" + directory + '\'' +
                '}';
    }
}
