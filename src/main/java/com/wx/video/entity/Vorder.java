package com.wx.video.entity;

import java.util.Date;

public class Vorder {
    private Integer oid;

    private Integer uid;

    private String openid;

    private Integer vid;

    private String videoId;

    private String otype;

    private Double oprice;

    private Double ointegral;

    private Date otime;

    private String status;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
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

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public Double getOprice() {
        return oprice;
    }

    public void setOprice(Double oprice) {
        this.oprice = oprice;
    }

    public Double getOintegral() {
        return ointegral;
    }

    public void setOintegral(Double ointegral) {
        this.ointegral = ointegral;
    }

    public Date getOtime() {
        return otime;
    }

    public void setOtime(Date otime) {
        this.otime = otime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}