package com.wx.video.controller.admin;

import java.util.Date;
import java.util.List;

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
import com.wx.video.utils.UploadUtil;

@Controller
@CrossOrigin
@RequestMapping("/admin/video")
public class AdminVideoController {
	private final static Logger logger = LoggerFactory.getLogger(AdminVideoController.class);
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
	private UploadUtil uploadUtil;

    @ResponseBody
    @RequestMapping(value = {"/save", "/insert"}, produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult save(@RequestBody @Validated Video model) {
        logger.info("新增视频");
        System.out.println(model);
    	
    	model.setCreateTime(new Date());
        
        try {
            videoService.save(model);
        } catch (Exception e) {
            logger.error("视频信息保存失败！", e);
            return JsonResult.error("视频信息保存失败！");
        }

        
        return JsonResult.successs("添加视频成功");
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{vid}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult delete(@PathVariable("vid") Integer vid) {
        logger.info("删除视频，ID:{}", vid);
        System.out.println(vid);
        if (vid == null) {
            return JsonResult.failure("ID不可为空！");
        }
        
        Video record = videoService.findById(vid);
        if (record == null) {
        	return JsonResult.error("视频不存在，无法删除！");
		}

        try {
	            videoService.delete(vid);
	            return JsonResult.successs("视频删除成功");
        } catch (Exception e) {
            logger.error("视频删除失败！", e);
            return JsonResult.error("视频删除失败！");
        } 
    }

    @ResponseBody
    @RequestMapping(value = "/update", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult update(@RequestBody @Validated Video model) {
        logger.info("更新视频，ID:{}", model.getVid());
        System.out.println(model);
        
        Video record = videoService.findById(model.getVid());
        if (record == null) {
			return JsonResult.error("视频不存在");
		}
        
        try {
	        videoService.update(model);
	        return JsonResult.successs("视频信息更新成功");
        } catch (Exception e) {
            logger.error("视频更新失败！", e);
            return JsonResult.error("视频更新失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/{vid}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public JsonResult get(@PathVariable("vid") Integer vid) {
        logger.info("查询视频，ID:{}", vid);

        Video result = null;
        try {
            result = videoService.findById(vid);
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

        List<VideoDTO> resultList = null;
        try {
            resultList = videoService.findAll(videoVo);
        } catch (Exception e) {
            logger.error("查询视频信息失败！", e);
            return JsonResult.error("查询视频信息失败！");
        }
        
        return JsonResult.successs(resultList);
    }
    
    @ResponseBody
    @RequestMapping(value = "/shelf/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public JsonResult shelf(@PathVariable("id") Integer id) {
    	Video video = videoService.findById(id);
    	video.setShelf("Y");
    	videoService.update(video);
		return JsonResult.successs("上架成功");
	}
    
    @ResponseBody
    @RequestMapping(value = "/unShelf/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public JsonResult unShelf(@PathVariable("id") Integer id) {
    	Video video = videoService.findById(id);
    	video.setShelf("N");
    	videoService.update(video);
		return JsonResult.successs("下架成功");
	}
    
    @ResponseBody
    @RequestMapping(value = "/uploadVideo")
    public String uploadVideo(@RequestParam("upfile") MultipartFile upfile) {
    	return uploadUtil.uploadFile(upfile);
    }
}
