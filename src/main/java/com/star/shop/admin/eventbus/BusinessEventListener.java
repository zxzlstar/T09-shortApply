package com.star.shop.admin.eventbus;

import com.google.common.eventbus.Subscribe;
import com.star.shop.admin.eventbus.handler.BusinessEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


@Component
public class BusinessEventListener {
	private final static Logger logger = LoggerFactory.getLogger(BusinessEventListener.class);
	
	@Subscribe
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void listener(BusinessEvent event) {
		try {
			String type = event.getType() ;
			BusinessEventHandler handler = this.getHandler(type) ;
			if(handler == null) {
				logger.error(type+"操作没有对应的执行类");
			}else {
				handler.execute(event.getData());
				logger.info(type+"操作执行,执行操作的类:"+handler.getClass());
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	 
	public BusinessEventHandler getHandler(String type) {
		return getClassMap().get(type);
	}
	
	public Map<Object, BusinessEventHandler> getClassMap() {
		Map<Object,BusinessEventHandler> map = new HashMap<Object,BusinessEventHandler>();
		return map ;
	 }
	 
	 public List<Class<?>> getClassList(Class<?> clazz) {
		 ArrayList<Class<?>> returnClassList = new ArrayList<Class<?>>();
		 String packageName = clazz.getPackage().getName();
		 List<Class<?>> allClass = getClassByPackageName(packageName);//获取该包下的所有类
		 for(int i = 0;i < allClass.size();i++) {
			 if(clazz.isAssignableFrom(allClass.get(i))) {
				 if(!clazz.equals(allClass.get(i))) {
					 returnClassList.add(allClass.get(i));//排除接口本身
				 }
			 }
		 }
		 return returnClassList;
	 }
	 
	 public List<Class<?>> getClassByPackageName(String packageName){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', File.separatorChar);
		Enumeration<URL> resources = null;
		try {
			resources = classLoader.getResources(path);
		} catch (IOException e) {
			logger.error("getClassByPackageName IOException:" , e.getMessage());
			e.printStackTrace();
		}
		 List<File> dirs = new ArrayList<File>();
		 while(resources.hasMoreElements()) {
			 URL resource = resources.nextElement();
			 dirs.add(new File(resource.getFile()));
		 }
		 ArrayList<Class<?>> classes = new ArrayList<Class<?>>(); 
		 for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName)); 
		}
		 return classes;
	 }
	 
	 public List<Class<?>> findClasses(File directory, String packageName) {
		 List<Class<?>> classes = new ArrayList<Class<?>>(); 
		 if (!directory.exists()) { 
			 return classes; 
		 }
		 File[] files = directory.listFiles();
		 for(File file:files) {
			 if(file.isDirectory()) {
				 assert!file.getName().contains(".");//递归查找文件夹下的所有文件
				 classes.addAll(findClasses(file, packageName + '.' + file.getName()));
			 }else if(file.getName().endsWith(".class")) {
				 try {
					classes.add(Class.forName(packageName + "." + file.getName().substring(0,file.getName().length() - 6)));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			 }
		 }
		 return classes;
	 }
	 
}
