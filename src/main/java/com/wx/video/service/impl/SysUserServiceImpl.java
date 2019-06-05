/**   
 ** 功能描述：
 * @Package: com.wx.video.service.impl 
 * @author: jiguiquan   
 * @date: 2019年6月5日 上午9:06:50 
 */
package com.wx.video.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wx.video.dao.SysUserMapper;
import com.wx.video.entity.SysUser;
import com.wx.video.service.SysUserService;

/**
 * @author jiguiquan
 *
 */
@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Override
	public SysUser findSysUserByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return sysUserMapper.findSysUserByLoginName(loginName);
	}

}
