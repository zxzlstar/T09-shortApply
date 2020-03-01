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
 * Title:Dictionary
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
 * @date 2018年8月22日
 */
@Entity
@Table(name = "t_dictionary")
@DynamicInsert
@DynamicUpdate
public class Dictionary extends AbstractTreeEntity {
	/**
	 * 字典值
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String value;

	/**
	 * 状态(1:显示, 0:隐藏)
	 */
	@Column(columnDefinition = "int default 0")
	private Integer status;

	/**
	 * 类型(1:参数, 0:目录)
	 */
	@Column(columnDefinition = "int default 0")
	private Integer type;

	/**
	 * 排序
	 */
	@Column(columnDefinition = "int default 0")
	private Integer orderNum;

	/**
	 * 备注
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String remark;

	/**
	 * ztree属性
	 */
	@Transient
	private Boolean open;

	@Transient
	private List<?> list;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
