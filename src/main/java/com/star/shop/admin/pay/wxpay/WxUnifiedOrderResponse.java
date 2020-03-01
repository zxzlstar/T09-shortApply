package com.star.shop.admin.pay.wxpay;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 *
 *
 * @date 2016年4月19日
 *
 * @author mo.xf
 *
 */
@XmlRootElement(name = "xml")
public class WxUnifiedOrderResponse extends WxBusinessResponse{
	/**
	 * 交易类型
	 * 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，详细说明见参数规定
	 */
	private String trade_type ;
	
	/**
	 * 预支付交易会话标识
	 * 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
	 */
	private String prepay_id ;
	
	/**
	 * 二维码链接
	 * trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付
	 */
	private String code_url ;

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getCode_url() {
		return code_url;
	}

	public void setCode_url(String code_url) {
		this.code_url = code_url;
	}
}
