package com.wx.video.service;

import java.util.List;

import com.wx.video.dto.VideoDTO;
import com.wx.video.entity.Video;
import com.wx.video.model.VideoPageVo;
import com.wx.video.model.VideoVo;

public interface VideoService {

	int save(Video record);

	Video findById(String vid);

	void delete(String vid);

	void update(Video record);

	List<VideoDTO> findAll(VideoVo videoVo);
	
	List<VideoDTO> findVideoPage(VideoPageVo pageable);

}
