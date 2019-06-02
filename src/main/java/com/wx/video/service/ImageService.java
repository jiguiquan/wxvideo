package com.wx.video.service;

import java.util.List;

import com.wx.video.entity.Image;

public interface ImageService {

	int save(Image model);

	Image findById(Integer imgId);

	int delete(Integer imgId);

	int update(Image model);

	List<Image> findAll();

	List<Image> findCarousels();

}
