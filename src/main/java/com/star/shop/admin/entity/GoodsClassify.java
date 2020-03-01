package com.star.shop.admin.entity;

import com.star.shop.basic.entity.AbstractTreeEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 商品分类
 * 
 * <p>
 * Title:GoodsClassify
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
 * @date 2019年9月22日
 */
@Entity
@Table(name = "t_goods_classify")
@DynamicInsert
@DynamicUpdate
public class GoodsClassify extends AbstractTreeEntity {



	/**
	 * 分类图片
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String image;
	/**
	 * 分类备注
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String remark;
	
	/**
	 * 排序
	 */
	@Column(columnDefinition = "int default 0")
	private Integer rank;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}


}
