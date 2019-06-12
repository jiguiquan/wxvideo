package com.wx.video.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wx.video.dao.VorderMapper;
import com.wx.video.entity.User;
import com.wx.video.entity.Vorder;
import com.wx.video.service.VorderService;
import com.wx.video.service.UserService;
@Service
public class VorderServiceImpl implements VorderService {
	@Autowired
	private VorderMapper vorderMapper;
	@Autowired
	private UserService userService;
	
	@Override
	public Vorder findById(Integer id) {
		return vorderMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Integer id) {
		vorderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(Vorder model) {
		vorderMapper.updateByPrimaryKeySelective(model);
	}

	@Override
	public int save(Vorder model) {
		return vorderMapper.insertSelective(model);
	}

	@Override
	@Transactional
	public void integralPay(Vorder order) {
		save(order);
		//修改当前余额
		User user = userService.getUserById(order.getUid());
		Double oldIntegral = user.getUintegral();
		user.setUintegral(oldIntegral-order.getOintegral());
		userService.update(user);
	}

	/**
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public List<Vorder> myOrder(Integer uid) {
		// TODO Auto-generated method stub
		return vorderMapper.myOrder(uid);
	}

	/**
	 * 
	 * @param parseInt
	 * @return
	 */
	@Override
	public List<Integer> myOrderVidList(Integer uid) {
		// TODO Auto-generated method stub
		return vorderMapper.myOrderVidList(uid);
	}

}
