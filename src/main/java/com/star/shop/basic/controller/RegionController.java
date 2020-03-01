package com.star.shop.basic.controller;

import com.star.shop.basic.annotation.Log;
import com.star.shop.basic.entity.Region;
import com.star.shop.basic.service.RegionService;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 
 * 
 * <p>Title:RegionController</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月23日
 */
@RestController
@RequestMapping(value = "/basic/region")
public class RegionController extends BaseController {
	private @Resource
	RegionService regionService ;
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/list" , method = {RequestMethod.GET , RequestMethod.POST})
	public ResultVo listRegion(@RequestBody Map<String,Object> params){
		return this.regionService.listRegion(params);
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/select" , method = {RequestMethod.GET , RequestMethod.POST})
	public ResultVo selectRegion(String code){
		return this.regionService.selectRegion(code);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/info" , method = {RequestMethod.GET , RequestMethod.POST})
	public ResultVo infoRegion(@RequestBody String id){
		return this.regionService.infoRegion(id);
	}
	
	/**
	 * 
	 * @param region
	 * @return
	 */
	@Log("区域新增")
	@RequestMapping(value = "/save" , method = {RequestMethod.GET , RequestMethod.POST})
	public ResultVo saveRegion(@RequestBody Region region){
		return this.regionService.saveRegion(region);
	}
	
	/**
	 * 
	 * @param region
	 * @return
	 */
	@Log("区域修改")
	@RequestMapping(value = "/modify" , method = {RequestMethod.GET , RequestMethod.POST})
	public ResultVo modifyRegion(@RequestBody Region region){
		return this.regionService.modifyRegion(region);
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@Log("区域删除")
	@RequestMapping(value = "/remove" , method = {RequestMethod.GET , RequestMethod.POST})
	public ResultVo removeRegion(@RequestBody String[] ids){
		return this.regionService.removeRegion(ids);
	}
	
}
