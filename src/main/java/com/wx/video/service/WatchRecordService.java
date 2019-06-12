/**   
 ** 功能描述：
 * @Package: com.wx.video.service 
 * @author: jiguiquan   
 * @date: 2019年6月12日 上午10:32:06 
 */
package com.wx.video.service;

import java.util.List;

import com.wx.video.dto.WatchRecordDTO;
import com.wx.video.entity.WatchRecord;
import com.wx.video.model.WatchRecordVo;

/**
 * @author jiguiquan
 *
 */
public interface WatchRecordService {

	void save(WatchRecord record);

	List<WatchRecordDTO> myWatchRecord(WatchRecordVo recordVo);

}
