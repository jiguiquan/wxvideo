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
	public Video findById(Integer id) {
		// TODO Auto-generated method stub
		return videoMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Integer id) {
		videoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Video record) {
		// TODO Auto-generated method stub
		videoMapper.updateByPrimaryKeySelective(record);
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

	/**
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public List<VideoDTO> myBought(Integer uid) {
		// TODO Auto-generated method stub
		return videoMapper.myBought(uid);
	}

	/**
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public List<VideoDTO> myWatchRecord(Integer uid) {
		// TODO Auto-generated method stub
		return videoMapper.myWatchRecord(uid);
	}

	/**
	 * 
	 * @param vid
	 * @return
	 */
	@Override
	public VideoDTO detail(Integer vid, Integer uid) {
		// TODO Auto-generated method stub
		return videoMapper.detail(vid, uid);
	}

}
