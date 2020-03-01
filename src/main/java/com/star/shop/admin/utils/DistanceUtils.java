package com.star.shop.admin.utils;

import org.springframework.util.StringUtils;

public class DistanceUtils {

    private static double EARTH_RADIUS = 6378.137;    
    
    private static double rad(double d) {    
        return d * Math.PI / 180.0;    
    }    
	    
	    
	public static double distance(String lat1, String lng1, String lat2, String lng2) {
		if(!StringUtils.hasText(lat2) && !StringUtils.hasText(lng2)) {
			return 0.0;
		}
		double radLat1 = rad(Double.parseDouble(lat1));    
        double radLat2 = rad(Double.parseDouble(lat2));    
        double a = radLat1 - radLat2;    
        double b = rad(Double.parseDouble(lng1)) - rad(Double.parseDouble(lng2));    
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)    
               + Math.cos(radLat1) * Math.cos(radLat2)    
               * Math.pow(Math.sin(b / 2), 2)));    
        s = s * EARTH_RADIUS;    
        s = Math.round(s * 10000d) / 10000d;    
        s = s*1000;    
        return s;    
			
	}
	
}
