package com.star.shop.admin.entity.express;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Cyan
 * @date 2021年1月19日
 */
@Entity
@Table(name = "express_spider_token")
@DynamicInsert
@DynamicUpdate
public class ExpressSpiderToken {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
	
    /**
     * token
     */
	@Column(columnDefinition = "varchar(100) default ''")
	private String token;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8") 
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@PrePersist
	public void prePersist() {
		this.setUpdateTime(new Date());
	}
	
	@PreUpdate
	public void preUpdate() {
		this.setUpdateTime(new Date());
	}
}
