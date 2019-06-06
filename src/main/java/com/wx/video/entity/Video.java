package com.wx.video.entity;

import java.util.Date;

public class Video {
    private String vid;  //videoid

    private String vtitle;  //标题

    private String vauthor;  //

    private String vsummary;  //简介

    private String vcover;  //

    private String vurl;  //视频url

    private Double vprice;  //价格

    private Double vintegral;  //积分价格

    private Double oldPrice;  //原价格

    private Double oldIntegral;  //原积分价格

    private Date createTime;  //

    private Integer heat;  //热度

    private Integer buys;  //已被购买次数

    private String remark;  //

    private String filterA;  //

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

    public String getVcover() {
        return vcover;
    }

    public void setVcover(String vcover) {
        this.vcover = vcover;
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

    public Double getVintegral() {
        return vintegral;
    }

    public void setVintegral(Double vintegral) {
        this.vintegral = vintegral;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Double getOldIntegral() {
        return oldIntegral;
    }

    public void setOldIntegral(Double oldIntegral) {
        this.oldIntegral = oldIntegral;
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

    public Integer getBuys() {
        return buys;
    }

    public void setBuys(Integer buys) {
        this.buys = buys;
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

	@Override
	public String toString() {
		return "Video [vid=" + vid + ", vtitle=" + vtitle + ", vauthor=" + vauthor + ", vsummary=" + vsummary
				+ ", vcover=" + vcover + ", vurl=" + vurl + ", vprice=" + vprice + ", vintegral=" + vintegral
				+ ", oldPrice=" + oldPrice + ", oldIntegral=" + oldIntegral + ", createTime=" + createTime + ", heat="
				+ heat + ", buys=" + buys + ", remark=" + remark + ", filterA=" + filterA + ", filterB=" + filterB
				+ ", filterC=" + filterC + ", filterD=" + filterD + ", getVid()=" + getVid() + ", getVtitle()="
				+ getVtitle() + ", getVauthor()=" + getVauthor() + ", getVsummary()=" + getVsummary() + ", getVcover()="
				+ getVcover() + ", getVurl()=" + getVurl() + ", getVprice()=" + getVprice() + ", getVintegral()="
				+ getVintegral() + ", getOldPrice()=" + getOldPrice() + ", getOldIntegral()=" + getOldIntegral()
				+ ", getCreateTime()=" + getCreateTime() + ", getHeat()=" + getHeat() + ", getBuys()=" + getBuys()
				+ ", getRemark()=" + getRemark() + ", getFilterA()=" + getFilterA() + ", getFilterB()=" + getFilterB()
				+ ", getFilterC()=" + getFilterC() + ", getFilterD()=" + getFilterD() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
    
    
}