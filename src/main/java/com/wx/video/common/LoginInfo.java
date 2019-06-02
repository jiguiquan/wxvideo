package com.wx.video.common;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class LoginInfo {

    @NotBlank
	private String loginName;
	
    @NotBlank
	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}

