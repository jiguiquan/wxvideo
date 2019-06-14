package com.wx.video.service;

import java.util.List;

import com.wx.video.dto.VideoDTO;
import com.wx.video.entity.Video;
import com.wx.video.model.VideoPageVo;
import com.wx.video.model.VideoVo;

public interface VideoService {

	int save(Video record);

	Video findById(Integer id);

	void delete(Integer id);

	void update(Video record);

	List<VideoDTO> findAll(VideoVo videoVo);
	
	List<VideoDTO> findVideoPage(VideoPageVo pageable);
	
	List<VideoDTO> myBought(Integer uid);

	List<VideoDTO> myWatchRecord(Integer uid);

	VideoDTO detail(Integer vid, Integer uid);

}
