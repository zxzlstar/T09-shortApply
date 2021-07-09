package com.star.shop.admin.vo.express;

import java.util.Date;

/**
 * @author Cyan
 * @date 2021年1月19日
 */
public class ShunFengLogisticVo {
	
	
	private Date scanDateTime; // 时间
	
	private String remark; // 物流信息
	
	private String opCode; // 物流编码。首字母以5开头：已揽收； 以3开头：运输中；以2开头：派送中；以8开头：已签收

	public Date getScanDateTime() {
		return scanDateTime;
	}

	public void setScanDateTime(Date scanDateTime) {
		this.scanDateTime = scanDateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOpCode() {
		return opCode;
	}

	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

}
