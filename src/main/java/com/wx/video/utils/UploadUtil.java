package com.wx.video.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadUtil {
	@Value("${upload.basePath}")
	private String basePath;
	@Value("${upload.urlPrefix}")
	private String urlPrefix;
	
	/**
	 * 将单文件上传到指定位置
	 * @param upfile
	 * @return 返回文件路径(带文件名)
	 */
	@RequestMapping("/uploadFile")
	public String uploadFile(MultipartFile upfile) {
		//检验路径是否存在和是否为文件夹
		System.out.println(basePath);
		
		File uploadPath = new File(basePath);
		if (!uploadPath.exists() || !uploadPath.isDirectory()) {
			uploadPath.mkdir();
		}
		
		String uuid = UUID.randomUUID().toString();
		String originName = upfile.getOriginalFilename();
		
		String destPath = basePath + "/" + uuid + originName;
		String urlPath = urlPrefix + uuid + originName;
		
		File destFile = new File(destPath);
		
		try {
			upfile.transferTo(destFile);
			return urlPath;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
