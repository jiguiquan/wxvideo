package com.wx.video.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wx.video.common.JsonResult;
import com.wx.video.common.LoginInfo;
import com.wx.video.entity.SysUser;
import com.wx.video.entity.User;
import com.wx.video.service.SysUserService;
import com.wx.video.service.UserService;
import com.wx.video.web.AuthorizationFilter;


/**
 * 处理用户登录
 * @author jiguiquan
 *
 */

@Controller
@CrossOrigin
@RequestMapping("/admin")
public class AdminLoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminLoginController.class);
	@Autowired
	private SysUserService sysUserService;
	
	@ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
	public JsonResult login(@RequestBody @Validated LoginInfo loginInfo, HttpServletRequest request) {
		String loginName = loginInfo.getLoginName();
		String pwd = loginInfo.getPassword();
		
		String password = DigestUtils.md5Hex(pwd);
		SysUser sysUser = sysUserService.findSysUserByLoginName(loginName);
		if (sysUser == null) {
			return JsonResult.failure("当前用户不存在");
		} 
		
		if (!(password.equals(sysUser.getPassword()))) {
			return JsonResult.error("用户登录密码不正确");
		}
		
		HttpSession session = request.getSession();
		session.setAttribute(AuthorizationFilter.USER_SESSION_KEY, sysUser);
		
		return JsonResult.successs(sysUser);
	}
	
	@RequestMapping(value = "/logout")
	public JsonResult logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		if(session.getAttribute(AuthorizationFilter.USER_SESSION_KEY) != null) {
			SysUser loginUser = (SysUser) session.getAttribute(AuthorizationFilter.USER_SESSION_KEY);
			logger.info("用户{}退出登录", loginUser.getUsername());
			session.removeAttribute(AuthorizationFilter.USER_SESSION_KEY);
			return JsonResult.successs("用户注销成功");
		} else {
			return JsonResult.error("用户未登录");
		} 
	}
}
