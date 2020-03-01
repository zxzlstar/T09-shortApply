package com.star.shop.basic.annotation.support;

import com.star.shop.admin.entity.Member;
import com.star.shop.admin.service.MemberService;
import com.star.shop.basic.annotation.RequestMember;
import com.star.shop.basic.utils.Constants;
import com.star.shop.basic.utils.ThreadLocalUtil;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;


/**
 * 
 * 
 * <p>Title:RequestMemberHandlerMethodArgumentResolver</p>
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
public class RequestMemberHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver{
	private @Resource
	MemberService memberService ;
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return (parameter.getParameterType().isAssignableFrom(Member.class) || parameter.getParameterType().isAssignableFrom(String.class))&&parameter.hasParameterAnnotation(RequestMember.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		String mid = (String) ThreadLocalUtil.get(Constants.MEMBER_ID);
		
		if(parameter.getParameterType().isAssignableFrom(String.class)) {
			return mid ;
		}else {
			if(StringUtils.hasText(mid)) {
				return this.memberService.findById(mid) ;
			}
		}
		
		return null;
	}

}
