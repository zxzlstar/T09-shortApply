package com.star.shop.admin.pay.wxpay;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 
 * <p>
 * Title:WxGetTransferInfoRequest
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
 * @author moxf
 *
 * @date 2018年6月7日
 */
@XmlRootElement(name = "xml")
public class WxGetTransferInfoRequest {
	/**
	 * 公众账号ID String(32) 是 wx8888888888888888 微信分配的公众账号ID（企业号corpid即为此appId）
	 */
	private String appid;

	/**
	 * 商户号 String(32) 是 1900000109 微信支付分配的商户号
	 */
	private String mch_id;

	/**
	 * 随机字符串 String(32) 是 5K8264ILTKCH16CQ2502SI8ZNMTM67VS 随机字符串，不长于32位。推荐随机数生成算法
	 */
	private String nonce_str;

	/**
	 * 签名 String(32) 是 C380BEC2BFD727A4B6845133519F3AD6 签名，详见签名生成算法
	 */
	private String sign;

	/**
	 * 商户调用企业付款API时使用的商户订单号
	 */
	private String partner_trade_no;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPartner_trade_no() {
		return partner_trade_no;
	}

	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}

}
