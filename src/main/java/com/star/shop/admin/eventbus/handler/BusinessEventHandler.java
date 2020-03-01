package com.star.shop.admin.eventbus.handler;

public interface BusinessEventHandler {
	
	public String getBusinessType();
	
	public void execute(Object data);
	
}
