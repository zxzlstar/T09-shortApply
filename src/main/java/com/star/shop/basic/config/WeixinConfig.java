package com.star.shop.basic.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

/**
 * 
 * 
 * <p>Title:WeixinConfig</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年9月11日
 */
@Configuration
public class WeixinConfig {
	private static Logger logger = LoggerFactory.getLogger(WeixinConfig.class) ;
	
	@Value("${shop.wxmsg.appid}")
	private String appid ;
	
	@Value("${shop.wxmsg.secret}")
	private String secret ;
	
	@Bean
	public WxMpService getWxMpService() {
		WxMpService wxMpService = new WxMpServiceImpl();
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(appid);
        configStorage.setSecret(secret);
		wxMpService.setWxMpConfigStorage(configStorage);
		return wxMpService;
	}

}