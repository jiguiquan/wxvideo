/**   
 ** 功能描述：
 * @Package: com.wx.video.dto 
 * @author: jiguiquan   
 * @date: 2019年6月12日 下午12:30:05 
 */
package com.wx.video.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author jiguiquan
 *
 */
public class WatchRecordDTO {
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
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date watchTime;

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

	public Date getWatchTime() {
		return watchTime;
	}

	public void setWatchTime(Date watchTime) {
		this.watchTime = watchTime;
	}
}
