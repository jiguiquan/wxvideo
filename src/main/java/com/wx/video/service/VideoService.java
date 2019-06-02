package com.wx.video.service;

import java.util.List;

import com.wx.video.entity.Video;

public interface VideoService {

	void save(Video model);

	Video findById(Integer vid);

	void delete(Integer vid);

	void update(Video model);

	List<Video> findAll();

}
