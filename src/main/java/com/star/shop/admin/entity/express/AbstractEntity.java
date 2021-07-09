/**
 * 
 */
package com.star.shop.admin.entity.express;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


/**
 * 
 * 实体类基类
 *
 * @date 2020年12月29日
 *
 * @author cyan
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
//	@GeneratedValue(generator = "uuid")
//	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;

	/**
	 * 创建时间
	 */
//	@Column(columnDefinition = "varchar(50) default ''")
//	private String ctime;

	/**
	 * 修改时间
	 */
//	@Column(columnDefinition = "varchar(50) default ''")
//	private String mtime;

	/**
	 * 创建人
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	@CreatedBy
	private String operation;

//	/**
//	 * 修改人
//	 */
//	@Column(columnDefinition = "varchar(50) default ''")
//	@LastModifiedBy
//	private String muser;

//	@PrePersist
//	public void prePersist() {
//		this.setCtime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_FULL));
//		this.setMtime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_FULL));
//	}

//	@PreUpdate
//	public void preUpdate() {
//		this.setMtime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_FULL));
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

//	public String getCtime() {
//		return ctime;
//	}
//
//	public void setCtime(String ctime) {
//		this.ctime = ctime;
//	}
//
//	public String getMtime() {
//		return mtime;
//	}
//
//	public void setMtime(String mtime) {
//		this.mtime = mtime;
//	}
//
//	public String getCuser() {
//		return cuser;
//	}
//
//	public void setCuser(String cuser) {
//		this.cuser = cuser;
//	}
//
//	public String getMuser() {
//		return muser;
//	}
//
//	public void setMuser(String muser) {
//		this.muser = muser;
//	}
	
	
}

