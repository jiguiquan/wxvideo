/**   
 ** 功能描述：
 * @Package: com.wx.video.controller.api 
 * @author: jiguiquan   
 * @date: 2019年6月6日 下午3:06:59 
 */
package com.wx.video.controller.api;

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
import com.wx.video.controller.admin.AdminImageController;
import com.wx.video.entity.Image;
import com.wx.video.service.ImageService;
import com.wx.video.utils.UploadUtil;

/**
 * @author jiguiquan
 *
 */
@Controller
@CrossOrigin
@RequestMapping("/api/image")
public class ImageController {
	private final static Logger logger = LoggerFactory.getLogger(ImageController.class);
    
    @Autowired
    private ImageService imageService;
    
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
    @RequestMapping(value = {"/findCarousels", "/getCarousels"}, produces = { "application/json;charset=UTF-8" })
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
    
    
}
