package com.wx.video.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import com.wx.video.common.JsonResult;
import com.wx.video.entity.Video;
import com.wx.video.service.VideoService;
import com.wx.video.utils.UploadUtil;

@Controller
@CrossOrigin
@RequestMapping("/video")
public class VideoController {
	private final static Logger logger = LoggerFactory.getLogger(VideoController.class);
    
    @Autowired
    private VideoService videoService;
    
    @Autowired
	private UploadUtil uploadUtil;

    @ResponseBody
    @RequestMapping(value = {"/save", "/insert"}, produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult save(@RequestBody @Validated Video model) {
        logger.info("新增视频");
        System.out.println(model);
        try {
            videoService.save(model);
        } catch (Exception e) {
            logger.error("视频保存失败！", e);
            return JsonResult.error("视频保存失败！");
        }

        
        return JsonResult.successs();
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult delete(@PathVariable("id") Integer id) {
        logger.info("删除视频，ID:{}", id);
        System.out.println(id);
        if (id == null) {
            return JsonResult.failure("视频ID不可为空！");
        }

        try {
        	Video record = videoService.findById(id);
	        if (record == null) {
	            return JsonResult.error("视频不存在，无法删除！");
	        } else {
	            videoService.delete(id);
	        }
        } catch (Exception e) {
            logger.error("视频删除失败！", e);
            return JsonResult.error("视频删除失败！");
        }

        return JsonResult.successs();
    }

    @ResponseBody
    @RequestMapping(value = "/update", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult update(@RequestBody @Validated Video model) {
        logger.info("更新视频，ID:{}", model.getVid());
        System.out.println(model);
        try {
        	Video record = videoService.findById(model.getVid());
	        if (record == null) {
	            return JsonResult.error("视频不存在，无法更新！");
	        } else {
	           videoService.update(model);
	        }
        } catch (Exception e) {
            logger.error("视频更新失败！", e);
            return JsonResult.error("视频更新失败！");
        }

        return JsonResult.successs();
    }

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
    @RequestMapping(value = {"/findAll", "/getAll"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResult findAll() {
        logger.info("查询全部视频信息");

        List<Video> resultList = null;
        try {
            resultList = videoService.findAll();
        } catch (Exception e) {
            logger.error("查询视频信息失败！", e);
            return JsonResult.error("查询视频信息失败！");
        }
        
        return JsonResult.successs(resultList);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/uploadVideo")
    public String uploadVideo(@RequestParam("upfile") MultipartFile upfile) {
    	return uploadUtil.uploadFile(upfile);
    }
}
