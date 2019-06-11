/**   
 ** 功能描述：
 * @Package: com.wx.video.dto 
 * @author: jiguiquan   
 * @date: 2019年6月5日 上午11:06:03 
 */
package com.wx.video.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author jiguiquan
 *
 */
public class VideoDTO {
	private Integer vid;
	
	private String videoId;

    private String vtitle;

    private String vauthor;

    private String vsummary;
    
    private String vcover;  //

    private String vurl;
    
    private String shortName;
    
    private String vsmallImg;

    private Double vprice;
    
    private Double vintegral;  //积分价格

    private Double oldPrice;  //原价格

    private Double oldIntegral;  //原积分价格

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date createTime;

    private Integer heat;
    
    private Integer buys;  //已被购买次数
    
    private String shelf;

    private String remark;

    private String filterA;

    private String filterB;

    private String filterC;

    private String filterD;
    
    

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getVsmallImg() {
		return vsmallImg;
	}

	public void setVsmallImg(String vsmallImg) {
		this.vsmallImg = vsmallImg;
	}

	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public String getVcover() {
		return vcover;
	}

	public void setVcover(String vcover) {
		this.vcover = vcover;
	}

	public Integer getBuys() {
		return buys;
	}

	public void setBuys(Integer buys) {
		this.buys = buys;
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
