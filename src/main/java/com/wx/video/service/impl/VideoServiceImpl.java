package com.wx.video.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wx.video.dao.VideoMapper;
import com.wx.video.dto.VideoDTO;
import com.wx.video.entity.Video;
import com.wx.video.model.VideoPageVo;
import com.wx.video.model.VideoVo;
import com.wx.video.service.VideoService;
@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
	private VideoMapper videoMapper;

	@Override
	public int save(Video record) {
		return videoMapper.insertSelective(record);
	}

	@Override
	public Video findById(String vid) {
		// TODO Auto-generated method stub
		return videoMapper.selectByPrimaryKey(vid);
	}

	@Override
	public void delete(String vid) {
		videoMapper.deleteByPrimaryKey(vid);
	}

	@Override
	public void update(Video record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<VideoDTO> findAll(VideoVo videoVo) {
		// TODO Auto-generated method stub
		return videoMapper.findAll(videoVo);
	}

	@Override
	public List<VideoDTO> findVideoPage(VideoPageVo pageable) {
		// TODO Auto-generated method stub
		return videoMapper.findVideoPage(pageable);
	}

}
