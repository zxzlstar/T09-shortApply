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
 * 系统菜单
 * 
 * <p>
 * Title:Menu
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
@Table(name = "t_menu")
@DynamicInsert
@DynamicUpdate
public class Menu extends AbstractTreeEntity {
	/**
	 * 菜单url
	 */
	@Column(columnDefinition = "varchar(255) default ''")
	private String url;

	/**
	 * 授权标识(多个用逗号分隔，如：user:list,user:create)
	 */
	@Column(columnDefinition = "varchar(255) default ''")
	private String perms;

	/**
	 * 类型(0：目录 1：菜单 2：按钮)
	 */
	@Column(columnDefinition = "int default 0")
	private Integer type;

	/**
	 * 菜单图标
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String icon;

	/**
	 * 排序
	 */
	@Column(columnDefinition = "bigint default 0")
	private Long orderNum;

	/**
	 * ztree属性
	 */
	@Transient
	private Boolean open;

	@Transient
	private List<?> list;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPerms() {
		return perms;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

}
