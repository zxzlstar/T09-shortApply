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
 * Title:Upload
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
 * @date 2018年8月28日
 */
@Entity
@Table(name = "t_upload")
@DynamicInsert
@DynamicUpdate
public class Upload extends AbstractEntity {
	/**
	 * 保存路径
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String path;

	/**
	 * 
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String contentType;

	/**
	 * 原始名称
	 */
	@Column(columnDefinition = "varchar(255) default ''")
	private String sourceName;

	/**
	 * 大小，单位K
	 */
	@Column(columnDefinition = "bigint default 0")
	private long size;

	/**
	 * 上传七牛key
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String qiniuKey;

	/**
	 * 上传阿里云key
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String aliyunKey;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getQiniuKey() {
		return qiniuKey;
	}

	public void setQiniuKey(String qiniuKey) {
		this.qiniuKey = qiniuKey;
	}

	public String getAliyunKey() {
		return aliyunKey;
	}

	public void setAliyunKey(String aliyunKey) {
		this.aliyunKey = aliyunKey;
	}

}
