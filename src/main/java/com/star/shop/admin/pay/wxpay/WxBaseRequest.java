package com.star.shop.admin.pay.wxpay;

/**
 * 
 * 微信支付请求基本数据
 *
 * @date 2016年3月22日
 *
 * @author mo.xf
 *
 */
public abstract class WxBaseRequest {
	/**
	 * 公众账号ID String(32) 是 wx8888888888888888 微信分配的公众账号ID（企业号corpid即为此appId）
	 */
	private String appid;

	/**
	 * 商户号 String(32) 是 1900000109 微信支付分配的商户号
	 */
	private String mch_id;

	/**
	 * 随机字符串 String(32) 是 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
	 * 随机字符串，不长于32位。推荐随机数生成算法
	 */
	private String nonce_str;

	/**
	 * 签名 String(32) 是 C380BEC2BFD727A4B6845133519F3AD6 签名，详见签名生成算法
	 */
	private String sign;

	/**
	 * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
	 */
	private String sign_type;

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

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

}
