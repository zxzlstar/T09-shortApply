package com.star.shop.basic.aspect;

import com.alibaba.fastjson.JSON;
import com.star.shop.basic.annotation.Log;
import com.star.shop.basic.entity.Journal;
import com.star.shop.basic.entity.User;
import com.star.shop.basic.service.JournalService;
import com.star.shop.basic.shiro.ShiroUtils;
import com.star.shop.basic.utils.HttpContextUtils;
import com.star.shop.basic.utils.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 
 * 
 * <p>Title:LogAspect</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年9月11日
 */
@Aspect
@Component
public class LogAspect {
	private @Resource
	JournalService journalService ;
	
	@Pointcut("@annotation(com.star.shop.basic.annotation.Log)")
	public void logPointCut() { 
		
	}
	
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		//保存日志
		this.saveJournal(point, time);
		return result;
	}
	
	private void saveJournal(ProceedingJoinPoint joinPoint, long time){
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Journal journal = new Journal();
		Log log = method.getAnnotation(Log.class);
		if(log != null){
			//注解上的描述
			journal.setOperation(log.value());
		}
		//请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		journal.setMethod(className + "." + methodName + "()");
		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = JSON.toJSONString(args[0]) ;
			journal.setParams(params);
		}catch (Exception e){

		}
		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		journal.setIp(IPUtils.getIpAddr(request));
		//用户名
		User currUser = ShiroUtils.getUser() ;
		if(currUser == null) {
			if(StringUtils.hasText(journal.getParams())) {
				journal.setUserId("");
				journal.setUsername("");
			} else {
				journal.setUserId("");
				journal.setUsername("获取用户信息为空");
			}
		} else {
			journal.setUserId(ShiroUtils.getUserId());
			journal.setUsername(ShiroUtils.getUser().getUsername());
		}
		journal.setTime(time);
		//保存系统日志
		this.journalService.save(journal) ;
	}

}
