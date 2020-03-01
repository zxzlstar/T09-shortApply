package com.star.shop.admin.entity;

import com.star.shop.basic.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 
 * 
 * <p>
 * Title:OrderDetail
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
 * @date 2019年11月21日
 */
@Entity
@Table(name = "t_order_detail")
@DynamicInsert
@DynamicUpdate
public class OrderDetail extends AbstractEntity {
	
	/**
	 * 订单ID
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String oid;
		
	/**
	 * 商品
	 */
	@Fetch(FetchMode.JOIN)
	@ManyToOne
	@JoinColumn(name = "goods", nullable = false ,foreignKey = @ForeignKey(value=ConstraintMode.NO_CONSTRAINT))
	private Goods goods;

	/**
	 * 单价
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal price;

	/**
	 * 金额
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal amount;

	/**
	 * 数量
	 */
	@Column(columnDefinition = "int default 0")
	private Integer quantity;

	
	/**
	 * 是否评价, 0未评价,1已评价
	 */
	@Column(columnDefinition = "int default 0")
	private Integer isEvaluate;

	/**
	 * 评价内容
	 */
	@Column(columnDefinition = "varchar(127) default ''")
	private String evalmsg;
	
	/**
	 * 商品名称
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String goodsName;

	/**
	 * 商品规格
	 */
	@Column(columnDefinition = "varchar(256) default ''")
	private String paramsValue;
	
	public Integer getIsEvaluate() {
		return isEvaluate;
	}

	public void setIsEvaluate(Integer isEvaluate) {
		this.isEvaluate = isEvaluate;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}


	public String getEvalmsg() {
		return evalmsg;
	}

	public void setEvalmsg(String evalmsg) {
		this.evalmsg = evalmsg;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getParamsValue() {
		return paramsValue;
	}

	public void setParamsValue(String paramsValue) {
		this.paramsValue = paramsValue;
	}
}
