package com.wx.video.entity;

import java.util.Date;

public class Video {
    private String vid;  //videoId，必须

    private String vtitle;   //视频标题

    private String vauthor;   //发布人

    private String vsummary;   //摘要

    private String vurl;   //url

    private Double vprice;   //单价

    private Date createTime;   //创建时间

    private Integer heat;   //热度

    private String remark;   //

    private String filterA;   //

    private String filterB;

    private String filterC;

    private String filterD;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVtitle() {
        return vtitle;
    }

    public void setVtitle(String vtitle) {
        this.vtitle = vtitle;
    }

    public String getVauthor() {
        return vauthor;
    }

    public void setVauthor(String vauthor) {
        this.vauthor = vauthor;
    }

    public String getVsummary() {
        return vsummary;
    }

    public void setVsummary(String vsummary) {
        this.vsummary = vsummary;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }

    public Double getVprice() {
        return vprice;
    }

    public void setVprice(Double vprice) {
        this.vprice = vprice;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getHeat() {
        return heat;
    }

    public void setHeat(Integer heat) {
        this.heat = heat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFilterA() {
        return filterA;
    }

    public void setFilterA(String filterA) {
        this.filterA = filterA;
    }

    public String getFilterB() {
        return filterB;
    }

    public void setFilterB(String filterB) {
        this.filterB = filterB;
    }

    public String getFilterC() {
        return filterC;
    }

    public void setFilterC(String filterC) {
        this.filterC = filterC;
    }

    public String getFilterD() {
        return filterD;
    }

    public void setFilterD(String filterD) {
        this.filterD = filterD;
    }
}