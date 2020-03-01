package com.star.shop.basic.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 
 * 
 * <p>Title:Role</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月17日
 */
@Entity
@Table(name = "t_role")
@DynamicInsert
@DynamicUpdate
public class Role extends AbstractEntity {
	/**
	 * 角色名称
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String roleName;

	/**
	 * 角色标识
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String roleSign;

	/**
	 * 备注
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String remark;

	@Transient
	private List<String> menuIdList;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleSign() {
		return roleSign;
	}

	public void setRoleSign(String roleSign) {
		this.roleSign = roleSign;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<String> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
		this.menuIdList = menuIdList;
	}

}
