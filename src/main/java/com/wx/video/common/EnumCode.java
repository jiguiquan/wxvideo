package com.wx.video.common;

public enum EnumCode {

	BAD_REQUEST("400001", "客户端请求参数错误"), NOT_LOGGED_IN("400002", "用户未登录");
	
	private String code;
	
	private String msg;
	
	private EnumCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
