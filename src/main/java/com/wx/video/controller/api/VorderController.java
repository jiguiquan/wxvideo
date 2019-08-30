package com.wx.video.controller.api;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wx.video.common.JsonResult;
import com.wx.video.entity.TempData;
import com.wx.video.entity.TemplateMsg;
import com.wx.video.entity.User;
import com.wx.video.entity.Video;
import com.wx.video.entity.Vorder;
import com.wx.video.service.UserService;
import com.wx.video.service.VorderService;
import com.wx.video.utils.HttpClientUtil;
import com.wx.video.utils.JwtUtils;
import com.wx.video.utils.RedisOperator;
import com.wx.video.wxpay.sdk.MyConfig;
import com.wx.video.wxpay.sdk.WXPay;
import com.wx.video.wxpay.sdk.WXPayUtil;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import io.jsonwebtoken.Claims;
import me.chanjar.weixin.common.error.WxErrorException;

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
	@Autowired
	private RedisOperator redis;

	@ResponseBody
	@RequestMapping(value = { "/save", "/insert" }, produces = {
			"application/json;charset=UTF-8" }, method = RequestMethod.POST)
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
	@RequestMapping(value = "/integralPay", produces = {
			"application/json;charset=UTF-8" }, method = RequestMethod.POST)
	public JsonResult integralPay(@RequestBody Vorder order, HttpServletRequest request) {
		// 1、积分不足判断，2、重复购买判断

		Claims claims = jwtUtils.getUserClaim(request);
		System.out.println(claims);
		System.out.println(order);
		String uid = claims.get("uid").toString();
		String openid = claims.get("openid").toString();

		// 1、判断积分是否充足
		User user = userService.getUserById(Integer.parseInt(uid));
		if (user.getUintegral() < order.getOintegral()) {
			return JsonResult.error("你的积分不足");
		}

		// 2、判断是否重复购买
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
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wxPay", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult wxPay(@RequestBody Vorder order, HttpServletRequest request) {
		System.out.println(order);
		Claims claims = jwtUtils.getUserClaim(request);
		System.out.println(claims);
		System.out.println(order);
		String uid = claims.get("uid").toString();
		String openid = claims.get("openid").toString();

		// 判断是否重复购买
		List<Integer> list = vorderService.myOrderVidList(Integer.parseInt(uid));
		System.out.println(list);
		if (list.contains(order.getVid())) {
			return JsonResult.error("当前课程你已经购买过，无需重复购买");
		}

		// 没有购买过的情况下，创建新订单
		// 商户订单号
		String out_trade_no = WXPayUtil.generateNonceStr();

		// 开始进入构建微信支付环境
		Map resultMap = new HashMap();

		MyConfig config = null;
		WXPay wxpay = null;
		try {
			config = new MyConfig();
			wxpay = new WXPay(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 生成的随机字符串
		String nonce_str = WXPayUtil.generateNonceStr();
		// 获取客户端的ip地址
		// 获取本机的ip地址
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String spbill_create_ip = addr.getHostAddress();
		// 支付金额，需要转成字符串类型，否则后面的签名会失败
		int total_fee = (int) (order.getOprice() * 100);
		System.out.println("付款金额为=" + total_fee);
		// 商品描述
		String body = order.getDescription();

		// 统一下单接口参数
		HashMap<String, String> data = new HashMap<String, String>();
		data.put("appid", "wx0fb11492cabd2544");
		data.put("mch_id", "1536427101");
		data.put("nonce_str", nonce_str);
		data.put("body", body);
		data.put("out_trade_no", out_trade_no);
		data.put("total_fee", String.valueOf(total_fee));
		data.put("spbill_create_ip", spbill_create_ip);
		data.put("notify_url", "https://www.wenchuangshixuan.com/wxvideo/api/order/notify");
		data.put("trade_type", "JSAPI");
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
				// 再一次签名之后，再进行预付订单的创建，这样可以同时将prepay_id一并存入数据库
				order.setOutTradeNo(out_trade_no);
				order.setUid(Integer.parseInt(uid));
				order.setOpenid(openid);
				order.setStatus("0");
				order.setOtime(new Date());
				order.setOtype("wxPay");
				order.setPrepayId(prepayid);
				int count = vorderService.save(order);
				if (count != 1) {
					return JsonResult.error("新增订单失败");
				}

				System.out.println("新增订单成功");

				// 开始进行第二次签名
				resultMap.put("package", "prepay_id=" + prepayid);
				resultMap.put("signType", "HMAC-SHA256");
				// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
				resultMap.put("timeStamp", timeStamp + "");
				// 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
				resultMap.put("appId", "wx0fb11492cabd2544");
				String sign = WXPayUtil.generateSignature(resultMap, "xKLPpyJOORlkVmu1dujovptx1PuxqqIx");
				resultMap.put("paySign", sign);
				System.out.println("生成的签名paySign : " + sign);

				System.out.println("返回结果为" + resultMap);
				return JsonResult.successs(resultMap);
			} else {
				return JsonResult.error("失败了");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.error("出错了");
		}
	}

	@RequestMapping("/notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");

		InputStream is = null;
		String resXml = null;

		try {
			is = request.getInputStream();// 获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
			// 将inputstream转化为String
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			resXml = sb.toString();
			is.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		System.out.println("====微信回调的结果====" + resXml);

		Map<String, String> notifyMap = new HashMap<>();
		try {
			notifyMap = WXPayUtil.xmlToMap(resXml);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		System.out.println(notifyMap);
		
		// 将微信发的xml转map
		if (notifyMap.get("return_code").equals("SUCCESS")) {
			if (notifyMap.get("result_code").equals("SUCCESS")) {
				String ordersSn = notifyMap.get("out_trade_no");// 商户订单号
				// 业务处理开始，根据out_trade_no去数据库中查找到对应的数据记录
				Vorder record = vorderService.getOrderByTradeNo(notifyMap.get("out_trade_no"));
				record.setStatus("1");
				record.setTimeEnd(new Date());
				record.setTransactionId(notifyMap.get("transaction_id"));
				vorderService.update(record);
				System.out.println("更新数据库完成");
				
				//给此用户增加积分
				String open_id = record.getOpenid();
				Integer total_fee = Integer.valueOf(notifyMap.get("total_fee"));  //单位为分
				Double integral = (double) (total_fee/10);
				System.out.println(open_id);
				System.out.println(total_fee);
				System.out.println(integral);
				
				userService.addIntegral(open_id, integral);
				System.out.println("为用户增加积分成功");
				
				// 业务处理结束

				// 发送一次模板消息
				// 准备模板消息需要的参数
				String openid = notifyMap.get("openid");
				String formid = record.getPrepayId();
				User user = userService.selectByOpenId(openid);
				
				String accesstoken = redis.get("accessToken");
				System.out.println("redis获取到的accesstoken为"+accesstoken);
				
				
				// 重点是构建data
				Map<String, TempData> data = new HashMap<>();
				TempData keyword1 = new TempData();
				keyword1.setValue("339208499");
				keyword1.setColor("#173177");
				data.put("keyword1", keyword1);
				TempData keyword2 = new TempData();
				keyword2.setValue("2015年01月05日 12:30");
				keyword2.setColor("#173177");
				data.put("keyword2", keyword2);
				TempData keyword3 = new TempData();
				keyword3.setValue("粤海喜来登酒店");
				keyword3.setColor("#173177");
				data.put("keyword3", keyword3);
				TempData keyword4 = new TempData();
				keyword4.setValue("广州市天河区天河路208号");
				keyword4.setColor("#173177");
				data.put("keyword4", keyword4);

				// 封装调用模板消息的方法的实体类
				TemplateMsg template = new TemplateMsg();
				template.setTouser(openid);
				template.setTemplate_id("xFQ_KkMrwzPLxsr8voK25m0AESlC6hiSUZxpb8OrxYY");
				template.setPage("index");
				template.setForm_id(formid);
				template.setData(data);
				template.setEmphasis_keyword("keyword1.DATA");
				System.out.println(template);
				String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="
						+ accesstoken;
				System.out.println("url为"+url);

				push(url, template);
				// 发送模板消息完成
				resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>";
			}else {
				resXml = "<xml><return_code><![CDATA[FAIL]]></return_code></xml>";
			}
		}else {
			resXml = "<xml><return_code><![CDATA[FAIL]]></return_code></xml>";
		}

		// 告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
		try {
			response.getWriter().write(resXml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void push(String url, TemplateMsg templateMsg){
		String json = JSONObject.toJSONString(templateMsg);
		System.out.println(json);
		String wxResult = HttpClientUtil.doPostJson(url, json);

		// 从结果中取结果，判断
		JSONObject jsonObject = JSONObject.parseObject(wxResult);
		if ("ok".equals(jsonObject.get("errmsg").toString())) {
			System.out.println("模版消息发送成功");
		} else {
			try {
				throw new Exception("模版消息发送失败\n" + wxResult);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//获取accesstoken的接口
	@Scheduled(fixedDelay = 7200 * 1000)
	public void getAccessToken() {
		System.out.println("这里");
		Map<String, String> param = new HashMap<>();
        param.put("grant_type", "client_credential");
        param.put("appid", "wx0fb11492cabd2544");
        param.put("secret", "5c5e5efae8b303170018081b2b53976b");
        
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        String wxResult = HttpClientUtil.doGet(url, param);
        
        JSONObject jsonObject = JSONObject.parseObject(wxResult);
        System.out.println("调用accessToken的返回值为"+jsonObject);
        // 获取参数返回的
        String accessToken = jsonObject.get("access_token").toString();
        System.out.println("设置redis"+accessToken);
        redis.set("accessToken", accessToken);
	}
}
