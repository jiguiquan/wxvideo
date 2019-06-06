package com.wx.video.controller.admin;

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
@RequestMapping("/admin/image")
public class AdminImageController {
	private final static Logger logger = LoggerFactory.getLogger(AdminImageController.class);
    
    @Autowired
    private ImageService imageService;
    
    @Autowired
	private UploadUtil uploadUtil;

    @ResponseBody
    @RequestMapping(value = {"/save", "/insert"}, produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult save(@RequestBody @Validated Image model) {
        logger.info("新增图片");
        System.out.println(model);
        try {
            imageService.save(model);
        } catch (Exception e) {
            logger.error("图片保存失败！", e);
            return JsonResult.error("图片保存失败！");
        }

        return JsonResult.successs();
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult delete(@PathVariable("id") Integer id) {
        logger.info("删除图片，ID:{}", id);
        System.out.println(id);
        if (id == null) {
            return JsonResult.failure("图片ID不可为空！");
        }

        try {
        	Image record = imageService.findById(id);
	        if (record == null) {
	            return JsonResult.error("图片不存在，无法删除！");
	        } else {
	            imageService.delete(id);
	        }
        } catch (Exception e) {
            logger.error("图片删除失败！", e);
            return JsonResult.error("图片删除失败！");
        }

        return JsonResult.successs();
    }

    @ResponseBody
    @RequestMapping(value = "/update", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.POST)
    public JsonResult update(@RequestBody @Validated Image model) {
        logger.info("更新图片，ID:{}", model.getImgId());
        System.out.println(model);
        try {
        	Image record = imageService.findById(model.getImgId());
	        if (record == null) {
	            return JsonResult.error("图片不存在，无法更新！");
	        } else {
	           imageService.update(model);
	        }
        } catch (Exception e) {
            logger.error("图片更新失败！", e);
            return JsonResult.error("图片更新失败！");
        }

        return JsonResult.successs();
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", produces = { "application/json;charset=UTF-8" }, method = RequestMethod.GET)
    public JsonResult get(@PathVariable("id") Integer id) {
        logger.info("查询图片，ID:{}", id);

        Image result = null;
        try {
            result = imageService.findById(id);
        } catch (Exception e) {
            logger.error("查询图片信息失败！", e);
            return JsonResult.error("查询图片信息失败！");
        }

        return JsonResult.successs(result);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/findCarousels", "/getCarousels"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResult findCarousels() {
        logger.info("查询全部轮播图信息");

        List<Image> resultList = null;
        try {
            resultList = imageService.findCarousels();
        } catch (Exception e) {
            logger.error("查询图片信息失败！", e);
            return JsonResult.error("查询图片信息失败！");
        }
        
        return JsonResult.successs(resultList);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/uploadCarousel")
    public JsonResult uploadCarousel(@RequestParam("carousel") MultipartFile carousel) {
    	String url = uploadUtil.uploadFile(carousel);
    	
    	Image image = new Image();
    	image.setImgUrl(url);
    	image.setRemark("carousel");
    	
    	imageService.save(image);
		
    	Integer newId = image.getImgId();
    	
    	Image result = imageService.findById(newId);
    	
    	return JsonResult.successs(result);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/uploadImage")
    public JsonResult uploadImage(@RequestParam("upfile") MultipartFile upfile) {
    	String url = uploadUtil.uploadFile(upfile);
    	
    	Image image = new Image();
    	image.setImgId(1);
    	image.setImgUrl(url);
    	int count = imageService.update(image);
    	
    	if (count > 0) {
			return JsonResult.successs(url);
		}
    	
    	return JsonResult.error("图片保存失败");
    }
}
