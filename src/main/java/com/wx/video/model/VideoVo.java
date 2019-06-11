/**   
 ** 功能描述：
 * @Package: com.wx.video.model 
 * @author: jiguiquan   
 * @date: 2019年6月5日 上午10:42:01 
 */
package com.wx.video.model;

/**
 * @author jiguiquan
 *
 */
public class VideoVo {
	private String vtitle;  //标题模糊查询 

    private Integer heat;  
    
    private String shelf;

    private String filterA;

    private String filterB;

    private String filterC;

    private String filterD;

	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public String getVtitle() {
		return vtitle;
	}

	public void setVtitle(String vtitle) {
		this.vtitle = vtitle;
	}

	public Integer getHeat() {
		return heat;
	}

	public void setHeat(Integer heat) {
		this.heat = heat;
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
