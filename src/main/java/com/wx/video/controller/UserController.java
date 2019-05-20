package com.wx.video.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wx.video.entity.User;
import com.wx.video.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/{id}")
	@ResponseBody
	public User get(@PathVariable("id") Integer id) {
		System.out.println(id);
		return userService.getUserById(id);
	}
}
