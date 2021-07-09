package com.star.shop.admin.vo.express;

import java.util.List;

/**
 * 顺丰快递接口返回的对象，参数只是拿了部分，还有部分参数暂时不写
 * @author Cyan
 * @date 2021年1月19日
 */
public class KdShunFengSearchResultVo {
	
	private String id; // 快递单号
	
	private String origin; // 快递起始地
	
	private String originOld; 
	
	private String originCode; 
	
	private String destination; 
	
	private String destinationOld; 
	
	private String receiveBillFlg; 
	
	private boolean delivered; 
	
	private String limitTypeCode;
	
	private String limitTypeName;
	
	private List<ShunFengLogisticVo> routes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getOriginOld() {
		return originOld;
	}

	public void setOriginOld(String originOld) {
		this.originOld = originOld;
	}

	public String getOriginCode() {
		return originCode;
	}

	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestinationOld() {
		return destinationOld;
	}

	public void setDestinationOld(String destinationOld) {
		this.destinationOld = destinationOld;
	}

	public String getReceiveBillFlg() {
		return receiveBillFlg;
	}

	public void setReceiveBillFlg(String receiveBillFlg) {
		this.receiveBillFlg = receiveBillFlg;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public String getLimitTypeCode() {
		return limitTypeCode;
	}

	public void setLimitTypeCode(String limitTypeCode) {
		this.limitTypeCode = limitTypeCode;
	}

	public String getLimitTypeName() {
		return limitTypeName;
	}

	public void setLimitTypeName(String limitTypeName) {
		this.limitTypeName = limitTypeName;
	}

	public List<ShunFengLogisticVo> getRoutes() {
		return routes;
	}

	public void setRoutes(List<ShunFengLogisticVo> routes) {
		this.routes = routes;
	}
	
}
