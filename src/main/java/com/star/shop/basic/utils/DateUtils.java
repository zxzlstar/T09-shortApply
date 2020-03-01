package com.star.shop.basic.utils;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 
 * <p>Title:DateUtils</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月17日
 */
public class DateUtils {
	
	/** 时间格式(yyyy-MM)*/
	public final static String DATE_PATTERN_SHORT = "yyyy-MM";
	
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	
	/** 时间格式(yyyy-MM-dd HH:mm)*/
	public final static String DATE_TIME_PATTERN_SHORT = "yyyy-MM-dd HH:mm";
	
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public final static String DATE_TIME_PATTERN_FULL = "yyyy-MM-dd HH:mm:ss SSS";

	public static String format(Date date) {
		return format(date, DATE_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}
	
	/**
	 * 字符串转日期
	 */
	public static Date string2Date(String date, String pattern) {		
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(date, pos);
		return strtodate;
	}
	
	/**
	 * 比较两个日期
	 */
	public static int compareDate(String startDate, String endDate,String pattern) {
		 DateFormat df = new SimpleDateFormat(pattern);
	     try {
	         Date dt1 = df.parse(startDate);
	         Date dt2 = df.parse(endDate);
	         if (dt1.getTime() > dt2.getTime()) 	              
	            return 1;
	         else if (dt1.getTime() < dt2.getTime()) 	               
	            return -1;
	         else 
	            return 0;	            
	     } catch (Exception exception) {
	         exception.printStackTrace();
	     }
	     return 0;		
	}
	
	/**
     * 把long 转换成 日期 再转换成String类型
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
	
	public static long fromDateStringToLong(String dateFormat, String inVal) { // 此方法计算时间毫秒
		Date date = null; // 定义时间类型
		SimpleDateFormat inputFormat = new SimpleDateFormat(dateFormat);
		try {
			date = inputFormat.parse(inVal); // 将字符型转换成日期型
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date.getTime(); // 返回毫秒数
	}
}
