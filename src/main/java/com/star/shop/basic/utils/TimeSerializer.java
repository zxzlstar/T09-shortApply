package com.star.shop.basic.utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * 
 * 
 * <p>Title:TimeSerializer</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年1月31日
 */
public class TimeSerializer implements ObjectSerializer {

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		long value = 0L;
		if(object != null){
			value = ((Long) object).longValue();
		}
		
		if(value == 0L) {
			serializer.out.writeString("");
		}else {
			serializer.out.writeString(DateUtils.format(new Date(value), DateUtils.DATE_TIME_PATTERN));
		}
	}

}
