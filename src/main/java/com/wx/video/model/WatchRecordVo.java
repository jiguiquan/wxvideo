/**   
 ** 功能描述：
 * @Package: com.wx.video.model 
 * @author: jiguiquan   
 * @date: 2019年6月12日 下午12:27:49 
 */
package com.wx.video.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author jiguiquan
 *
 */
public class WatchRecordVo {
	private Integer uid;
	
	private Date startTime;
	
	private Date endTime;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "WatchRecordVo [uid=" + uid + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
}
