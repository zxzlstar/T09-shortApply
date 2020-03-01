package com.star.shop.basic.controller;

import com.star.shop.basic.service.JournalService;
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
 * <p>Title:JournalController</p>
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
@RequestMapping(value = "/basic/journal")
public class JournalController extends BaseController{
	private @Resource
	JournalService journalService ;
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/list" , method = {RequestMethod.GET ,RequestMethod.POST})
	public ResultVo listJournal(@RequestBody Map<String , Object> params){
		return this.journalService.listJournal(params);
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/remove" , method = {RequestMethod.POST})
	public ResultVo removeJournal(@RequestBody String[] ids){
		return this.journalService.removeJournal(ids);
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/clear" , method = {RequestMethod.POST})
	public ResultVo clearJournal(){
		return this.journalService.clearJournal();
	}
}
