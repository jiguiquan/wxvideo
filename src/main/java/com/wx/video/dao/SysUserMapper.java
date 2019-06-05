package com.wx.video.dao;

import org.apache.ibatis.annotations.Mapper;

import com.wx.video.entity.SysUser;
@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

	SysUser findSysUserByLoginName(String loginName);
}