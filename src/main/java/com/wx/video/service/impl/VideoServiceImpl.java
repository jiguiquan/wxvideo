package com.wx.video.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wx.video.dao.VideoMapper;
import com.wx.video.entity.Video;
import com.wx.video.service.VideoService;
@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
	private VideoMapper videoMapper;

	@Override
	public void save(Video model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Video findById(Integer vid) {
		// TODO Auto-generated method stub
		return videoMapper.selectByPrimaryKey(vid);
	}

	@Override
	public void delete(Integer vid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Video model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Video> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
