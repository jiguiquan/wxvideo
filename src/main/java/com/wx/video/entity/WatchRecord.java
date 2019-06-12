package com.wx.video.entity;

import java.util.Date;

public class WatchRecord {
    private Integer wid;

    private Integer uid;

    private String openid;

    private Integer vid;

    private String videoId;

    private String vtitle;

    private String vurl;

    private Date watchTime;

    private String spare;

    private String remark;

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVtitle() {
        return vtitle;
    }

    public void setVtitle(String vtitle) {
        this.vtitle = vtitle;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }

    public Date getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(Date watchTime) {
        this.watchTime = watchTime;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}