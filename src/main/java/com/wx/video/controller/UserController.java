package com.wx.video.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wx.video.common.JsonResult;
import com.wx.video.entity.User;
import com.wx.video.service.UserService;
import com.wx.video.utils.HttpClientUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
    private UserService userService;

	@RequestMapping("/{id}")
	@ResponseBody
	public JsonResult get(@PathVariable("id") Integer id) {
		System.out.println(id);
		User user = new User();
		user.setId(1);
		user.setUname("吉桂权");
		
		return JsonResult.successs(user);
	}
	
	
    @PostMapping("/login")
    public JsonResult user_login(
            @RequestParam("code") String code,
            @RequestParam("userHead") String userHead,
            @RequestParam("userName") String userName,
            @RequestParam("userGender") String userGender,
            @RequestParam("userCity") String userCity,
            @RequestParam("userProvince") String userProvince
    ){
        // 配置请求参数
        Map<String, String> param = new HashMap<>();
        param.put("appid", "UserConstantInterface.WX_LOGIN_APPID");
        param.put("secret", "UserConstantInterface.WX_LOGIN_SECRET");
        param.put("js_code", code);
        param.put("grant_type", "UserConstantInterface.WX_LOGIN_GRANT_TYPE");
        // 发送请求
        String wxResult = HttpClientUtil.doGet("UserConstantInterface.WX_LOGIN_URL", param);
        JSONObject jsonObject = JSONObject.parseObject(wxResult);
        
        // 获取参数返回的
        String sessionkey = jsonObject.get("session_key").toString();
        String openid = jsonObject.get("openid").toString();
        // 根据返回的user实体类，判断用户是否是新用户，不是的话，更新最新登录时间，是的话，将用户信息存到数据库
        User user = userService.selectByOpenId(openid);
        if(user != null){
            user.setUpdateTime(new Date());
            userService.update(user);
        }else{
            User newUser = new User();
            newUser.setUid(openid);
            newUser.setSessionkey(sessionkey);
            
            // 添加到数据库
            int count = userService.insert(newUser);
            if(count < 0){
                return JsonResult.error("插入数据失败");
            }
        }
        // 封装返回小程序
        Map<String, String> result = new HashMap<>();
        result.put("session_key", sessionkey);
        result.put("open_id", openid);
        return JsonResult.successs(result);
    }
}
