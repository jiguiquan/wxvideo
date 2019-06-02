package com.wx.video.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wx.video.dao.ImageMapper;
import com.wx.video.entity.Image;
import com.wx.video.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	private ImageMapper imageMapper;

	@Override
	public int save(Image model) {
		// TODO Auto-generated method stub
		return imageMapper.insert(model);
	}

	@Override
	public Image findById(Integer imgId) {
		// TODO Auto-generated method stub
		return imageMapper.selectByPrimaryKey(imgId);
	}

	@Override
	public int delete(Integer imgId) {
		// TODO Auto-generated method stub
		return imageMapper.deleteByPrimaryKey(imgId);
	}

	@Override
	public int update(Image model) {
		// TODO Auto-generated method stub
		return imageMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public List<Image> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Image> findCarousels() {
		// TODO Auto-generated method stub
		return imageMapper.findCarousels();
	}

	
}
