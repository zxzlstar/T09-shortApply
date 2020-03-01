package com.star.shop.basic.controller;

import com.star.shop.basic.annotation.Log;
import com.star.shop.basic.entity.Role;
import com.star.shop.basic.service.RoleService;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 
 * 角色管理
 * 
 * <p>Title:RoleController</p>
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
@RequestMapping(value = "/basic/role")
public class RoleController extends BaseController{
	private @Resource
	RoleService roleService ;
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/list" , method = {RequestMethod.GET , RequestMethod.POST})
	public ResultVo listRole(@RequestBody Map<String, Object> params){
		return this.roleService.listRole(params) ;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/info" , method = {RequestMethod.GET , RequestMethod.POST})
	public ResultVo infoRole(@RequestBody String id){
		return this.roleService.infoRole(id) ;
	}
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	@Log("角色新增")
	@RequestMapping(value = "/save" ,method = {RequestMethod.POST})
	public ResultVo saveRole(@RequestBody Role role){
		return this.roleService.saveRole(role) ;
	}
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	@Log("角色修改")
	@RequestMapping(value = "/modify" ,method = {RequestMethod.POST})
	public ResultVo modifyRole(@RequestBody Role role){
		return this.roleService.modifyRole(role) ;
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@Log("角色删除")
	@RequestMapping(value = "/remove" ,method = {RequestMethod.POST})
	public ResultVo removeRole(@RequestBody String[] ids){
		return this.roleService.removeRole(ids) ;
	}
	
	/**
	 * 
	 * @param role
	 * @return
	 */
	@Log("角色授权")
	@RequestMapping(value = "/authorize" ,method = {RequestMethod.POST})
	public ResultVo authorize(@RequestBody Role role){
		return this.roleService.authorize(role) ;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/select" ,method = {RequestMethod.GET,RequestMethod.POST})
	public ResultVo selectRole(){
		return this.roleService.selectRole() ;
	}
	
}
