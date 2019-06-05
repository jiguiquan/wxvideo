package com.wx.video.dao;

import com.wx.video.entity.WatchRecord;

public interface WatchRecordMapper {
    int deleteByPrimaryKey(Integer wid);

    int insert(WatchRecord record);

    int insertSelective(WatchRecord record);

    WatchRecord selectByPrimaryKey(Integer wid);

    int updateByPrimaryKeySelective(WatchRecord record);

    int updateByPrimaryKey(WatchRecord record);
}