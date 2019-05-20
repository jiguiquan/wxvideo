package com.wx.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableTransactionManagement
@EnableRedisHttpSession
@ServletComponentScan
@SpringBootApplication
public class VideoApplication {
	public static void main(String[] args) {
		SpringApplication.run(VideoApplication.class, args);
	}
}
