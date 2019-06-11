package com.wx.video.service;

import com.wx.video.entity.Order;

public interface OrderService {

	Order findById(Integer id);

	void delete(Integer id);

	void update(Order model);

	void save(Order model);

}
