/**   
 ** 功能描述：
 * @Package: com.wx.video.controller.api 
 * @author: jiguiquan   
 * @date: 2019年6月6日 下午1:58:07 
 */
package com.wx.video.controller.api;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wx.video.common.JsonResult;
import com.wx.video.dto.VideoDTO;
import com.wx.video.dto.WatchRecordDTO;
import com.wx.video.entity.Video;
import com.wx.video.entity.WatchRecord;
import com.wx.video.model.VideoPageVo;
import com.wx.video.model.VideoVo;
import com.wx.video.model.WatchRecordVo;
import com.wx.video.service.WatchRecordService;
import com.wx.video.utils.JwtUtils;

import io.jsonwebtoken.Claims;

/**
 * @author jiguiquan
 *
 */
@Controller
@CrossOrigin
@RequestMapping("/api/watch")
public class WatchRecordController {
	private final static Logger logger = LoggerFactory.getLogger(WatchRecordController.class);
    
    @Autowired
    private WatchRecordService watchRecordService;
    @Autowired
    private JwtUtils jwtUtils;

    @ResponseBody
    @RequestMapping(value = {"/save", "/insert"}, produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult save(@RequestBody @Validated WatchRecord record, HttpServletRequest request) {
        logger.info("新增观看记录");
        System.out.println(record);
        
        Claims claims = jwtUtils.getUserClaim(request);
    	System.out.println(claims);
    	String uid = claims.get("uid").toString();
    	String openid = claims.get("openid").toString();
        
    	record.setWatchTime(new Date());
    	record.setUid(Integer.parseInt(uid));
    	record.setOpenid(openid);
    	
        try {
            watchRecordService.save(record);
        } catch (Exception e) {
            logger.error("视频信息保存失败！", e);
            return JsonResult.error("视频信息保存失败！");
        }

        
        return JsonResult.successs("添加视频成功");
    }
   
    //查询我的观看课程
    @ResponseBody
    @RequestMapping(value = "myWatchRecord", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult myWatchRecord(HttpServletRequest request) {
    	Claims claims = jwtUtils.getUserClaim(request);
    	System.out.println(claims);
    	String uid = claims.get("uid").toString();
    	String openid = claims.get("openid").toString();
    	
    	//构建查询条件
    	WatchRecordVo recordVo = new WatchRecordVo();
    	recordVo.setUid(Integer.parseInt(uid));
    	
    	//获取时间点：
    	Calendar nowCal = Calendar.getInstance();  //获得当前日期对象
    	Calendar todayCal = Calendar.getInstance(); //今天凌晨的日期对象
    	Calendar yesCal = Calendar.getInstance();  //昨天凌晨的日期对象
    	
    	todayCal.set(nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH), 
    			nowCal.get(Calendar.DATE), 0, 0, 0);
    	
    	nowCal.add(Calendar.DATE, -1);
    	yesCal.set(nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH), 
    			nowCal.get(Calendar.DATE), 0, 0, 0);
    	//查询所有今天的观看记录
    	recordVo.setStartTime(todayCal.getTime());
    	System.out.println(recordVo);
    	List<WatchRecordDTO> todayList = watchRecordService.myWatchRecord(recordVo);
    	
    	//查看昨天的观看记录
    	recordVo.setStartTime(yesCal.getTime());
    	recordVo.setEndTime(todayCal.getTime());
    	System.out.println(recordVo);
    	List<WatchRecordDTO> yesList = watchRecordService.myWatchRecord(recordVo);
    	
    	//查看更早之前的观看记录(限制10条)
    	recordVo.setStartTime(null);
    	recordVo.setEndTime(yesCal.getTime());
    	System.out.println(recordVo);
    	List<WatchRecordDTO> earlyList = watchRecordService.myWatchRecord(recordVo);
    	
    	Map<String, Object> map = new HashMap<>();
    	map.put("todayList", todayList);
    	map.put("yesList", yesList);
    	map.put("earlyList", earlyList);
    	
		return JsonResult.successs(map);
	}
}
