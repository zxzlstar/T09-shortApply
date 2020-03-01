package com.star.shop.basic.annotation.support;

import com.star.shop.admin.entity.Merch;
import com.star.shop.basic.annotation.RequestMerch;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * 
 * 
 * <p>Title:RequestMerchHandlerMethodArgumentResolver</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年9月11日
 */
@Component
public class RequestMerchHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return (parameter.getParameterType().isAssignableFrom(Merch.class) || parameter.getParameterType().isAssignableFrom(String.class))&&parameter.hasParameterAnnotation(RequestMerch.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		
		return null;
	}
}
