package com.wx.video.service;

import java.util.List;

import com.wx.video.entity.Video;
import com.wx.video.entity.Vorder;

public interface VorderService {

	Vorder findById(Integer id);

	void delete(Integer id);

	void update(Vorder model);

	int save(Vorder model);

	void integralPay(Vorder order);

	List<Vorder> myOrder(Integer id);

	List<Integer> myOrderVidList(Integer uid);

}
