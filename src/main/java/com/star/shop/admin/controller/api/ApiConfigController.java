package com.star.shop.admin.controller.api;

import com.star.shop.admin.entity.Banner;
import com.star.shop.admin.service.BannerService;
import com.star.shop.basic.component.FileComponent;
import com.star.shop.basic.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * <p>Title:ApiConfigController</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年1月25日
 */
@RestController
@RequestMapping(value = "/api")
public class ApiConfigController {
	private static final Logger logger = LoggerFactory.getLogger(ApiConfigController.class) ;
	
	private @Resource
	BannerService bannerService ;
	
	private @Resource
	FileComponent fileComponent;
	
	@RequestMapping(value = "/banner/list" , method = {RequestMethod.GET})
	public ResultVo bannerList(int position) {
		logger.info("获取banner列表[/api/banner/list]请求参数：{}",position);
		List<Banner> list = this.bannerService.findByPosition(position) ;
		List<Map<String, Object>> datas = new ArrayList<>() ;
		for(Banner banner : list) {
			Map<String, Object> data = new HashMap<>() ;
			data.put("id", banner.getId()) ;
			data.put("name", banner.getName()) ;
			data.put("rank", banner.getRank()) ;
			data.put("image", this.fileComponent.getImageUrl(banner.getImage())) ;
			data.put("link", banner.getLink()) ;
			data.put("remark", banner.getRemark()) ;
			
			datas.add(data) ;
		}
		
		ResultVo resultVo = new ResultVo() ;
		resultVo.setData(datas);
		resultVo.setTotal(datas.size());
		return resultVo ;
	}

}
