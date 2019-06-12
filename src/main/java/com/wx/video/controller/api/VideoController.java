/**   
 ** 功能描述：
 * @Package: com.wx.video.controller.api 
 * @author: jiguiquan   
 * @date: 2019年6月6日 下午1:58:07 
 */
package com.wx.video.controller.api;

import java.util.Date;
import java.util.List;

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
import com.wx.video.entity.Video;
import com.wx.video.model.VideoPageVo;
import com.wx.video.model.VideoVo;
import com.wx.video.service.VideoService;
import com.wx.video.utils.JwtUtils;

import io.jsonwebtoken.Claims;

/**
 * @author jiguiquan
 *
 */
@Controller
@CrossOrigin
@RequestMapping("/api/video")
public class VideoController {
	private final static Logger logger = LoggerFactory.getLogger(VideoController.class);
    
    @Autowired
    private VideoService videoService;
    @Autowired
    private JwtUtils jwtUtils;

    @ResponseBody
    @RequestMapping(value = "/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public JsonResult get(@PathVariable("id") Integer id) {
        logger.info("查询视频，ID:{}", id);

        Video result = null;
        try {
            result = videoService.findById(id);
        } catch (Exception e) {
            logger.error("查询视频信息失败！", e);
            return JsonResult.error("查询视频信息失败！");
        }

        return JsonResult.successs(result);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/findPage", "/getPage"}, produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult findPage(@RequestBody VideoPageVo pageable) {
    	//如果分页信息不存在，给设置个默认值
    	if (pageable.getPageSize() == null) {
			pageable.setPageSize(10);
		}
    	if (pageable.getPageNum() == null) {
			pageable.setPageNum(1);
		}
    	logger.info("查询视频分页列表, pageNum:{}, pageSize:{}", pageable.getPageNum(), pageable.getPageSize());
    	
    	List<VideoDTO> result = null;
        PageHelper.startPage(pageable);
        
        try {
            result = videoService.findVideoPage(pageable);
        } catch (Exception e) {
            logger.error("查询用户分页列表失败！", e);
            return JsonResult.error("查询用户分页列表失败！");
        }
        
        PageInfo<VideoDTO> resultPage = new PageInfo<>(result);
    	return JsonResult.successs(resultPage);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/findAll", "/getAll"}, produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult findAll(@RequestBody VideoVo videoVo) {
        logger.info("查询全部视频信息");

        videoVo.setShelf("Y");
        
        List<VideoDTO> resultList = null;
        try {
            resultList = videoService.findAll(videoVo);
        } catch (Exception e) {
            logger.error("查询视频信息失败！", e);
            return JsonResult.error("查询视频信息失败！");
        }
        
        return JsonResult.successs(resultList);
    }
    
    //查询我的已购课程
    @ResponseBody
    @RequestMapping(value = "myBought", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult myBought(HttpServletRequest request) {
    	Claims claims = jwtUtils.getUserClaim(request);
    	System.out.println(claims);
    	String uid = claims.get("uid").toString();
    	String openid = claims.get("openid").toString();
    	
    	List<VideoDTO> list = videoService.myBought(Integer.parseInt(uid));
		return JsonResult.successs(list);
	}
    
    
}
