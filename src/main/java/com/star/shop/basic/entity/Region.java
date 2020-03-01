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
 * <p>
 * Title:Region
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
 * @date 2018年8月23日
 */
@Entity
@Table(name = "t_region")
@DynamicInsert
@DynamicUpdate
public class Region extends AbstractEntity {
	/**
	 * 区域名称
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String name;
	/**
	 * 上级区域名称
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String pname;
	/**
	 * 区域编号
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String code;

	/**
	 * 上级区域编号
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String pcode;

	/**
	 * 层级，1：省级，2：地市，3：区县,4:街道
	 */
	@Column(columnDefinition = "tinyint default 1")
	private Integer layer;

	/**
	 * 排序
	 */
	@Column(columnDefinition = "int default 0")
	private Integer orderNum;

	/**
	 * 状态，1：显示，0：隐藏
	 */
	@Column(columnDefinition = "int default 0")
	private Integer status;

	/**
	 * 备注
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String remark;

	@Transient
	private boolean isParent;

	/**
	 * ztree属性
	 */
	@Transient
	private Boolean open;

	@Transient
	private List<?> list;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean getIsParent() {
		return this.layer < 3;
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
