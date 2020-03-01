package com.star.shop.admin.entity;

import com.star.shop.basic.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * Banner管理
 * 
 * <p>
 * Title:Banner
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
 * @date 2017年8月29日
 */
@Entity
@Table(name = "t_banner")
@DynamicInsert
@DynamicUpdate
public class Banner extends AbstractEntity {
	/**
	 * Banner名称
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String name;

	/**
	 * 位置：1首页,2竞猜页面
	 */
	@Column(columnDefinition = "int default 1")
	private Integer position;

	/**
	 * 排序
	 */
	@Column(columnDefinition = "int default 0")
	private Integer rank;

	/**
	 * 图片上传id(文件上传信息记录到t_upload表，这里记录是表的ID)
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String image;

	/**
	 * 链接地址
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String link;

	/**
	 * 备注
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String remark;
	
	@Transient
	private String imageUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	
}
