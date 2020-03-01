package com.star.shop.admin.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * 
 * 微信模版消息
 * 
 * <p>Title:WxmsgService</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author
 *
 * @date
 */
@Service
public class WxmsgService {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WxmsgService.class) ;
	
	/*@Value("${shop.wxmsg.appid}")
	private String appid ;
	
	@Value("${shop.wxmsg.secret}")
	private String secret ;*/
	
	private @Resource WxMpService wxMpService;
	
	public void sendMsg(String templateId,String openid,List<WxMpTemplateData> wxMpTemplateDataList){
		
		/*WxMpService wxMpService = new WxMpServiceImpl();
		WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(appid);
        configStorage.setSecret(secret);
		wxMpService.setWxMpConfigStorage(configStorage);*/
		
	    String lang = "zh_CN"; //语言
	    WxMpUser user = null   ;
		try {
			user = wxMpService.getUserService().userInfo(openid,lang);
	    	WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder().toUser(user.getOpenId()).templateId(templateId).build();
//		 	templateMessage.setUrl(" ");
	    	for (WxMpTemplateData wxMpTemplateData : wxMpTemplateDataList) {
	    		templateMessage.addWxMpTemplateData(wxMpTemplateData);
			}
	 	    wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
		} catch (WxErrorException e) {
//			e.printStackTrace();
			System.out.println(openid+"未关注此公众号");
		}
	}
}
