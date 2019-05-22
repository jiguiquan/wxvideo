package com.wx.video.common;

public enum EnumResult {
	
	SUCCESS(200, "请求处理成功"), FAILURE(400, "请求参数出错"), ERROR(500, "请求处理出错");
	
	private int code;
	
	private String desc;
	
	private EnumResult(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
