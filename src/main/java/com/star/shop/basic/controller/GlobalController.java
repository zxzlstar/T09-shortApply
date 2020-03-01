package com.star.shop.basic.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * 
 * <p>
 * Title:GlobalController
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author x.zhang
 *
 * @date 2018年8月17日
 */
@Controller
public class GlobalController {
	/**
	 * 
	 * @param module
	 * @param function
	 * @param url
	 * @return
	 */
	@RequestMapping("/v/{module}/{function}/{url}.html")
	public String page(@PathVariable("module") String module, 
			@PathVariable("function") String function,
			@PathVariable("url") String url,
			@RequestParam Map<String,Object> map,
			ModelMap model) {
		model.addAttribute("time", System.currentTimeMillis()) ;
		model.addAllAttributes(map) ;
		return module + "/" + function + "/" + url + ".html";
	}

	/**
	 * 
	 * @param module
	 * @param url
	 * @return
	 */
	@RequestMapping("/v/{module}/{url}.html")
	public String page(@PathVariable("module") String module, @PathVariable("url") String url,@RequestParam Map<String,Object> map,ModelMap model) {
		model.addAttribute("time", System.currentTimeMillis()) ;
		model.addAllAttributes(map) ;
		return module + "/" + url + ".html";
	}
}
