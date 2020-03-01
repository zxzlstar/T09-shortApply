package com.star.shop.basic.controller;

import com.star.shop.admin.service.MemberService;
import com.star.shop.basic.shiro.ShiroUtils;
import com.star.shop.basic.utils.MD5Utils;
import com.star.shop.basic.vo.ResultVo;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * <p>Title:IndexController</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月17日
 */
@Controller
public class IndexController extends BaseController{

	
	private @Resource
	MemberService memberService;
	
	@RequestMapping(value = {"","/","/index"} , method = {RequestMethod.GET})
	public String index(ModelMap model){
		model.addAttribute("time", System.currentTimeMillis()) ;
		return "index" ;
	}

	@RequestMapping(value = "/login" , method = {RequestMethod.GET})
	public String login(ModelMap model){
		model.addAttribute("time", System.currentTimeMillis()) ;
		return "login" ;
	} 
	
	@RequestMapping(value = "/login" , method = {RequestMethod.POST})
	public @ResponseBody
	ResultVo login(String username , String password){
		if(StringUtils.isEmpty(username)){
			return ResultVo.e(400 , "用户名不能为空") ;
		}
		
		if(StringUtils.isEmpty(password)){
			return ResultVo.e(400 , "密码不能为空") ;
		}
		
		try{
			Subject subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Utils.encode(password));
			subject.login(token);
		}catch (UnknownAccountException e) {
			return ResultVo.e(400,e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return ResultVo.e(400,e.getMessage());
		}catch (LockedAccountException e) {
			return ResultVo.e(400,e.getMessage());
		}catch (AuthenticationException e) {
			return ResultVo.e(400,"账户验证失败");
		}
		List<Map<String,Object>> list = memberService.getMemberCountSex();
		return ResultVo.s() ;
	}
	
	@RequestMapping(value = "/logout" , method = {RequestMethod.POST})
	public ModelAndView logout(){
		ShiroUtils.logout();
		ModelAndView mav = new ModelAndView() ;
		mav.setViewName("login");
		return mav ;
	}
	
	@RequestMapping(value = "/basic/test" ,produces = {"text/html;charset=UTF-8"})
	public @ResponseBody String test(){
		return "test";
	}
	
}
