package com.star.shop.basic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 
 * <p>
 * Title:Token
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
 * @date 2018年2月6日
 */
@Entity
@Table(name = "t_token")
@DynamicInsert
@DynamicUpdate
public class Token extends AbstractEntity {
	/**
	 * 用户ID
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String userId;

	/**
	 * token值
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String token;

	/**
	 * appid
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String appid;

	/**
	 * 过期时间
	 */
	@Column(columnDefinition = "bigint default 0")
	private Long expireTime;

	/**
	 * 状态:0正常，1过期,2重复登录过期
	 */
	@Column(columnDefinition = "int default 0")
	private Integer state;

	/**
	 * 备注
	 */
	@Column(columnDefinition = "varchar(256) default ''")
	private String remark;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
