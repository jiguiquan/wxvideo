package com.wx.video.common;

import java.io.Serializable;

public class LoginUser implements Serializable {
	private static final long serialVersionUID = 1622600288329122547L;

	private Long userId; //用户ID
	
	private String loginName; // 登录名
	
	private String userName; // 用户名
	
	private String mobile;

    private String email;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "LoginUser [userId=" + userId + ", loginName=" + loginName + ", userName=" + userName + ", mobile="
				+ mobile + ", email=" + email + "]";
	}
}
