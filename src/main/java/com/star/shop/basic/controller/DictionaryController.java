package com.star.shop.basic.controller;

import com.star.shop.basic.annotation.Log;
import com.star.shop.basic.entity.Dictionary;
import com.star.shop.basic.service.DictionaryService;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 
 * 
 * <p>Title:DictionaryController</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月22日
 */
@RestController
@RequestMapping(value = "/basic/dict")
public class DictionaryController extends BaseController {
	
	private @Resource
	DictionaryService dictionaryService ;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list" , method = {RequestMethod.GET ,RequestMethod.POST})
	public ResultVo listDict(){
		return this.dictionaryService.listDict();
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/select" , method = {RequestMethod.GET ,RequestMethod.POST})
	public ResultVo selectDict(){
		return this.dictionaryService.selectDict();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/info" , method = {RequestMethod.GET ,RequestMethod.POST})
	public ResultVo infoDict(@RequestBody String id){
		return this.dictionaryService.infoDict(id);
	}
	
	/**
	 * 
	 * @param dict
	 * @return
	 */
	@Log(value = "数据字典新增")
	@RequestMapping(value = "/save" , method = {RequestMethod.POST})
	public ResultVo saveDict(@RequestBody Dictionary dict){
		return this.dictionaryService.saveDict(dict);
	}
	
	/**
	 * 
	 * @param dict
	 * @return
	 */
	@Log(value = "数据字典修改")
	@RequestMapping(value = "/modify" , method = {RequestMethod.POST})
	public ResultVo modifyDict(@RequestBody Dictionary dict){
		return this.dictionaryService.modifyDict(dict);
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@Log(value = "数据字典删除")
	@RequestMapping(value = "/remove" , method = {RequestMethod.POST})
	public ResultVo removeDict(@RequestBody String[] ids){
		return this.dictionaryService.removeDict(ids);
	}

}
