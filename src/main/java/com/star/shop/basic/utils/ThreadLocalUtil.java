package com.star.shop.basic.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 *
 * @date 2018年3月23日
 *
 * @author x.zhang
 *
 */
public class ThreadLocalUtil {
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    private static String defaultKey = "defaultKey";
    
    public static void set(String key , Object val) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, val);
    }
    
    public static void set( Object val) {
    	ThreadLocalUtil.set(defaultKey, val);
    }


    public static Object get(String key) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            return null;
        }
        Object o = map.get(key);
        return o  ;
    }
    public static Object get(){
    	return ThreadLocalUtil.get(defaultKey) ;
    }
    
    
    public static Map<String, Object> getAll() {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();;
        }
        return map;
    }
    
    public static void remove() {
    	threadLocal.remove(); 
    }
}