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
 * Title:Permission
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
 * @date 2018年8月21日
 */
@Entity
@Table(name = "t_permission")
@DynamicInsert
@DynamicUpdate
public class Permission extends AbstractEntity {
	@Column(columnDefinition = "varchar(50) default ''")
	private String roleId;

	@Column(columnDefinition = "varchar(50) default ''")
	private String menuId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
