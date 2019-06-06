package com.wx.video.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wx.video.dto.VideoDTO;
import com.wx.video.entity.Video;
import com.wx.video.model.VideoPageVo;
import com.wx.video.model.VideoVo;
@Mapper
public interface VideoMapper {
    int deleteByPrimaryKey(String vid);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(String vid);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
    
	List<VideoDTO> findAll(VideoVo videoVo);

	List<VideoDTO> findVideoPage(VideoPageVo pageable);
}