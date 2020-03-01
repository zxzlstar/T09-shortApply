package com.star.shop.basic.utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.star.shop.basic.component.FileComponent;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
public class PathSerializer implements ObjectSerializer {

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		String path = "";
		if(object != null){
			path = ((String) object);
		}
		FileComponent fileComponent = SpringContextHolder.getBean("fileComponent");
		if(path.contains(";")) {
			String [] paths = path.split(";");
			List<String> list = new ArrayList<>();
			for (String str : paths) {
				if(org.apache.commons.lang3.StringUtils.isNotBlank(str)) {
					list.add(fileComponent.getImageUrl(str));
				}
			}
			serializer.out.writeString(StringUtils.join(list, ";"));
		}else if(org.apache.commons.lang3.StringUtils.isNotBlank(path)){	
			serializer.out.writeString(fileComponent.getImageUrl(path));
		}else {
			serializer.out.writeString("");
		}
	}

}
