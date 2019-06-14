package com.wx.video.controller.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.wx.video.wxpay.sdk.MyConfig;
import com.wx.video.wxpay.sdk.WXPay;
import com.wx.video.wxpay.sdk.WXPayUtil;

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
    
    
    /**
     * 微信统一下单接口
     * @return
     */
    @RequestMapping(value = "/doUnifiedOrder", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult doUnifiedOrder(HttpServletRequest request) {
    	System.out.println("这里");
        Map resultMap=new HashMap();
        
        Claims claims = jwtUtils.getUserClaim(request);
    	System.out.println(claims);
    	String uid = claims.get("uid").toString();
    	String openid = claims.get("openid").toString();
    	
//    	String openid = "oxm6Q4uwLuB_cUQrO6FTLEE-r4qk";
    	
        MyConfig config = null;
        WXPay wxpay =null;
        try {
            config = new MyConfig();
            wxpay= new WXPay(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //生成的随机字符串
        String nonce_str = WXPayUtil.generateNonceStr();
        //获取客户端的ip地址
        //获取本机的ip地址
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String spbill_create_ip = addr.getHostAddress();
        //支付金额，需要转成字符串类型，否则后面的签名会失败
        int  total_fee=1;
        //商品描述
        String body = "chefeizhifu";
        //商户订单号
        String out_trade_no= WXPayUtil.generateNonceStr();
        
        System.out.println("=====输出检验====");
        System.out.println(nonce_str);
        System.out.println(out_trade_no);
        System.out.println(spbill_create_ip);
        System.out.println(openid);
        
        //统一下单接口参数
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("appid", "wx0fb11492cabd2544");
        data.put("mch_id", "1536427101");
        data.put("nonce_str", nonce_str);
        data.put("body", body);
        data.put("out_trade_no",out_trade_no);
        data.put("total_fee", String.valueOf(total_fee));
        data.put("spbill_create_ip", spbill_create_ip);
        data.put("notify_url", "http://47.99.48.242");
        data.put("trade_type","JSAPI");
        data.put("openid", openid);
        try {
            Map<String, String> rMap = wxpay.unifiedOrder(data);
            System.out.println("统一下单接口返回: " + rMap);
            String return_code = (String) rMap.get("return_code");
            String result_code = (String) rMap.get("result_code");
            String nonceStr = WXPayUtil.generateNonceStr();
            resultMap.put("nonceStr", nonceStr);
            Long timeStamp = System.currentTimeMillis() / 1000;
            if ("SUCCESS".equals(return_code) && return_code.equals(result_code)) {
                String prepayid = rMap.get("prepay_id");
                resultMap.put("package", "prepay_id="+prepayid);
                resultMap.put("signType", "MD5");
                //这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                resultMap.put("timeStamp", timeStamp + "");
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                resultMap.put("appId","wx0fb11492cabd2544");
                String sign = WXPayUtil.generateSignature(resultMap, "xKLPpyJOORlkVmu1dujovptx1PuxqqIx");
                resultMap.put("paySign", sign);
                System.out.println("生成的签名paySign : "+ sign);
                
                System.out.println("返回结果为"+resultMap);
                return JsonResult.successs(resultMap);
            }else{
                return  JsonResult.error("失败了");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  JsonResult.error("出错了");
        }
    }
}
