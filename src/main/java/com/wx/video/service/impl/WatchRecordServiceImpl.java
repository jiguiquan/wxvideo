/**   
 ** 功能描述：
 * @Package: com.wx.video.service.impl 
 * @author: jiguiquan   
 * @date: 2019年6月12日 上午10:32:43 
 */
package com.wx.video.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wx.video.dao.WatchRecordMapper;
import com.wx.video.dto.WatchRecordDTO;
import com.wx.video.entity.WatchRecord;
import com.wx.video.model.WatchRecordVo;
import com.wx.video.service.WatchRecordService;

/**
 * @author jiguiquan
 *
 */
@Service
public class WatchRecordServiceImpl implements WatchRecordService {
	@Autowired
	private WatchRecordMapper watchRecordMapper;
	

	/**
	 * 
	 * @param record
	 */
	@Override
	public void save(WatchRecord record) {
		// TODO Auto-generated method stub
		watchRecordMapper.insertSelective(record);
	}


	/**
	 * 
	 * @param recordVo
	 * @return
	 */
	@Override
	public List<WatchRecordDTO> myWatchRecord(WatchRecordVo recordVo) {
		// TODO Auto-generated method stub
		return watchRecordMapper.myWatchRecord(recordVo);
	}

}
