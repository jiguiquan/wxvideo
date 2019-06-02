package com.wx.video.controller;

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
import com.wx.video.entity.User;
import com.wx.video.service.UserService;


/**
 * 处理用户登录
 * @author jiguiquan
 *
 */

@Controller
@CrossOrigin
public class LoginController {
	
//	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
//	@Autowired
//	private UserService userService;
//	
//	@ResponseBody
//    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
//	public JsonResult login(@RequestBody @Validated LoginInfo loginInfo, HttpServletRequest request) {
//		String loginName = loginInfo.getLoginName();
//		String pwd = loginInfo.getPassword();
//		
//		String password = DigestUtils.md5Hex(pwd);
//		User user = userService.findUserByLoginName(loginName);
//		if (user == null) {
//			return JsonResult.failure("当前用户不存在");
//		} else {
//			if (user.) {
//				
//			}
//			LoginUser loginUser = userService.getLoginUser(user.getId());
//			
//			request.getSession().setAttribute(AuthorizationFilter.USER_SESSION_KEY, loginUser);
//			
//			return JsonResult.successs(loginUser);
//		}
//	}
	
//	@RequestMapping(value = "/logout")
//	public String logout(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		
//		if(session.getAttribute(AuthorizationFilter.USER_SESSION_KEY) != null) {
//			LoginUser loginUser = (LoginUser) session.getAttribute(AuthorizationFilter.USER_SESSION_KEY);
//			logger.info("用户{}退出登录", loginUser.getUserName());
//			session.removeAttribute(AuthorizationFilter.USER_SESSION_KEY);
//		} 
//		
//		return "login";
//	}
//	
//	@PostMapping("/getCurrentUser")
//	@ResponseBody
//	public JsonResult getCurrentUser(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		LoginUser loginUser = (LoginUser) session.getAttribute(AuthorizationFilter.USER_SESSION_KEY);
//		return JsonResult.successs(loginUser);
//	}
}
