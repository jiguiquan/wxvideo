package com.wx.video.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wx.video.dao.UserMapper;
import com.wx.video.entity.User;
import com.wx.video.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserById(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * 
	 * @param open_id
	 * @return
	 */
	@Override
	public User selectByOpenId(String open_id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public int update(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 
	 * @param newUser
	 * @return
	 */
	@Override
	public int insert(User newUser) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
