package com.wx.video.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.wx.video.dto.WatchRecordDTO;
import com.wx.video.entity.WatchRecord;
import com.wx.video.model.WatchRecordVo;
@Mapper
public interface WatchRecordMapper {
    int deleteByPrimaryKey(Integer wid);

    int insert(WatchRecord record);

    int insertSelective(WatchRecord record);

    WatchRecord selectByPrimaryKey(Integer wid);

    int updateByPrimaryKeySelective(WatchRecord record);

    int updateByPrimaryKey(WatchRecord record);

	List<WatchRecordDTO> myWatchRecord(WatchRecordVo recordVo);
}