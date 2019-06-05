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
	private String vid;

    private String vtitle;

    private String vauthor;

    private String vsummary;

    private String vurl;

    private Double vprice;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale="zh", timezone="GMT+8")
    private Date createTime;

    private Integer heat;

    private String remark;

    private String filterA;

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
