package com.wx.video.web;

public interface PatternMatcher {

	/**
	 * 判断字符串是否满足pattern
	 * 
	 * @param pattern
	 * @param source
	 * @return
	 */
	boolean matches(String pattern, String source);
}
