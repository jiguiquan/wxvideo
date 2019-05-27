package com.wx.video.service;

import java.util.List;

import com.wx.video.entity.Image;

public interface ImageService {

	Image findById(Integer id);

	int delete(Integer id);

	int update(Image model);

	List<Image> findAll();

}
