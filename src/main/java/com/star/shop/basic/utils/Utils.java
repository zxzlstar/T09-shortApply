package com.star.shop.basic.utils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 
 * 
 * <p>Title:Utils</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年9月14日
 */
public final class Utils {
	public static int getPageSize(Map<String , Object> params){
		if(params.get("pageSize") != null){
			return Integer.parseInt(params.get("pageSize").toString()) ; 
		}
		return 1 ;
	}
	
	public static int getPageNumber(Map<String , Object> params){
		if(params.get("pageNumber") != null){
			return Integer.parseInt(params.get("pageNumber").toString()) ;
		}
		return 10 ;
	}
	
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String getRandomNumber(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			if(i == 0 ){
				while(number == 0) {
					number = random.nextInt(base.length());
				}
			}
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static Field[] getAllDeclaredFields(Class<?> clazz){
		final List<Field> list = new LinkedList<Field>();
		Class<?> targetClass = clazz;
		do {
			Field[] fields = targetClass.getDeclaredFields();
			for (Field field : fields) {
				if(field.getName().equals("serialVersionUID")){
					continue ;
				}
				list.add(field) ;
			}
			targetClass = targetClass.getSuperclass();
		}
		while (targetClass != null && targetClass != Object.class);
		return list.toArray(new Field[list.size()]) ;
	}
}
