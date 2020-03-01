package com.star.shop.basic.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 日志
 * 
 * <p>
 * Title:Journal
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author x.zhang
 *
 * @date 2018年8月17日
 */
@Entity
@Table(name = "t_journal")
@DynamicInsert
@DynamicUpdate
public class Journal extends AbstractEntity {

	/**
	 * 操作用户id
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String userId;

	/**
	 * 操作用户
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String username;

	/**
	 * 操作
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String operation;

	/**
	 * 方法
	 */
	@Column(columnDefinition = "varchar(255) default ''")
	private String method;

	/**
	 * 参数
	 */
	@Column(columnDefinition = "text")
	private String params;

	/**
	 * 耗时
	 */
	@Column(columnDefinition = "bigint default 0")
	private Long time;

	/**
	 * 操作ip地址
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String ip;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
