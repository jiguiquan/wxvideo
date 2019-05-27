package com.wx.video.entity;

public class Image {
    private Integer imgId;

    private String imgName;

    private String imgUrl;

    private Double imgSize;

    private String remark;

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Double getImgSize() {
        return imgSize;
    }

    public void setImgSize(Double imgSize) {
        this.imgSize = imgSize;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}