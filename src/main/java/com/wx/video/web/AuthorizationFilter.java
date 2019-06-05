package com.wx.video.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wx.video.common.JsonResult;

public class AuthorizationFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);

	public static final String PARAM_NAME_EXCLUSIONS = "exclusions";
	
	public static final String PARAM_NAME_AUTHENABLE = "enabled";
	
	public static final String PARAM_NAME_CORS_ORIGIN = "corsOrigin";
	
	public static final String USER_SESSION_KEY = "LOGIN_USER";
	
	private String contextPath;
	
	private String authEnable;
	
	private String corsOrigin;
	
	protected PatternMatcher pathMatcher = new ServletPathMatcher();
	
	private Set<String> excludesPattern;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("初始化AuthorizationFilter");
		
		this.contextPath = filterConfig.getServletContext().getContextPath();
		
		String exclusions = filterConfig.getInitParameter(PARAM_NAME_EXCLUSIONS);
        if (exclusions != null && exclusions.trim().length() != 0) {
            excludesPattern = new HashSet<String>(Arrays.asList(exclusions.split("\\s*,\\s*")));
        }
        System.out.println(excludesPattern.toString());
        
        authEnable = filterConfig.getInitParameter(PARAM_NAME_AUTHENABLE);
        corsOrigin = filterConfig.getInitParameter(PARAM_NAME_CORS_ORIGIN);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		logger.info("cors-origin: {}", corsOrigin);
		httpResponse.setHeader("Access-Control-Allow-Origin", corsOrigin);
		httpResponse.setHeader("Access-Control-Allow-Methods", "*");
		httpResponse.setHeader("Access-Control-Allow-Headers", "DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization,SessionToken,token");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		
		String requestURI = httpRequest.getRequestURI();
		logger.info("filtered url: {}", requestURI);
		
		if(!"true".equalsIgnoreCase(authEnable)) {
			logger.info("权限认证未开启：{}", requestURI);
			chain.doFilter(request, response);
            return;
		}
		
		if (isExclusion(requestURI)) {
			logger.info("URL不需授权：{}", requestURI);
            chain.doFilter(request, response);
            return;
        }
		
		String method = httpRequest.getMethod();
		if("OPTIONS".equalsIgnoreCase(method)) {
			logger.info("过滤options请求:{}", requestURI);
			ObjectMapper objectMapper = new ObjectMapper();
			JsonResult success = JsonResult.successs();
			try {
				httpResponse.setContentType("application/json;charset=utf-8");
				httpResponse.getWriter().print(objectMapper.writeValueAsString(success));
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			return;
		}
		
		Object loginUser = httpRequest.getSession().getAttribute(USER_SESSION_KEY);
		if(loginUser == null) {
			logger.info("用户未登录");
        	ObjectMapper objectMapper = new ObjectMapper();
			httpResponse.setContentType("application/json;charset=utf-8");
        	httpResponse.getWriter().print(objectMapper.writeValueAsString(JsonResult.failure("用户未登录")));
		} else {
			chain.doFilter(request, response);
		}
	}
	
	public boolean isExclusion(String requestURI) {
        if (excludesPattern == null || requestURI == null) {
            return false;
        }

        if (contextPath != null && requestURI.startsWith(contextPath)) {
            requestURI = requestURI.substring(contextPath.length());
            if (!requestURI.startsWith("/")) {
                requestURI = "/" + requestURI;
            }
        }

        for (String pattern : excludesPattern) {
            if (pathMatcher.matches(pattern, requestURI)) {
                return true;
            }
        }

        return false;
    }

	@Override
	public void destroy() {
		logger.debug("销毁AuthorizationFilter");
	}
}
