/**   
 ** 功能描述：
 * @Package: com.wx.video.service 
 * @author: jiguiquan   
 * @date: 2019年6月5日 上午9:06:12 
 */
package com.wx.video.service;

import com.wx.video.entity.SysUser;

/**
 * @author jiguiquan
 *
 */
public interface SysUserService {

	/**
	 * @param loginName
	 * @return
	 */
	SysUser findSysUserByLoginName(String loginName);

}
