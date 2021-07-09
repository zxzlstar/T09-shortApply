package com.star.shop.basic.controller;

import com.star.shop.basic.annotation.Log;
import com.star.shop.basic.entity.User;
import com.star.shop.basic.service.UserService;
import com.star.shop.basic.shiro.ShiroUtils;
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
 * <p>Title:UserController</p>
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
@RequestMapping(value = "/basic/user")
public class UserController extends BaseController {
	private @Resource
	UserService userService ;
	
	/**
	 * 
	 * 获取当前登录用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/current")
	public ResultVo getCurrentUser(){
		return ResultVo.s(ShiroUtils.getUser()) ;
	}
	
//	@RequestMapping(value = "/perms")
//	public ResultVo perms(){
//		return ResultVo.s(this.userService.listUserPerms(ShiroUtils.getUserId())) ;
//	}
//
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/info")
	public ResultVo infoUser(@RequestBody String id){
		return this.userService.infoUser(id) ;
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ResultVo listUser(@RequestBody Map<String , Object> params){
		return this.userService.listUser(params) ;
	}
	
	/**
	 * 
	 * 修改密码
	 * 
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@Log("用户密码修改")
	@RequestMapping(value = "/modifypassword" , method = {RequestMethod.POST})
	public ResultVo modifypassword(String oldPassword , String newPassword){
		return this.userService.modifypassword(oldPassword , newPassword);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@Log("用户新增")
	@RequestMapping(value = "/save" , method = {RequestMethod.POST})
	public ResultVo saveUser(@RequestBody User user){
		return this.userService.saveUser(user);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@Log("用户修改")
	@RequestMapping(value = "/modify" , method = {RequestMethod.POST})
	public ResultVo modifyUser(@RequestBody User user){
		return this.userService.modifyUser(user);
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@Log("用户删除")
	@RequestMapping(value = "/remove" , method = {RequestMethod.POST})
	public ResultVo removeUser(@RequestBody String[] ids){
		return this.userService.removeUser(ids) ;
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@Log("用户禁用")
	@RequestMapping(value = "/disable" , method = {RequestMethod.POST})
	public ResultVo disableUser(@RequestBody String[] ids){
		return this.userService.disableUser(ids) ;
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@Log("用户启用")
	@RequestMapping(value = "/enable" , method = {RequestMethod.POST})
	public ResultVo enableUser(@RequestBody String[] ids){
		return this.userService.enableUser(ids) ;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	@Log("用户重置密码")
	@RequestMapping(value = "/reset" , method = {RequestMethod.POST})
	public ResultVo resetUser(@RequestBody User user){
		return this.userService.resetUser(user) ;
	}
}
