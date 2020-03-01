package com.star.shop.admin.controller;

import com.star.shop.admin.entity.Banner;
import com.star.shop.admin.service.BannerService;
import com.star.shop.basic.annotation.Log;
import com.star.shop.basic.controller.BaseController;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


@RestController
@RequestMapping(value = "/shop/banner")
public class BannerController extends BaseController {
	
	private @Resource
	BannerService bannerServcice;
	
	/**
	 * 获取banner列表
	 * @return
	 */
	@RequestMapping(value = "/list" , method = {RequestMethod.GET ,RequestMethod.POST})
	public ResultVo listBanner(@RequestBody Map<String , Object> params) {
		return this.bannerServcice.listBanner(params);
		
	}
	
	@Log("banner新增")
	@RequestMapping(value = "/save" , method = {RequestMethod.POST})
	public ResultVo saveBanner(@RequestBody Banner banner) {
		return this.bannerServcice.saveBanner(banner);
	}
	
	@RequestMapping(value = "/info" ,  method = {RequestMethod.GET ,RequestMethod.POST})
	public ResultVo infoBanner(@RequestBody String id) {
		return this.bannerServcice.infoBanner(id);
	}
	
	@Log("banner编辑")
	@RequestMapping(value = "/modify" , method = {RequestMethod.POST})
	public ResultVo modifyBanner(@RequestBody Banner banner) {
		return this.bannerServcice.modifyBanner(banner);
	}
	
	@Log("banner删除")
	@RequestMapping(value = "/remove" , method = {RequestMethod.POST})
	public ResultVo removeBanner(@RequestBody String[] ids) {
		return this.bannerServcice.removeBanner(ids);
	}
}
