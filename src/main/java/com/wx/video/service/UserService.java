package com.wx.video.service;

import com.wx.video.entity.User;

public interface UserService {
	User getUserById(Integer id);

	/**
	 * @param open_id
	 * @return
	 */
	User selectByOpenId(String open_id);

	/**
	 * @param user
	 */
	int update(User user);

	/**
	 * @param newUser
	 * @return
	 */
	int insert(User newUser);
}
