package com.star.shop.basic.entity;

import com.star.shop.basic.utils.DateUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;


/**
 * 
 * 实体类基类
 *
 * @date 2018年8月10日
 *
 * @author x.zhang
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity {
	/**
	 * ID
	 */
	@Id
	@Column(length = 50)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	/**
	 * 创建时间
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String ctime;

	/**
	 * 修改时间
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String mtime;

	/**
	 * 创建人
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	@CreatedBy
	private String cuser;

	/**
	 * 修改人
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	@LastModifiedBy
	private String muser;

	@PrePersist
	public void prePersist() {
		this.setCtime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_FULL));
		this.setMtime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_FULL));
	}

	@PreUpdate
	public void preUpdate() {
		this.setMtime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_FULL));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getMtime() {
		return mtime;
	}

	public void setMtime(String mtime) {
		this.mtime = mtime;
	}

	public String getCuser() {
		return cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getMuser() {
		return muser;
	}

	public void setMuser(String muser) {
		this.muser = muser;
	}

}
