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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @RequestMapping(value = "/wxPay", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult wxPay(@RequestBody Vorder order, HttpServletRequest request) {
    	System.out.println(order);
    	Claims claims = jwtUtils.getUserClaim(request);
    	System.out.println(claims);
    	System.out.println(order);
    	String uid = claims.get("uid").toString();
    	String openid = claims.get("openid").toString();
    	
    	//判断是否重复购买
    	List<Integer> list = vorderService.myOrderVidList(Integer.parseInt(uid));
    	System.out.println(list);
    	if (list.contains(order.getVid())) {
			return JsonResult.error("当前课程你已经购买过，无需重复购买");
		}
    	
    	//没有购买过的情况下，创建新订单
    	//商户订单号
        String out_trade_no= WXPayUtil.generateNonceStr();
        order.setOutTradeNo(out_trade_no);
        order.setUid(Integer.parseInt(uid));
        order.setOpenid(openid);
        order.setStatus("0");
        order.setOtime(new Date());
        order.setOtype("wxPay");
        int count = vorderService.save(order);
        if (count != 1) {
			return JsonResult.error("新增订单失败");
		}
        
        System.out.println("新增订单成功");
    	
        
        //开始进入构建微信支付环境
        Map resultMap=new HashMap();
    	
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
        int total_fee=(int)(order.getOprice()*100);
        System.out.println("付款金额为="+total_fee);
        //商品描述
        String body = order.getDescription();
        
        //统一下单接口参数
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("appid", "wx0fb11492cabd2544");
        data.put("mch_id", "1536427101");
        data.put("nonce_str", nonce_str);
        data.put("body", body);
        data.put("out_trade_no",out_trade_no);
        data.put("total_fee", String.valueOf(total_fee));
        data.put("spbill_create_ip", spbill_create_ip);
        data.put("notify_url", "https://www.wenchuangshixuan.com/wxvideo/api/order/notify");
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
                resultMap.put("signType", "HMAC-SHA256");
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
    
    @RequestMapping("/notify")
    
    public String notify(HttpServletRequest request,HttpServletResponse response){
     
    //System.out.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");
     
    InputStream is = null;
    String resXml="";
     
    try {
     
	    is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
	     
	    //将inputstream转化为String
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
	    resXml=sb.toString();
	} catch (Exception e) {
		System.out.println(e);
		e.printStackTrace();
	}
    
    System.out.println("====微信回调的结果===="+resXml);
    
    try {
    	
    Map<String, String> notifyMap = WXPayUtil.xmlToMap(resXml);//将微信发的xml转map
     
     
     
    if(notifyMap.get("return_code").equals("SUCCESS")){
     
    if(notifyMap.get("result_code").equals("SUCCESS")){
     
    String ordersSn = notifyMap.get("out_trade_no");//商户订单号
     
    String amountpaid = notifyMap.get("total_fee");//实际支付的订单金额:单位 分
     
    BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);//将分转换成元-实际支付金额:元
     
    System.out.println(ordersSn);
    System.out.println(amountpaid);
    System.out.println(amountPay);
    
    //String openid = notifyMap.get("openid"); //如果有需要可以获取
     
    //String trade_type = notifyMap.get("trade_type");
     
    /*以下是自己的业务处理------仅做参考
    * 更新order对应字段/已支付金额/状态码
    */
//    Orders order = ordersService.selectOrdersBySn(ordersSn);  
//    if(order != null) {
//    order.setLastmodifieddate(new Date());
//    order.setVersion(order.getVersion().add(BigDecimal.ONE));
//    order.setAmountpaid(amountPay);//已支付金额
//    order.setStatus(2L);//修改订单状态为待发货
//    int num = ordersService.updateOrders(order);//更新order
//    String amount = amountPay.setScale(0, BigDecimal.ROUND_FLOOR).toString();//实际支付金额向下取整-123.23--123
    /*
    * 更新用户经验值
    */
//    Member member = accountService.findObjectById(order.getMemberId());
//    accountService.updateMemberByGrowth(amount, member);
    /*
    * 添加用户积分数及添加积分记录表记录
     
    */
//    pointService.updateMemberPointAndLog(amount, member, "购买商品,订单号为:"+ordersSn);
//    }
     
     
     
    }
     
    }
     
     
     
    //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可

    response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
     
    is.close();
     
    } catch (Exception e) {
     
    e.printStackTrace();
     
    }
     
    return null;
     
    }
    
//    @RequestMapping("/notify")
//    public String wxProPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
//    	logger.info("微信支付请求回调了");
//        String resXml = "";
//        Map<String, String> backxml = new HashMap<String, String>();
//        InputStream inStream;
//        try {
//            inStream = request.getInputStream();
//            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = inStream.read(buffer)) != -1) {
//                outSteam.write(buffer, 0, len);
//            }
//            outSteam.close();
//            inStream.close();
//            String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
//        }
//        String result1 =  "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
//        try {
//            response.getWriter().write(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    	logger.info("微信支付请求回调了");
//        String resXml = "";
//        Map<String, String> backxml = new HashMap<String, String>();
//        InputStream inStream;
//        try {
//            inStream = request.getInputStream();
//            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = inStream.read(buffer)) != -1) {
//                outSteam.write(buffer, 0, len);
//            }
//            outSteam.close();
//            inStream.close();
//            String result = new String(outSteam.toByteArray(), "utf-8");// 获取微信调用我们notify_url的返回信息
//            Map<String, String> map = WXPayUtil.xmlToMap(result);
//            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
//                if (WXPayUtil.isSignatureValid(map, "xKLPpyJOORlkVmu1dujovptx1PuxqqIx")) {
//                    System.out.println("微信支付-签名验证成功");
////                    backxml.put("return_code", "SUCCESS");
////                    backxml.put("return_msg", "OK");
////                    String toXml = WXPayUtil.mapToXml(backxml);
////                    response.getWriter().write(toXml);
//                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
//                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
//                    //业务处理开始
//                    Vorder record = vorderService.getOrderByTradeNo(map.get("out_trade_no"));
//                    record.setStatus("1");
//                    record.setTimeEnd(new Date());
//                    record.setTransactionId(map.get("transaction_id"));
//                    vorderService.update(record);
//                    //业务处理结束
//                }
//                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
//                out.write(resXml.getBytes());
//                out.flush();
//                out.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return resXml;
//    }
}
