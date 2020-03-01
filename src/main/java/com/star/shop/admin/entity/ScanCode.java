package com.star.shop.admin.entity;

import com.star.shop.basic.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 扫码
 * 
 * <p>
 * Title:ScanCode
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
 * @date 2018年5月21日
 */
@Entity
@Table(name = "t_scan_code")
@DynamicInsert
@DynamicUpdate
public class ScanCode extends AbstractEntity {
	/**
	 * 类型:1绑定微信，2扫码登录,3店员绑定微信,4店员解绑微信
	 */
	@Column(columnDefinition = "int default 1")
	private Integer type;

	/**
	 * 状态：1创建，2,已失效，3已扫码，4已确认
	 */
	@Column(columnDefinition = "int default 1")
	private Integer status;

	/**
	 * 扫码时间
	 */
	@Column(columnDefinition = "bigint default 0")
	private Long scanTime;

	/**
	 * 失效时间
	 */
	@Column(columnDefinition = "bigint default 0")
	private Long deadTime;

	/**
	 * 微信openId
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String wxOpenid;

	/**
	 * 昵称
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String wxNickname;

	/**
	 * 微信头像
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String wxHeadimgurl;

	/**
	 * 登录token
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String token;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getScanTime() {
		return scanTime;
	}

	public void setScanTime(Long scanTime) {
		this.scanTime = scanTime;
	}

	public Long getDeadTime() {
		return deadTime;
	}

	public void setDeadTime(Long deadTime) {
		this.deadTime = deadTime;
	}

	public String getWxOpenid() {
		return wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	public String getWxNickname() {
		return wxNickname;
	}

	public void setWxNickname(String wxNickname) {
		this.wxNickname = wxNickname;
	}

	public String getWxHeadimgurl() {
		return wxHeadimgurl;
	}

	public void setWxHeadimgurl(String wxHeadimgurl) {
		this.wxHeadimgurl = wxHeadimgurl;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
