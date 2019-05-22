package com.wx.video.common;

public class JsonResult {

	private int status;
	
	private String code;
	
	private String message;
	
	private Object data;
	
	public JsonResult(EnumResult enumResult, Object data) {
		this(enumResult, enumResult.getDesc(), data);
	}
	
	public JsonResult(EnumResult enumResult, String message, Object data) {
		this.status = enumResult.getCode();
		this.message = message;
		this.data = data;
	}
	
	public static JsonResult successs() {
		return successs(null);
	}
	
	public static JsonResult successs(Object data) {
		return new JsonResult(EnumResult.SUCCESS, data);
	}
	
	public static JsonResult failure(String message) {
		return failure(null, message);
	}
	
	public static JsonResult failure(String code, String message) {
		JsonResult result = new JsonResult(EnumResult.FAILURE, null);
		result.setCode(code);
		result.setMessage(message);
		return result;
	}

	public static JsonResult failure(EnumCode enumCode) {
		JsonResult result = new JsonResult(EnumResult.FAILURE, enumCode.getMsg(), null);
		result.setCode(enumCode.getCode());
		return result;
	}
	
	public static JsonResult error(String message) {
		return new JsonResult(EnumResult.ERROR, message, null);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg() {
		return message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
