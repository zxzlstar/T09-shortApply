package com.star.shop.basic.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 
 * <p>
 * Title:SerialNumber
 * </p>
 *
 * <p>
 * Description:流水号管理
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 *
 * @author x.zhang
 *
 * @date 2018年12月10日
 */
@Entity
@Table(name = "t_serial_number")
@DynamicInsert
@DynamicUpdate
public class SerialNumber extends AbstractEntity {
	/**
	 * 当前序号
	 */
	@Column(columnDefinition = "int default 0")
	private Integer currNo;

	/**
	 * 流水号长度
	 */
	@Column(columnDefinition = "int default 6")
	private Integer len;

	/**
	 * 日期
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String date;

	/**
	 * 业务代码
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String serviceCode;

	public Integer getCurrNo() {
		return currNo;
	}

	public void setCurrNo(Integer currNo) {
		this.currNo = currNo;
	}

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

}
