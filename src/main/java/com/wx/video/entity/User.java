package com.wx.video.entity;

import java.util.Date;

public class User {
    private Integer uid;

    private String openid;

    private String uname;

    private String ugender;

    private String uaddress;

    private Double uintegral;

    private String uavatar;

    private String skey;

    private String sessionkey;

    private Date createTime;

    private Date updateTime;

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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUgender() {
        return ugender;
    }

    public void setUgender(String ugender) {
        this.ugender = ugender;
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }

    public Double getUintegral() {
        return uintegral;
    }

    public void setUintegral(Double uintegral) {
        this.uintegral = uintegral;
    }

    public String getUavatar() {
        return uavatar;
    }

    public void setUavatar(String uavatar) {
        this.uavatar = uavatar;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	@Override
	public String toString() {
		return "User [uid=" + uid + ", openid=" + openid + ", uname=" + uname + ", ugender=" + ugender + ", uaddress="
				+ uaddress + ", uintegral=" + uintegral + ", uavatar=" + uavatar + ", skey=" + skey + ", sessionkey="
				+ sessionkey + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
    
}