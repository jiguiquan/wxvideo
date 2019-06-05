package com.wx.video.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig {

	@Value("${auth.enable}")
	private String enable;
	
	@Value("${auth.exclusions}")
	private String exclusions;
	
	@Value("${auth.cors-origin}")
	private String corsOrigin;
	
	@Bean
	public FilterRegistrationBean<AuthorizationFilter> druidFilter() {
	    FilterRegistrationBean<AuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<AuthorizationFilter>();
	    filterRegistrationBean.setFilter(new AuthorizationFilter());
	    Map<String, String> initParams = new HashMap<String, String>();
	    //设置忽略请求
	    initParams.put("exclusions", exclusions);
	    initParams.put("enabled", enable);
	    initParams.put("corsOrigin", corsOrigin);
	    filterRegistrationBean.setInitParameters(initParams);
	    filterRegistrationBean.addUrlPatterns("/admin/*");
	    return filterRegistrationBean;
	}
	
}
