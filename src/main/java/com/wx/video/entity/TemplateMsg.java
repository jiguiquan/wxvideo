package com.wx.video.entity;

import java.util.Map;

/**   
 ** 功能描述：
 * @Package: com.wx.video.entity 
 * @author: jiguiquan   
 * @date: 2019年6月21日 下午8:01:46 
 */
public class TemplateMsg {
	private String touser;
	private String template_id;
	private String page;
	private String form_id;
	private Map<String,TempData> data;
	private String emphasis_keyword;
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getForm_id() {
		return form_id;
	}
	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}
	public Map<String, TempData> getData() {
		return data;
	}
	public void setData(Map<String, TempData> data) {
		this.data = data;
	}
	public String getEmphasis_keyword() {
		return emphasis_keyword;
	}
	public void setEmphasis_keyword(String emphasis_keyword) {
		this.emphasis_keyword = emphasis_keyword;
	}
}
