package com.star.shop.admin.controller.api;

import com.star.shop.basic.vo.ResultVo;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 
 * 
 * <p>Title:ApiWeixinController</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author 
 *
 * @date 
 */
@RestController
@RequestMapping(value = "/api")
public class ApiWeixinController{
	private final static Logger logger = LoggerFactory.getLogger(ApiWeixinController.class) ;
	
	private @Resource WxMpService wxMpService;

	/**
	 * 获取access_token
	 * @return
	 * @throws WxErrorException 
	 */
	@RequestMapping(value = "/weixin/getaccesstoken" , method = {RequestMethod.GET})
	public ResultVo getAccessToken() {
		logger.info("获取access_token-[/weixin/getaccesstoken]请求参数：");
		
		try {
			String accessToken = wxMpService.getAccessToken();
			ResultVo resultVo = new ResultVo();
			resultVo.addData("accessToken", accessToken);
			return resultVo;
		} catch (WxErrorException e) {
			e.printStackTrace();
			return ResultVo.e(419, "获取access_token失败");
		}
	}
	
	/**
	 * 获取JsapiTicket
	 * @return
	 */
	@RequestMapping(value = "/weixin/getjsapiticket" , method = {RequestMethod.GET})
	public ResultVo getJsapiTicket() {
		logger.info("获取JsapiTicket-[/weixin/getjsapiticket]请求参数：");
		
		try {
			String jsapiTicket = wxMpService.getJsapiTicket();
			ResultVo resultVo = new ResultVo();
			resultVo.addData("jsapiTicket", jsapiTicket);
			return resultVo;
		} catch (WxErrorException e) {
			e.printStackTrace();
			return ResultVo.e(420, "获取JsapiTicket失败");
		}
	}
	
	/**
	 * 签名
	 * @param url
	 * @return
	 */
	@RequestMapping(value = "/weixin/getsignature" , method = {RequestMethod.GET})
	public ResultVo getSignature(@RequestParam String url) {
		
		System.out.println(url);
		logger.info("签名-[/weixin/getsignature]请求参数：",url);
		
		try {
			WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(url);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("appid", jsapiSignature.getAppId());
			map.put("nonceStr", jsapiSignature.getNonceStr());
			map.put("timestamp", jsapiSignature.getTimestamp());
			map.put("url", jsapiSignature.getUrl());
			map.put("signature", jsapiSignature.getSignature());
			return ResultVo.s(map);
		} catch (WxErrorException e) {
			e.printStackTrace();
			return ResultVo.e(421, "签名失败");
		}
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
	
}
