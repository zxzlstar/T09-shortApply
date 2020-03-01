package com.star.shop.admin.pay.wxpay;

/**
 * 
 * 微信支付支付基本参数
 *
 * @date 2016年4月19日
 *
 * @author mo.xf
 *
 */
public abstract class WxBasePayRequest extends WxBaseRequest{
	/**
	 * 设备号		String(32)	否	013467007045764	终端设备号(商户自定义，如门店编号)
	 */
	private String device_info ;
	
	/**
	 * 商品描述	String(32)	是	Ipadmini16G白色	商品或支付单简要描述
	 */
	private String body ;	
	
	/**
	 * 商品详情	String(8192)	否	Ipadmini16G白色	商品名称明细列表
	 */
	private String detail ;	
	
	/**
	 * 附加数据	 String(127)	否	说明	附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
	 */
	private String attach ;	
		
	/**
	 * 商户订单号 String(32)	是	1217752501201407033233368018	商户系统内部的订单号,32个字符内、可包含字母,其他说明见商户订单号
	 */
	private String out_trade_no ;	
		
	/**
	 * 总金额  Int	是	888	订单总金额，单位为分，只能为整数，详见支付金额
	 */
	private String total_fee ;	
	
	/**
	 * 货币类型	String(16)	否	CNY	符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	 */
	private String fee_type ;	
	
	/**
	 * 终端IP	String(16)	是	8.8.8.8	调用微信支付API的机器IP
	 */
	private String spbill_create_ip ;	
	
	/**
	 * 商品标记	String(32)	否		商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
	 */
	private String goods_tag ;	
		
	/**
	 * 指定支付方式 String(32)	否	no_credit	no_credit--指定不能使用信用卡支付
	 */
	private String limit_pay ;	
	
	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getLimit_pay() {
		return limit_pay;
	}

	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}
}
