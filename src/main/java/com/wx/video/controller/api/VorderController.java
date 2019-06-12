package com.wx.video.controller.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wx.video.common.JsonResult;
import com.wx.video.entity.User;
import com.wx.video.entity.Video;
import com.wx.video.entity.Vorder;
import com.wx.video.service.UserService;
import com.wx.video.service.VorderService;
import com.wx.video.utils.JwtUtils;

import io.jsonwebtoken.Claims;

@Controller
@CrossOrigin
@RequestMapping("/api/order")
public class VorderController {
private final static Logger logger = LoggerFactory.getLogger(VorderController.class);
    
    @Autowired
    private VorderService vorderService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @ResponseBody
    @RequestMapping(value = {"/save", "/insert"}, produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult save(@RequestBody @Validated Vorder model) {
        logger.info("新增视频");
        System.out.println(model);
//    	if (StringUtils.isBlank(model.getVid())) {
//			return JsonResult.error("vid不可为空");
//		}
    	
    	model.setOtime(new Date());
        
        try {
            vorderService.save(model);
        } catch (Exception e) {
            logger.error("视频信息保存失败！", e);
            return JsonResult.error("视频信息保存失败！");
        }

        
        return JsonResult.successs("添加视频成功");
    }
    
    @ResponseBody
    @RequestMapping(value = "/integralPay", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult integralPay(@RequestBody Vorder order, HttpServletRequest request) {
    	//1、积分不足判断，2、重复购买判断
    	
    	Claims claims = jwtUtils.getUserClaim(request);
    	System.out.println(claims);
    	System.out.println(order);
    	String uid = claims.get("uid").toString();
    	String openid = claims.get("openid").toString();
    	
    	//1、判断积分是否充足
    	User user = userService.getUserById(Integer.parseInt(uid));
    	if (user.getUintegral() < order.getOintegral()) {
			return JsonResult.error("你的积分不足");
		}
    	
    	//2、判断是否重复购买
    	List<Integer> list = vorderService.myOrderVidList(Integer.parseInt(uid));
    	System.out.println(list);
    	if (list.contains(order.getVid())) {
			return JsonResult.error("当前课程你已经购买过，无需重复购买");
		}
    	
    	order.setUid(Integer.parseInt(uid));
    	order.setOpenid(openid);
    	order.setOtype("igPay");
    	order.setOtime(new Date());
    	order.setStatus("1");
    	System.out.println(order);
    	
    	try {
    		vorderService.integralPay(order);
		} catch (Exception e) {
			logger.info("积分支付失败");
			return JsonResult.error("积分支付失败");
		}
    	
		return JsonResult.successs("积分支付成功");
	}
    
    
    
}
