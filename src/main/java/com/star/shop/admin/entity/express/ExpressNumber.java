/**
 * 
 */
package com.star.shop.admin.entity.express;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author cyan
 * 国内快递单号
 */
@Entity
@Table(name = "express_number")
@DynamicInsert
@DynamicUpdate
public class ExpressNumber {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	
    /**
     * 单号
     */
	@Column(columnDefinition = "varchar(100) default ''")
	private String number;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8") 
	private Date operationTime;
	
	/**
	 * 创建人
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	@CreatedBy
	private String operation;
	
	@Column(columnDefinition = "int default 0")
	private Integer status;  // 状态：0未完成抓取，1已完成抓取。

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	@PrePersist
	public void prePersist() {
		this.setOperationTime(new Date());
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
