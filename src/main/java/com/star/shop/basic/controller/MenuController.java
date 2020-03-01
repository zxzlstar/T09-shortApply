package com.star.shop.basic.controller;

import com.star.shop.basic.annotation.Log;
import com.star.shop.basic.entity.Menu;
import com.star.shop.basic.service.MenuService;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 
 * 
 * <p>Title:MenuController</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月17日
 */
@RestController
@RequestMapping(value = "/basic/menu")
public class MenuController extends BaseController{
	private @Resource
	MenuService menuService ;
	
//	@RequestMapping(value = "/index" , method = {RequestMethod.GET})
//	public ModelAndView index(){
//		ModelAndView mav = new ModelAndView() ;
//		mav.setViewName("basic/menu/list");
//		return mav ;
//	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list" ,method = {RequestMethod.GET ,RequestMethod.POST})
	public ResultVo listMenu(){
		return this.menuService.listMenu() ;
	}
	
	/**
	 * 根据用户权限获取菜单
	 * @return
	 */
	@RequestMapping(value = "/tree" ,method = {RequestMethod.GET ,RequestMethod.POST})
	public ResultVo treeMenu(){
		return this.menuService.treeMenu() ;
	}
	
	/**
	 * 
	 * @param menu
	 * @return
	 */
	@Log("菜单新增")
	@RequestMapping(value = "/save" ,method = {RequestMethod.POST})
	public ResultVo saveMenu(@RequestBody Menu menu){
		return this.menuService.saveMenu(menu) ;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/info" ,method = {RequestMethod.POST})
	public ResultVo infoMenu(@RequestBody String id){
		return this.menuService.infoMenu(id) ;
	}
	
	/**
	 * 
	 * @param menu
	 * @return
	 */
	@Log("菜单修改")
	@RequestMapping(value = "/modify" ,method = {RequestMethod.POST})
	public ResultVo modifyMenu(@RequestBody Menu menu){
		return this.menuService.modifyMenu(menu) ;
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@Log("菜单删除")
	@RequestMapping(value = "/remove" ,method = {RequestMethod.POST})
	public ResultVo removeMenu(@RequestBody String[] ids){
		return this.menuService.removeMenu(ids) ;
	}
}
