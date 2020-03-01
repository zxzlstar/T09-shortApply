package com.star.shop.admin.pay.wxpay;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 *
 * @date 2016年4月25日
 *
 * @author mo.xf
 *
 */
@XmlRootElement(name = "xml")
public class WxTradeNotity extends WxBusinessResponse {
	private String sub_appid;
	private String sub_mch_id;
	
	/**
	 * 用户标识 ,用户在商户appid下的唯一标识
	 */
	private String openid;
	
	/**
	 * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	 */
	private String is_subscribe;
	
	/**
	 * 
	 */
	private String sub_openid;
	private String sub_is_subscribe;
	
	/**
	 * 交易类型,JSAPI、NATIVE、APP
	 */
	private String trade_type;
	
	/**
	 * 银行类型，采用字符串类型的银行标识，银行类型见银行列表
	 */
	private String bank_type;
	
	/**
	 * 订单总金额，单位为分
	 */
	private int total_fee;
	
	/**
	 * 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 */
	private String fee_type;
	
	/**
	 * 现金支付金额订单现金支付金额，详见支付金额
	 */
	private int cash_fee;
	
	/**
	 * 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 */
	private String cash_fee_type;
	
	/**
	 * 代金券或立减优惠金额<=订单总金额，订单总金额-代金券或立减优惠金额=现金支付金额，详见支付金额
	 */
	private int coupon_fee;
	
	/**
	 * 代金券或立减优惠使用数量
	 */
	private int coupon_count;
	
	/**
	 * 微信支付订单号
	 */
	private String transaction_id;
	
	/**
	 * 商户系统的订单号，与请求一致。
	 */
	private String out_trade_no;
	
	/**
	 * 商家数据包，原样返回
	 */
	private String attach;
	
	/**
	 * 支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	 */
	private String time_end;

	public String getSub_appid() {
		return sub_appid;
	}

	public void setSub_appid(String sub_appid) {
		this.sub_appid = sub_appid;
	}

	public String getSub_mch_id() {
		return sub_mch_id;
	}

	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	public String getSub_openid() {
		return sub_openid;
	}

	public void setSub_openid(String sub_openid) {
		this.sub_openid = sub_openid;
	}

	public String getSub_is_subscribe() {
		return sub_is_subscribe;
	}

	public void setSub_is_subscribe(String sub_is_subscribe) {
		this.sub_is_subscribe = sub_is_subscribe;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public int getCash_fee() {
		return cash_fee;
	}

	public void setCash_fee(int cash_fee) {
		this.cash_fee = cash_fee;
	}

	public String getCash_fee_type() {
		return cash_fee_type;
	}

	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}

	public int getCoupon_fee() {
		return coupon_fee;
	}

	public void setCoupon_fee(int coupon_fee) {
		this.coupon_fee = coupon_fee;
	}

	public int getCoupon_count() {
		return coupon_count;
	}

	public void setCoupon_count(int coupon_count) {
		this.coupon_count = coupon_count;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
}
