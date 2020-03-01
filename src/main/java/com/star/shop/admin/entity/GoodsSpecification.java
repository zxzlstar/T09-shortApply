package com.star.shop.admin.entity;

import com.star.shop.basic.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


/**
 * 商品规格
 * @author zhangxing
 *
 */
@Entity
@Table(name = "t_goods_specification")
@DynamicInsert
@DynamicUpdate
public class GoodsSpecification extends AbstractEntity {
	
	/**
	 * 规格参数值
	 * 例如：
	 * {
	 *	"口味": "葱香",
	 *  "重量": "1kg"
	 * }
	 */
	@Column(columnDefinition = "varchar(1000) default ''")
	private String paramsValue;
	
	/**
	 * 商品id
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String goodsId;
	
	/**
	 * 商品名称
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String goodsName;
	
	/**
	 * 图片（只有一张）
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String picture;
	
	/**
	 * 商品原价
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal originalPrice;
	
	/**
	 * 商品价格
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal price;
	
	/**
	 * 库存
	 */
	@Column(columnDefinition = "int default 0")
	private Integer stock;
	
	/**
	 * 状态（1.正在使用，2.表示过期）
	 */
	@Column(columnDefinition = "int default 1")
	private Integer status;


	public String getParamsValue() {
		return paramsValue;
	}

	public void setParamsValue(String paramsValue) {
		this.paramsValue = paramsValue;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
