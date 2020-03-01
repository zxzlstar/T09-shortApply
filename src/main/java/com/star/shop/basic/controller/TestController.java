package com.star.shop.basic.controller;

import com.star.shop.admin.entity.Member;
import com.star.shop.admin.service.MemberService;
import com.star.shop.basic.utils.SpringContextHolder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 
 * 
 * <p>Title:TestController</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年2月9日
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {
	private @Resource
	MemberService memberService  ;
	@RequestMapping(value = "/demo1")
	public String demo1(@RequestParam String name) {
		TaskScheduler scheduler = (TaskScheduler) SpringContextHolder.getBean(TaskScheduler.class) ;
		System.out.println(scheduler);
		System.out.println(name);
		Member member = new Member() ;
		member.setNickname(name);
		//this.memberService.save(member) ;
		return "success" ;
	}
	
}
