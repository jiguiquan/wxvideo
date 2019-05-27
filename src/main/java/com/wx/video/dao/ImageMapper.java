package com.wx.video.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wx.video.entity.Image;

@Mapper
public interface ImageMapper {
    int deleteByPrimaryKey(Integer imgId);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(Integer imgId);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);
}