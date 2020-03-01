
package com.star.shop.admin.entity;

import com.star.shop.basic.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 规格属性
 * @author zhangxing
 *
 */
@Entity
@Table(name = "t_specification_item")
@DynamicInsert
@DynamicUpdate
public class SpecificationItem extends AbstractEntity {
	
	/**
	 * 规格项
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String paramsName;

	/**
	 * 规格值,以“;”分割
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String paramsValue;
	
	/**
	 * 备注(关于规格项的一些描述说明)
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String remark;



	public String getParamsName() {
		return paramsName;
	}

	public void setParamsName(String paramsName) {
		this.paramsName = paramsName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getParamsValue() {
		return paramsValue;
	}

	public void setParamsValue(String paramsValue) {
		this.paramsValue = paramsValue;
	}
}
