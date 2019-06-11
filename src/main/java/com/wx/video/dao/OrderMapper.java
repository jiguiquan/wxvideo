package com.wx.video.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wx.video.entity.Order;
@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Integer oid);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer oid);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}