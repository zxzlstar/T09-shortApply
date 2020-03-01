package com.star.shop.basic.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 
 * 
 * <p>
 * Title:User
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
@Table(name = "t_user")
@DynamicInsert
@DynamicUpdate
public class User extends AbstractEntity {

	/**
	 * 用户名
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String username;

	/**
	 * 密码
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String password;

	/**
	 * 邮箱
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String email;

	/**
	 * 手机号
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String mobile;

	/**
	 * 状态(0：禁用 1：正常)
	 */
	@Column(columnDefinition = "int default 0")
	private Integer status;

	/**
	 * 角色id列表
	 */
	@Transient
	private List<String> roleIdList;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<String> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		this.roleIdList = roleIdList;
	}

}
