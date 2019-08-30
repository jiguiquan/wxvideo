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

	@Override
	public User selectByOpenId(String openid) {
		// TODO Auto-generated method stub
		return userMapper.selectByOpenId(openid);
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
		return userMapper.insertSelective(newUser);
	}

	@Override
	public User findUserByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addIntegral(String open_id, Double integral) {
		User user = selectByOpenId(open_id);
		user.setUintegral(user.getUintegral() + integral);
		update(user);
	}
	
	
}
