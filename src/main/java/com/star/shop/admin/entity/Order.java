package com.star.shop.admin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.star.shop.basic.entity.AbstractEntity;
import com.star.shop.basic.utils.TimeSerializer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 
 * 订单
 * 
 * <p>
 * Title:Order
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
@Table(name = "t_order")
@DynamicInsert
@DynamicUpdate
public class Order extends AbstractEntity {
	/**
	 * 订单ID
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String oid;

	/**
	 * 会员ID
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String mid;

	/**
	 * 订单日期
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String date;

	/**
	 * 订单时间
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String time;

	/**
	 * 商品金额
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal amount;

	/**
	 * 快递费
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal postage;

	/**
	 * 数量
	 */
	@Column(columnDefinition = "int default 0")
	private Integer quantity;

	/**
	 * 卖家留言
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String message;

	/**
	 * 推荐人
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String referrer;

	/**
	 * 收货人
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String userName;

	/**
	 * 收货人手机
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String mobile;

	/**
	 * 收货地址
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String address;

	/**
	 * 订单状态，1待付款，2已付款，待发货，3已发货，待收货，4已经完成，5已经转入售后， -1取消  ,6  待赠送
	 */
	@Column(columnDefinition = "int default 0")
	private Integer status;

	/**
	 * 支付时间
	 */
	@Column(columnDefinition = "bigint default 0")
	@JSONField(serializeUsing = TimeSerializer.class)
	private long payTime;

	/**
	 * 货运单号
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String transportNo;

	/**
	 * 出货时间
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String transportTime;

	/**
	 * 用于展示订单列表
	 */
	@Transient
	private OrderDetail orderDetail;

	/**
	 * 支付订单号
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String payNo;


	/**
	 * 是否完成评价, 0未完成,1已完成
	 */
	@Column(columnDefinition = "int default 0")
	private Integer isEvaluate;


	/**
	 * 订单类型： 1 普通订单，2赠送订单
	 */
	@Column(columnDefinition = "int default 1")
	private Integer orderType;

	/**
	 * 接收人id
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String receiverName;

	/**
	 * 接收时间
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String receiveTime;

	@Column(columnDefinition = "varchar(100) default ''")
	private String receiveAdress;
	
	public Integer getIsEvaluate() {
		return isEvaluate;
	}

	public void setIsEvaluate(Integer isEvaluate) {
		this.isEvaluate = isEvaluate;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPostage() {
		return postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public long getPayTime() {
		return payTime;
	}

	public void setPayTime(long payTime) {
		this.payTime = payTime;
	}

	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getTransportNo() {
		return transportNo;
	}

	public void setTransportNo(String transportNo) {
		this.transportNo = transportNo;
	}

	public String getTransportTime() {
		return transportTime;
	}

	public void setTransportTime(String transportTime) {
		this.transportTime = transportTime;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}


	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}


	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiveAdress() {
		return receiveAdress;
	}

	public void setReceiveAdress(String receiveAdress) {
		this.receiveAdress = receiveAdress;
	}
}
