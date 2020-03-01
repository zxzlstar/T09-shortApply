package com.star.shop.basic.config;

import com.star.shop.basic.annotation.support.RequestMemberHandlerMethodArgumentResolver;
import com.star.shop.basic.annotation.support.RequestMerchHandlerMethodArgumentResolver;
import com.star.shop.basic.interceptor.ApiInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.List;


/**
 * 
 * 
 * <p>Title:WebConfiguration</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年9月11日
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
	private @Resource
	ApiInterceptor apiInterceptor ;
	
	private @Resource
	RequestMemberHandlerMethodArgumentResolver requestMemberHandlerMethodArgumentResolver ;
	
	private @Resource
	RequestMerchHandlerMethodArgumentResolver requestMerchHandlerMethodArgumentResolver ;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/getsystemtime").excludePathPatterns("/api/uploadimgs") ;
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(requestMemberHandlerMethodArgumentResolver) ;
		argumentResolvers.add(requestMerchHandlerMethodArgumentResolver); 
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		//配置文件能跨域上传
		registry.addMapping("/api/uploadimgs").allowCredentials(true).allowedHeaders("*").allowedOrigins("*").allowedMethods("*");

	}
}
