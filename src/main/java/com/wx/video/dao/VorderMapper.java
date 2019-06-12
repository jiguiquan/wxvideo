package com.wx.video.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wx.video.entity.Vorder;
@Mapper
public interface VorderMapper {
    int deleteByPrimaryKey(Integer oid);

    int insert(Vorder record);

    int insertSelective(Vorder record);

    Vorder selectByPrimaryKey(Integer oid);

    int updateByPrimaryKeySelective(Vorder record);

    int updateByPrimaryKey(Vorder record);

	List<Vorder> myOrder(Integer uid);

	List<Integer> myOrderVidList(Integer uid);
}