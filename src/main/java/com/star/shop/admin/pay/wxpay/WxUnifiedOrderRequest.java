package com.star.shop.admin.pay.wxpay;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 预支付交易单
 * 
 * 参考文档：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_10&index=1
 * 
 * 接口地址：https://api.mch.weixin.qq.com/pay/micropay
 *
 * @date 2016年3月22日
 *
 * @author mo.xf
 *
 */
@XmlRootElement(name="xml")
public class WxUnifiedOrderRequest extends WxBasePayRequest{
	/**
	 * 交易起始时间
	 * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	 */
	private String time_start ;
	
	/**
	 * 交易结束时间
	 * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。其他详见时间规则注意：最短失效时间间隔必须大于5分钟
	 */
	private String time_expire ;
	
	/**
	 * 通知地址
	 * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
	 */
	private String notify_url ;
	/**
	 * 交易类型
	 * 取值如下：JSAPI，NATIVE，APP，详细说明见参数规定
	 */
	private String trade_type ;
	/**
	 * 商品ID
	 * trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
	 */
	private String product_id ;
	
	/**
	 * 用户标识
	 * 
	 * trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
	 */
	private String openid ;

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
