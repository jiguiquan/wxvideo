package com.wx.video.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wx.video.dao.OrderMapper;
import com.wx.video.entity.Order;
import com.wx.video.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper orderMapper;
	
	@Override
	public Order findById(Integer id) {
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Integer id) {
		orderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Order model) {
		orderMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public void save(Order model) {
		orderMapper.insert(model);
	}

}
