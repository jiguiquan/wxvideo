package com.wx.video.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wx.video.entity.User;
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByOpenId(String openid);
}