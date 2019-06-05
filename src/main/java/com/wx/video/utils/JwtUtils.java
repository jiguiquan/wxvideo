package com.wx.video.utils;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

@Component
public class JwtUtils {
	@Value("${auth.jwtKey}")
	private String jwtKey;
	
	/**
	 ** 根据UserClaims创建JwtToken 
	 * @param userClaims
	 * @return
	 */
	public String createToken(UserClaims userClaims) {
		JwtTokenProvider tokenProvider = new JwtTokenProvider(jwtKey);
		return tokenProvider.createToken(userClaims);
	}
	
	/**
	 ** 根据token解析出Claim 
	 * @param token
	 * @return
	 */
	public Claims parseToken(String token) {
		JwtTokenProvider tokenProvider = new JwtTokenProvider(jwtKey);
		return tokenProvider.parseToken(token);
	}
	
	/**
	 **  根据request请求直接解析出Claim 
	 * @param request
	 * @return
	 */
	public Claims getUserClaim(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
      	JwtTokenProvider tokenProvider = new JwtTokenProvider(jwtKey);
		return tokenProvider.parseToken(token);
	}
}
