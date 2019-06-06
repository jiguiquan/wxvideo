package com.wx.video.controller.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wx.video.common.JsonResult;
import com.wx.video.entity.User;
import com.wx.video.service.UserService;
import com.wx.video.utils.HttpClientUtil;
import com.wx.video.utils.JwtUtils;
import com.wx.video.utils.RedisOperator;
import com.wx.video.utils.UserClaims;

import ch.qos.logback.core.subst.Token;

@Controller
@RequestMapping("/api")
public class UserController {
	@Autowired
    private UserService userService;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private RedisOperator redis;
	
    @PostMapping("/wxlogin")
    @ResponseBody
    public JsonResult wxlogin(@RequestBody Map<String, Object> map){
    	
    	System.out.println(map);
        // 配置请求参数
        Map<String, String> param = new HashMap<>();
        param.put("appid", "wx0fb11492cabd2544");
        param.put("secret", "5c5e5efae8b303170018081b2b53976b");
        param.put("js_code", map.get("code").toString());
        param.put("grant_type", "authorization_code");
        
        System.out.println(param);
        // 发送请求
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String wxResult = HttpClientUtil.doGet(url, param);
        
        JSONObject jsonObject = JSONObject.parseObject(wxResult);
        // 获取参数返回的
        String sessionkey = jsonObject.get("session_key").toString();
        String openid = jsonObject.get("openid").toString();
        
        // 根据返回的user实体类，判断用户是否是新用户，不是的话，更新最新登录时间，是的话，将用户信息存到数据库
        User user = userService.selectByOpenId(openid);

        if(user != null){
        	System.out.println("1");
        	user.setUname(map.get("uname").toString());
        	user.setUavatar(map.get("uavatar").toString());
        	user.setUgender(map.get("ugender").toString());
        	user.setUaddress(map.get("address").toString());
            user.setSessionkey(sessionkey);  //修改sessionKey
            user.setUpdateTime(new Date());  //修改更新时间
            //更新数据库
            userService.update(user);
        }else{
        	System.out.println("2");
            User newUser = new User();
            newUser.setUid(openid);
            newUser.setSessionkey(sessionkey);
            newUser.setUname(map.get("uname").toString());
            newUser.setUavatar(map.get("uavatar").toString());
            newUser.setUgender(map.get("ugender").toString());
            newUser.setUaddress(map.get("address").toString());  
            newUser.setCreateTime(new Date());
            // 添加到数据库
            int count = userService.insert(newUser);
            if(count < 0){
                return JsonResult.error("插入数据失败");
            }
        }
        System.out.println("3");
        
        //生成JWTtoken 
        UserClaims userClaims = new UserClaims();
        userClaims.setUid(openid);
        String jwttoken = jwtUtils.createToken(userClaims);
        System.out.println(jwttoken);
        //将token存入Redis
        redis.set(jwttoken, sessionkey);
        
        // 封装返回小程序
        Map<String, String> result = new HashMap<>();
        result.put("token", jwttoken);
        result.put("open_id", openid);
        System.out.println(result);
        return JsonResult.successs(result);
    }
}
