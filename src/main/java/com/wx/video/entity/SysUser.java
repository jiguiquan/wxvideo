package com.wx.video.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SysUser implements Serializable {
	private static final long serialVersionUID = 5772904081640636158L;

	private Integer id;

    private String loginName;

    private String username;

    @JsonIgnore
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}