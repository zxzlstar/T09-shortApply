package com.star.shop.admin.eventbus;

import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

@Component
public class EventBusComponent {
	/**
	 * 
	 * @param businessEventListener
	 */
	public EventBusComponent(BusinessEventListener businessEventListener) {
		this.eventBus.register(businessEventListener);
	}
	
	private EventBus eventBus = new AsyncEventBus(Executors.newFixedThreadPool(32));
	
	public void post(BusinessEvent businessEvent) {
        eventBus.post(businessEvent);
	}
}
