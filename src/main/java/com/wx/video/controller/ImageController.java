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
import com.wx.video.entity.Image;
import com.wx.video.service.ImageService;
import com.wx.video.utils.UploadUtil;

@Controller
@CrossOrigin
@RequestMapping("/image")
public class ImageController {
private final static Logger logger = LoggerFactory.getLogger(ImageController.class);
    
    @Autowired
    private ImageService imageService;
    
    @Autowired
	private UploadUtil uploadUtil;

    @ResponseBody
    @RequestMapping(value = {"/save", "/insert"}, produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult save(@RequestBody @Validated Image model) {
//        logger.info("新增留言");
//        System.out.println(model);
//        try {
//            imageService.save(model);
//        } catch (Exception e) {
//            logger.error("留言保存失败！", e);
//            return JsonResult.error("留言保存失败！");
//        }
//
//        
        return JsonResult.successs();
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult delete(@PathVariable("id") Integer id) {
        logger.info("删除留言，ID:{}", id);
        System.out.println(id);
        if (id == null) {
            return JsonResult.failure("留言ID不可为空！");
        }

        try {
        	Image record = imageService.findById(id);
	        if (record == null) {
	            return JsonResult.error("留言不存在，无法删除！");
	        } else {
	            imageService.delete(id);
	        }
        } catch (Exception e) {
            logger.error("留言删除失败！", e);
            return JsonResult.error("留言删除失败！");
        }

        return JsonResult.successs();
    }

    @ResponseBody
    @RequestMapping(value = "/update", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult update(@RequestBody @Validated Image model) {
        logger.info("更新留言，ID:{}", model.getImgId());
        System.out.println(model);
        try {
        	Image record = imageService.findById(model.getImgId());
	        if (record == null) {
	            return JsonResult.error("留言不存在，无法更新！");
	        } else {
	           imageService.update(model);
	        }
        } catch (Exception e) {
            logger.error("留言更新失败！", e);
            return JsonResult.error("留言更新失败！");
        }

        return JsonResult.successs();
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public JsonResult get(@PathVariable("id") Integer id) {
        logger.info("查询留言，ID:{}", id);

        Image result = null;
        try {
            result = imageService.findById(id);
        } catch (Exception e) {
            logger.error("查询留言信息失败！", e);
            return JsonResult.error("查询留言信息失败！");
        }

        return JsonResult.successs(result);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/findAll", "/getAll"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResult findAll() {
        logger.info("查询全部留言信息");

        List<Image> resultList = null;
        try {
            resultList = imageService.findAll();
        } catch (Exception e) {
            logger.error("查询留言信息失败！", e);
            return JsonResult.error("查询留言信息失败！");
        }
        
        return JsonResult.successs(resultList);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/uploadImage")
    public String uploadImage(@RequestParam("upfile") MultipartFile upfile) {
    	return uploadUtil.uploadFile(upfile);
    }
}
